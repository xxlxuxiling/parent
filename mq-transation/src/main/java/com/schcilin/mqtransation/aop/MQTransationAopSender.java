package com.schcilin.mqtransation.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.schcilin.mqtransation.anno.MQTransationMessageAnno;
import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.coordinator.DBCoordinator;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import com.schcilin.mqtransation.sender.MQTransation4Bug2ReSend;
import com.schcilin.mqtransation.sender.MQTransationSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 15:24
 * @Content: AOP切面实现分布式事务
 */

@Aspect
@Component
@Slf4j
public class MQTransationAopSender {

    @Autowired
    private MQTransationSender mqTransationSender;
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private MQTransation4Bug2ReSend mqTransation4Bug2ReSend;

    /**
     * 定义注解类型的切点，只要方法上有该注解，都会匹配
     */
    @Pointcut("@annotation(com.schcilin.mqtransation.anno.MQTransationMessageAnno)")
    public void sendPointCut() {
    }

    @Around("sendPointCut()&& @annotation(mqt)")
    public void beforeSendMsg(ProceedingJoinPoint joinPoint, MQTransationMessageAnno mqt) throws Throwable {
        //支持spring EL表达式 start
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取目标方法
        Method method = signature.getMethod();
        //获取方法参数
        String[] parameterNames = discoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        //获取方法上的注解
        MQTransationMessageAnno mqTransationMessageAnno = method.getDeclaredAnnotation(MQTransationMessageAnno.class);
        //得到el表达式
        String elExpress = mqTransationMessageAnno.bizName();
        //解析el表达式
        //spring el 解释器
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression expression = spelExpressionParser.parseExpression(elExpress);
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        int pNameLen = parameterNames.length;
        for (int i = 0; i < pNameLen; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }
        //获取参数el 表达式的值
        Object value = expression.getValue(evaluationContext, Object.class);

        //支持spring EL表达式 end
        log.info("==>custom mq annotation:" + mqt);
        String[] exchange = mqt.exchange().split(",");
        String[] bindingKey = mqt.bindingKey().split(",");
        String[] queue = mqt.bindingQueue().split(",");
        String coordinator = mqt.dbCoordinator();
        final DBCoordinator dbCoordinator = (DBCoordinator) applicationContext.getBean(coordinator);
        try {
            if (exchange.length != bindingKey.length) {
                throw new Exception("绑定交换机和路由不正确");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        /**发送前暂存消息*/
        //为了保存业务信息，把参数保留在msg里面
        ObjectMapper mapper = new ObjectMapper();
        String bizValue = mapper.writeValueAsString(args[0]);
        int changeLen = exchange.length;
        List<RabbitMetaMessage> batchMsg = Lists.newArrayList();
        for (int j = 0; j < changeLen; j++) {
            /**生成发送消息体*/
            RabbitMetaMessage rabbitMetaMessage = new RabbitMetaMessage();
            rabbitMetaMessage.setMessageId(queue[j] + ":" + value + MQConstant.DB_SPLIT + getCurrentDateTime());
            /**交换机*/
            rabbitMetaMessage.setExchange(exchange[j]);
            /**指定routing key */
            rabbitMetaMessage.setRoutingKey(bindingKey[j]);
            /** 设置消息来源 */
            rabbitMetaMessage.setOrigin("provider");
            /**保留业务信息*/
            rabbitMetaMessage.setBizMsg(bizValue);
            /** 将消息设置为prepare状态，此时非必须保证redis服务器可用*/
            dbCoordinator.setMsgPrepare(rabbitMetaMessage.getMessageId());
            batchMsg.add(rabbitMetaMessage);
        }

        /** 执行业务函数 */
        try {
            joinPoint.proceed();
        } catch (Exception e) {
            //业务信息异常，删除redis中prepare状态信息
            batchMsg.stream().forEach(msg -> dbCoordinator.deleteMsgPrepare(msg.getMessageId()));
            log.error("业务执行失败,业务名称:" + value);
            throw e;
        }
        batchMsg.stream().forEach(msg -> {
            /** 将消息设置为ready状态，此时必须保证redis服务器可用*/
            dbCoordinator.setMsgReady(msg.getMessageId(), msg);
        });

        /** 发送消息 */
        try {
            /**保存消息载体*/
            this.mqTransationSender.setCorrelationData(coordinator);
            //此时业务执行成功，必须向mq发送消息
            //int i =1/0;
            batchMsg.stream().forEach(msg -> {

                try {
                    this.mqTransationSender.send(msg);
                } catch (Exception e) {
                    log.error("第四阶段消息ID:{}发送异常，此时本地事务已经执行完成，向RabbitMQ发送消息必须成功", msg.getMessageId(), e);
                    Long increment = dbCoordinator.incrResendKey(MQConstant.MQ_PROVIDER_RETRY_COUNT_KEY, msg.getMessageId());
                    log.error("生产者成功生产消息ID->{}，同时添加redis中的统计{}次", msg.getMessageId(), increment);
                    try {

                        this.mqTransation4Bug2ReSend.reSendMsg4Bug(msg.getMessageId());
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 解析请求信息 获取属性字段的值
     *
     * @param bean 传入的要解析的bean对象
     * @param expr 要解析的请求信息
     * @return 返回取到对应字段的值
     */
    public Object parseElExpress(Object bean, String expr) {
        //获得bean的一个
        Class beanClass = bean.getClass();
        Method method = null;
        Object value = null;
        try {
            //通过放射机制 获得方法 并执行[由于get访问器没有参数，所以直接传入null]
            String[] split = expr.split(".");
            method = beanClass.getMethod(split[1], null);
            //调用get方法  获得值
            value = method.invoke(bean, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 时间格式化
     *
     * @return
     */
    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

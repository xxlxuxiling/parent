package com.schcilin.mqtransation.aop;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        log.info("==>custom mq annotation:" + mqt);
        String exchange = mqt.exchange();
        String bindingKey = mqt.bindingKey();
        String bizName = mqt.bizName() + MQConstant.DB_SPLIT + getCurrentDateTime();
        String coordinator = mqt.dbCoordinator();
        DBCoordinator dbCoordinator = null;
        try {
            dbCoordinator = (DBCoordinator) applicationContext.getBean(coordinator);
        } catch (Exception e) {
            log.error("无消息存储类，事务执行终止");
            return;
        }
        /**发送前暂存消息*/
        dbCoordinator.setMsgPrepare(bizName);
        Object returnObj = null;
        /** 执行业务函数 */
        try {
            returnObj = joinPoint.proceed();
        } catch (Exception e) {
            //业务信息异常，删除redis中prepare状态信息
            dbCoordinator.deleteMsgPrepare(bizName);
            log.error("业务执行失败,业务名称:" + bizName);
            throw e;
        }
        if (returnObj == null) {
            returnObj = "";
        }
        /**生成发送消息体*/
        RabbitMetaMessage rabbitMetaMessage = new RabbitMetaMessage();
        rabbitMetaMessage.setMessageId(bizName);
        /**交换机*/
        rabbitMetaMessage.setExchange(exchange);
        /**指定routing key */
        rabbitMetaMessage.setRoutingKey(bindingKey);
        /** 设置需要传递的消息体,可以是任意对象 */
        rabbitMetaMessage.setPayload(returnObj);
        /** 将消息设置为ready状态，此时必须保证redis服务器可用*/
        dbCoordinator.setMsgReady(bizName, rabbitMetaMessage);

        /** 发送消息 */
        try {
            /**保存消息载体*/
            this.mqTransationSender.setCorrelationData(coordinator);
            //此时业务执行成功，必须向mq发送消息
            //int i =1/0;
            this.mqTransationSender.send(rabbitMetaMessage);
        } catch (Exception e) {
            log.error("第四阶段消息ID:{}发送异常，此时本地事务已经执行完成，向RabbitMQ发送消息必须成功", bizName, e);
            Long increment = dbCoordinator.incrResendKey(MQConstant.MQ_PROVIDER_RETRY_COUNT_KEY, bizName);
            log.error("生产者成功生产消息ID->{}，同时添加redis中的统计{}次", bizName, increment);
            try {

                this.mqTransation4Bug2ReSend.reSendMsg4Bug(bizName);
            } catch (Exception ee) {
                ee.printStackTrace();
            }

        }

    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

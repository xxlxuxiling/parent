package com.schcilin.mqtransation.config;

import com.schcilin.mqtransation.coordinator.DBCoordinator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: schcilin
 * @Date: 2019/5/24 13:53
 * @Content: RabbitTemplate配置工厂类
 */

@Configuration
@Slf4j
public class RabbitTemplateConfig {

    @Autowired
    private ApplicationContext applicationContext;
    private boolean returnFlag = false;

    @Bean
    public RabbitTemplate customerRabbitTemplate(ConnectionFactory connectionFactory) {
        log.info("customerRabbitTemplate, connectionFactory:" + connectionFactory);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        /**ReturnCallback*/
        rabbitTemplate.setMandatory(true);
        /**消息发送到RabbitMQ交换器后接收ack回调void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause);*/
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (returnFlag) {
                log.error("mq发送错误，无对应的的交换机,confirm回掉,ack={},correlationData={} cause={} returnFlag={}",
                        ack, correlationData, cause, returnFlag);
            }
            log.info("confirm回调，ack={} correlationData={} cause={}", ack, correlationData, cause);
            String msgId = correlationData.getId();
            /** 只要消息能投入正确的消息队列，并持久化，就返回ack为true*/
            if (ack) {
                log.info("消息已正确投递到RabbitMQ队列, correlationData:{}", correlationData);
                //此时需要清除redis中的缓存
                String coordinator = ((CompleteCorrelationData) correlationData).getCoordinator();
                DBCoordinator dbCoordinator = (DBCoordinator) applicationContext.getBean(coordinator);
                dbCoordinator.setMsgSuccess(msgId);

            } else {
                log.error("消息投递至交换机失败,业务号:{}，原因:{}", correlationData.getId(), cause);
            }
        });
        //消息发送到RabbitMQ交换器，但无相应Exchange时的回调
        /**Message message, int replyCode, String replyText,
         String exchange, String routingKey*/
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String messageId = message.getMessageProperties().getMessageId();
            log.error("return回调，没有找到任何匹配的队列！message id:{},replyCode{},replyText:{},"
                    + "exchange:{},routingKey{}", messageId, replyCode, replyText, exchange, routingKey);
            returnFlag = true;

        });
        return rabbitTemplate;

    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

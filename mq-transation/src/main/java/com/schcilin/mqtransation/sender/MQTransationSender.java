package com.schcilin.mqtransation.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schcilin.mqtransation.config.CompleteCorrelationData;
import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 18:02
 * @Content: 分布式事务发起者
 */

@Component
@Slf4j
public class MQTransationSender {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 扩展消息的CorrelationData，方便在回调中应用
     * @param coordinator
     */
    public void setCorrelationData(String coordinator) {
        rabbitTemplate.setCorrelationDataPostProcessor(((message, correlationData) ->
                new CompleteCorrelationData(correlationData != null ? correlationData.getId() : null, coordinator)));
    }

    /**
     * 向RabbitMQ发送消息
     *
     * @param rabbitMetaMessage
     */
    public void send(RabbitMetaMessage rabbitMetaMessage) throws Exception {
        String msgId = rabbitMetaMessage.getMessageId();
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setMessageId(msgId);
                // 设置消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        };
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(rabbitMetaMessage.getPayload());
        MessageProperties messageProperties = new MessageProperties();
        /**默认"application/octet-stream"*/
        messageProperties.setContentType("application/json");
        //消息体
        Message message = new Message(value.getBytes(), messageProperties);
        try {
            this.rabbitTemplate.convertAndSend(rabbitMetaMessage.getExchange(), rabbitMetaMessage.getRoutingKey(), message, messagePostProcessor, new CorrelationData(msgId));
            log.info("成功向rabbitMQ发送消息，消息ID为{}", msgId);
        } catch (Exception e) {

            log.error("向rabbitMQ发送消息异常，消息ID：{}，消息体:{}, exchangeName:{}, routingKey:{}",
                    msgId, value, rabbitMetaMessage.getExchange(), rabbitMetaMessage.getRoutingKey(), e);
            throw e;

        }
    }

}

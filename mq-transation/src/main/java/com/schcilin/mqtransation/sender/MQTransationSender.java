package com.schcilin.mqtransation.sender;

import com.schcilin.mqtransation.config.CompleteCorrelationData;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 18:02
 * @Content: 分布式事务发起者
 */

@Component
public class MQTransationSender implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**扩展消息的CorrelationData，方便在回调中应用*/
    public void setCorrelationData(String coordinator){
        rabbitTemplate.setCorrelationDataPostProcessor(((message, correlationData) ->
                new CompleteCorrelationData(correlationData != null ? correlationData.getId() : null, coordinator)));
    }

    /**
     * 发送消息
     * @param rabbitMetaMessage
     */
    public void send(RabbitMetaMessage rabbitMetaMessage){

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {

    }
}

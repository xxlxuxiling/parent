package com.schcilin.mqtransation.listener;

import com.rabbitmq.client.Channel;
import com.schcilin.mqtransation.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/5/24 17:44
 * @Content: RabbitMQ抽象消息监听，所有消息消费者必须继承此类
 */

@Component
@Slf4j
public class AbstractRabbitMQMessageListener implements ChannelAwareMessageListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        redisTemplate.opsForHash().increment(MQConstant.MQ_CONSUMER_RETRY_COUNT_KEY,messageProperties.getMessageId(),1L);
        //https://github.com/vvsuperman/coolmq

    }
}

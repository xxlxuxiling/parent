package com.schcilin.mqtransation.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: schcilin
 * @Date: 2019/5/24 17:44
 * @Content: RabbitMQ抽象消息监听，所有消息消费者必须继承此类
 */

@Slf4j
public abstract class AbstractRabbitMQMessageListener implements ChannelAwareMessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 钩子回调方法，本项目的精髓，最骚之处，子类必须实现这个方法，消费消息
     * 代码入侵少,
     *
     * @param message 消息
     */
    public abstract void receiveMsg(Message message) throws Exception;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        Long countConsumercount = redisTemplate.opsForHash().increment(MQConstant.MQ_CONSUMER_RETRY_COUNT_KEY, messageProperties.getMessageId(), 1L);
        log.info("收到消息,当前消息ID:{} 消费次数：{}", messageProperties.getMessageId(), countConsumercount);
        try {
            //消费者成功消费消息
            receiveMsg(message);
            //成功消费消息执行回执
            channel.basicAck(deliveryTag, false);
            //消费者消费消息成功，将redis中的统计次数删除
            Long delete = redisTemplate.opsForHash().delete(MQConstant.MQ_CONSUMER_RETRY_COUNT_KEY, messageProperties.getMessageId());
            log.info("消费者成功消费消息ID->{}，同时删除redis中的统计{}次", messageProperties.getMessageId(), delete);
            //此时需要将redisready状态设置为一定时间内过期
            redisTemplate.opsForHash().delete(MQConstant.MQ_MSG_READY, messageProperties.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            //消费者消费消息失败
            log.error("消费者消费RabbitMQ失败，消息ID{}", messageProperties.getMessageId(), e);
            //超过最大次数，进入死信队列
            if (countConsumercount >= MQConstant.MAX_CONSUMER_COUNT) {
                //表示如何处理这条消息，如果值为true，则重新放入RabbitMQ的发送队列，如果值为false，则通知RabbitMQ销毁这条消息
                channel.basicReject(deliveryTag, false);
                //是否消息恢复
                //channel.basicRecover(true);
                //消费者消费消息失败,消息体为RabbitMetaMessage,同时设置消息源
                ObjectMapper mapper = new ObjectMapper();
                String messaged = new String(message.getBody());
                RabbitMetaMessage rabbitMetaMessage = mapper.readValue(messaged.getBytes("utf-8"), RabbitMetaMessage.class);
                rabbitMetaMessage.setOrigin("consumer");
                this.redisTemplate.opsForHash().put(MQConstant.MQ_MSG_READY, messageProperties.getMessageId(), rabbitMetaMessage);
                Long delete = redisTemplate.opsForHash().delete(MQConstant.MQ_CONSUMER_RETRY_COUNT_KEY, messageProperties.getMessageId());
                log.error("消费者消费消息ID->{}失败，同时删除redis中的统计{}次", messageProperties.getMessageId(), delete);

            }
            else {
                log.error("rabbitMQ消费消息异常，消息ID：{}， exchangeName:{}, routingKey:{}",
                        messageProperties.getMessageId(), messageProperties.getReceivedExchange(), messageProperties.getReceivedRoutingKey(), e);
                //重新发送消息，但是程序要延迟发送，为了等微服务可用，减少人工干预
                Thread.sleep((long) Math.pow(MQConstant.BASE_NUM, countConsumercount) * 1000);
                //同时发送次数+1
                redisTemplate.opsForHash().increment(MQConstant.MQ_CONSUMER_RETRY_COUNT_KEY, messageProperties.getMessageId(), 1);
                //向MQ重新发送消息
                /**
                 * @param multiple 真，拒绝所有消息，直到和包括
                 * 所提供的送货标签;false拒绝仅提供的内容
                 * 交货标签
                 * @param requeue 如果应该重新请求被拒绝的消息，而不是丢弃/死字，则requeue为true
                 */
                channel.basicNack(deliveryTag, false, true);

            }

        }

    }
}

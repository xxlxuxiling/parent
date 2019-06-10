package com.schcilin.mqtransation.config;

import com.google.common.collect.Maps;
import com.schcilin.mqtransation.consumer.XxlMQTransationMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: schcilin
 * @Date: 2019/5/28 11:47
 * @Content: RabbitMQ交换机、队列的配置类.定义交换机、key、queue并做好绑定。
 * 同时定义每个队列的ttl，队列最大长度，Qos等等
 * 建议每个队列定义自己的QueueConfig
 */

//@Configuration
public class XxlQueueConfig {
    /**
     * 业务交换机
     */
    @Bean
    public DirectExchange bizQueueExchange() {
        return new DirectExchange("xxl.exchange");
    }

    /**
     * 声明业务队列String name, boolean durable, boolean exclusive, boolean autoDelete
     */
    @Bean
    public Queue bizQueue() {
        return new Queue("xxl.queue", true, false, false);
    }

    /**
     * 通过死信路由key绑定交换机和队列
     *
     * @return
     */
    @Bean
    public Binding bizBinding() {
        return BindingBuilder.bind(bizQueue()).to(bizQueueExchange()).with("xxl.route");
    }

    /**
     * 死信队列的监听
     *
     * @param connectionFactory RabbitMQ连接工厂
     * @param listener          队列监听器
     * @return 监听容器对象
     */
    //@Bean
    public SimpleMessageListenerContainer xxlMessageListenerContainer(ConnectionFactory connectionFactory, XxlMQTransationMessageListener listener) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueues(bizQueue());
        //设置消费者的consumerTag_tag
        simpleMessageListenerContainer.setConsumerTagStrategy(queue -> "xxl.queue");
        //设置消费者的Arguments
        Map<String, Object> args = Maps.newHashMap();
        args.put("module","xxl模块");
        args.put("funtion","向rabbitmq发送消息");
        simpleMessageListenerContainer.setConsumerArguments(args);
        simpleMessageListenerContainer.setMessageListener(listener);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        return simpleMessageListenerContainer;
    }

}

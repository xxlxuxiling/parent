package com.schcilin.mqtransation.consumer;

import com.schcilin.mqtransation.listener.AbstractRabbitMQMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/5/28 17:58
 * @Content:
 */

@Slf4j
//@Component
public class XxlMQTransationMessageListener extends AbstractRabbitMQMessageListener {

    @Override
    public void receiveMsg(Message message) throws Exception {

        System.out.println("成功消费消息xxl");
    }
}

package com.schcilin.goods.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.BaseInfoFeignClient;
import com.schcilin.goods.service.TGoodsService;
import com.schcilin.mqtransation.listener.AbstractRabbitMQMessageListener;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: schcilin
 * @Date: 2019/5/28 17:58
 * @Content:
 */

@Slf4j
@Component
public class BizMQTransationMessageListener extends AbstractRabbitMQMessageListener {
    @Autowired
    private TGoodsService tGoodsService;
    @Autowired
    private BaseInfoFeignClient baseInfoFeignClient;

    @Autowired
    HttpServletRequest servletRequest;

    @Override
    public void receiveMsg(Message message) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String messaged = new String(message.getBody());
            RabbitMetaMessage rabbitMetaMessage = mapper.readValue(messaged.getBytes("utf-8"), RabbitMetaMessage.class);
            String bizMsg = rabbitMetaMessage.getBizMsg();
            TGoods tGoods = mapper.readValue(bizMsg.getBytes("utf-8"), TGoods.class);
     /*       tGoods.setId(String.valueOf(Math.random()));
            tGoods.setGoodName("测试回调" + tGoods.getGoodName());
            tGoodsService.add(tGoods);*/
            System.out.println(servletRequest.getRequestURL());
            baseInfoFeignClient.addUser();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

package com.schcilin.goods.consumer;

import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.BaseInfoFeignClient;
import com.schcilin.goods.service.TGoodsService;
import com.schcilin.mqtransation.listener.AbstractRabbitMQMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Override
    public void receiveMsg(Message message) throws Exception {
    try {
        System.out.println(message);
        TGoods tGoods = new TGoods();
        tGoods.setId(String.valueOf(Math.random()));
        tGoods.setGoodName("测试回调"+Math.random()*100);
        tGoodsService.add(tGoods);
    }catch (Exception e){
        e.printStackTrace();
        throw  e;
    }
    }
}

package com.schcilin.payserver.strategy;

import com.schcilin.payserver.factory.PayChannelFactory;

import java.math.BigDecimal;

/**
 * @Author: schcilin
 * @Date: 2019/7/6 22:48
 * @Version 1.0
 * @des: 支付接口上下文
 */
public class PayContext {
    /**
     * 根据上文的渠道ID,得到下文的某个具体实现类
     * @param channelId
     * @param goodsId
     * @return
     */
   public BigDecimal calculateRecharge(String channelId, String goodsId) throws Exception {
        //此时会遇到问题,需要一个类来帮我们创建某个实现类(工厂设计模式)
        PayChannelFactory payChannelFactory = PayChannelFactory.getInstance();
        PayStrategy strategy = payChannelFactory.create(channelId);
        BigDecimal bigDecimal = strategy.calculateRecharge(channelId, goodsId);
        return bigDecimal;


    }
}

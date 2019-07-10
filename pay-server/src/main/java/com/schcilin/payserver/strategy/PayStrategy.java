package com.schcilin.payserver.strategy;

import java.math.BigDecimal;

/**
 * @Author: schcilin
 * @Date: 2019/7/6 22:37
 * @Version 1.0
 * @des: 支付策略
 */
public interface PayStrategy {
    /**
     * 根据不同的渠道查找不同的优惠信息
     * @param channelId 渠道ID
     * @param goodsId 商品ID
     * @return
     */
    BigDecimal calculateRecharge(String channelId, String goodsId);

}

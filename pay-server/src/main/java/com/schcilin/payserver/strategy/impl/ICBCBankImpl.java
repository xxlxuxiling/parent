package com.schcilin.payserver.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.schcilin.payserver.entity.PayChannel;
import com.schcilin.payserver.mapper.PayChannelMapper;
import com.schcilin.payserver.strategy.Pay;
import com.schcilin.payserver.strategy.PayStrategy;
import com.schcilin.payserver.util.ProjectBeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Author: schcilin
 * @Date: 2019/7/6 22:41
 * @Version 1.0
 * @des:
 */
@Service
@Pay(channelId = "1")
public class ICBCBankImpl extends ProjectBeanUtils implements PayStrategy {
    @Resource
    private PayChannelMapper payChannelMapper;
    @Override
    public BigDecimal calculateRecharge(String channelId, String goodsId) {
        PayChannel payChannel = payChannelMapper.selectById(channelId);
        IPage page = new Page(1,1);
        QueryWrapper<PayChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayChannel::getName,"sss");

        IPage iPage = payChannelMapper.selectPage(page, queryWrapper);

        return null;
    }
}

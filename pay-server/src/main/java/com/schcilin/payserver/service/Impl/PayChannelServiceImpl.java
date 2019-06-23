package com.schcilin.payserver.service.Impl;

import com.schcilin.payserver.entity.PayChannel;
import com.schcilin.payserver.mapper.PayChannelMapper;
import com.schcilin.payserver.service.PayChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author schcilin
 * @since 2019-06-23
 */
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {

}

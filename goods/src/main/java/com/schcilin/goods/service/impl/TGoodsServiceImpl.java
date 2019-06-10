package com.schcilin.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.mapper.TGoodsMapper;
import com.schcilin.goods.service.TGoodsService;
import com.schcilin.mqtransation.anno.MQTransationMessageAnno;
import com.schcilin.mqtransation.constant.MQConstant;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author schcilin
 * @since 2019-06-04
 */
@Service
public class TGoodsServiceImpl   extends ServiceImpl<TGoodsMapper, TGoods> implements  TGoodsService {

    @MQTransationMessageAnno(exchange = MQConstant.BIZ_EXCHANGE+",xxl.exchange", bizName ="#tGoods.getId()",bindingQueue =MQConstant.BIZ_QUEUE+",xxl.queue" , bindingKey = MQConstant.BIZ_ROUTINGKEY+",xxl.route", dbCoordinator = MQConstant.RedisDBCoordinator)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertModel(TGoods tGoods,String test) {
        this.baseMapper.insert(tGoods);


    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TGoods tGoods) throws Exception {
        this.baseMapper.insert(tGoods);
    }
}

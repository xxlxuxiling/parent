package com.schcilin.goods.service;

import com.schcilin.goods.entity.TGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author schcilin
 * @since 2019-06-04
 */
public interface TGoodsService extends IService<TGoods> {


    void insertModel(TGoods tGoods);

    void add(TGoods tGoods) throws Exception;
}

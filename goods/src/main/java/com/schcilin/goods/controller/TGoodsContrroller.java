package com.schcilin.goods.controller;


import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.BaseInfoFeignClient;
import com.schcilin.goods.service.TGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author schcilin
 * @since 2019-06-04
 */
@Controller
@RequestMapping("/tGoods")
public class TGoodsContrroller {
    @Autowired
    private TGoodsService tGoodsService;

    @Autowired
    private BaseInfoFeignClient baseInfoFeignClient;
    @PostMapping("/add")
    public void add(){
        TGoods tGoods = new TGoods();
        tGoods.setId(String.valueOf(Math.random()));
        tGoods.setGoodName("ssss");
        tGoodsService.insertModel(tGoods,"1");
    }
    @PostMapping("/sss")
    public void sss(){
        baseInfoFeignClient.addUser();
    }

}


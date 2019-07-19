package com.schcilin.goods.controller;


import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.BaseInfoFeignClient;
import com.schcilin.goods.service.TGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author schcilin
 * @since 2019-06-04
 */
@RestController
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
        tGoods.setGoodName("哈哈哈");
        tGoodsService.insertModel(tGoods,"1");
    }
    @GetMapping("/sss")
    public String sss(){

        return "c";
        //baseInfoFeignClient.addUser(null);
    }

}


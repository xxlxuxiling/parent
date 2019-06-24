package com.schcilin.goods.feign;

import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.impl.BaseInfoFeignClientImpl;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;

/**
 * @Author: schcilin
 * @Date: 2019/6/4 15:25
 * @Content:
 */
@FeignClient(qualifier = "baseInfoFeignClientImpl", name = "basicInfo", fallback = BaseInfoFeignClientImpl.class)
public interface BaseInfoFeignClient {
    /**
     * springboot 2.1.x版本才支持
     * @param tGoods
     */
    @RequestLine("GET /baseUser/add")
    void addUser(@SpringQueryMap TGoods tGoods);
}

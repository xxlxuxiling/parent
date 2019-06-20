package com.schcilin.goods.feign;

import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.feign.impl.BaseInfoFeignClientImpl;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: schcilin
 * @Date: 2019/6/4 15:25
 * @Content:
 */
@FeignClient(qualifier = "baseInfoFeignClientImpl", name = "basicInfo", fallback = BaseInfoFeignClientImpl.class)
public interface BaseInfoFeignClient {
    @RequestLine("GET /baseUser/add")
    void addUser(@SpringQueryMap TGoods tGoods);
}

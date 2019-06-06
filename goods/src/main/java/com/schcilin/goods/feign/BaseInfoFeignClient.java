package com.schcilin.goods.feign;

import com.schcilin.goods.feign.impl.BaseInfoFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: schcilin
 * @Date: 2019/6/4 15:25
 * @Content:
 */
@FeignClient(qualifier = "baseInfoFeignClientImpl", name = "basicInfo", fallback = BaseInfoFeignClientImpl.class)
public interface BaseInfoFeignClient {
    @GetMapping("/baseUser/add")
    void addUser();
}

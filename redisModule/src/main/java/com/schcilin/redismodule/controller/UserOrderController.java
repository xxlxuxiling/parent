package com.schcilin.redismodule.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther:
 * @Date: 2018/11/22 21:03
 * @Description: 测试redis分布式锁
 */
@Slf4j
@RestController("/redis")
public class UserOrderController {
    @PostMapping(value = "/order", produces = "application/json")
    public String order() {
        return null;
    }
}

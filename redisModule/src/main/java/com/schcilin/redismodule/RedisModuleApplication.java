package com.schcilin.redismodule;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableCaching
public class RedisModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisModuleApplication.class, args);
    }
}

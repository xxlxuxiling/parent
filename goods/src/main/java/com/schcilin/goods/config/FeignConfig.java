package com.schcilin.goods.config;

import feign.Contract;
import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: schcilin
 * @Date: 2019/6/24 12:02
 * @Content: 解决Method xxx not annotated with HTTP method type (ex. GET, POST)
 */

@Configuration
public class FeignConfig {
    @Bean
    public Contract customerContract(){
        return new feign.Contract.Default();
    }
}

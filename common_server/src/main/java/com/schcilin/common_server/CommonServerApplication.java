package com.schcilin.common_server;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;


/**
 * 公共模块，包换工具类
 */
@SpringCloudApplication
public class CommonServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonServerApplication.class, args);
    }

}

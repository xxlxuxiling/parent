package com.schcilin.mqtransation;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
@EnableCaching
@SpringCloudApplication
public class MqTransationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqTransationApplication.class, args);
    }

}

package com.schcilin.payserver;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class PayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayServerApplication.class, args);
    }

}

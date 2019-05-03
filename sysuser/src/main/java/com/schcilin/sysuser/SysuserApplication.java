package com.schcilin.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableCaching
public class SysuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysuserApplication.class, args);
    }

}

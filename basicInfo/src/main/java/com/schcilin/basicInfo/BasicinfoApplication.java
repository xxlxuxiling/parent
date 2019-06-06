package com.schcilin.basicInfo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringCloudApplication
@EnableFeignClients
@EnableTransactionManagement
@MapperScan(basePackages ="com.schcilin.basicInfo.mapper")
public class BasicinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicinfoApplication.class, args);
    }

}


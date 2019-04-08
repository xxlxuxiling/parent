package com.schcilin.tcctransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringCloudApplication//已经包含服务发现
@EnableCaching
@EnableTransactionManagement
public class TccTransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccTransactionsApplication.class, args);
    }

}

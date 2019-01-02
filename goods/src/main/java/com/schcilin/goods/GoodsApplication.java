package com.schcilin.goods;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringCloudApplication
@EnableTransactionManagement//开启事务
@RestController
public class GoodsApplication {
    @Value("${app.name.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
    @GetMapping("/test")
    public String test() {
        return name;
    }
   // @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);//一个配置文件中找不到继续找
        return c;
    }

}


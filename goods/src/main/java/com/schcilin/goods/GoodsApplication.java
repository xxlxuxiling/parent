package com.schcilin.goods;

import com.schcilin.mqtransation.anno.MQTransationMessageAnno;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableEncryptableProperties//开启数据库加密配置，不开启访问时需要密码错误
@SpringCloudApplication
@EnableFeignClients
@EnableTransactionManagement//开启事务
@EnableAspectJAutoProxy(proxyTargetClass = true)//开启切面代理
@ComponentScan(basePackages = "com.schcilin")//在com.schcilin这里面的包进行扫描
public class GoodsApplication {

   // @Value("${app.name.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

   // @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);//一个配置文件中找不到继续找
        return c;
    }

}


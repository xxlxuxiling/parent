package com.schcilin.payserver;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@EnableEncryptableProperties//开启数据库加密配置，不开启访问时需要密码错误
@SpringCloudApplication
@EnableFeignClients
@EnableTransactionManagement//开启事务
@EnableAspectJAutoProxy(proxyTargetClass = true)//开启切面代理
@ComponentScan(basePackages = "com.schcilin")//在com.schcilin这里面的包进行扫描
public class PayServerApplication {


    public static void main(String[] args) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        Object impl = context.getBean("payChannelServiceImpl");
        System.out.println("获取spring 管理的bean==>"+impl);
        SpringApplication.run(PayServerApplication.class, args);
    }

}

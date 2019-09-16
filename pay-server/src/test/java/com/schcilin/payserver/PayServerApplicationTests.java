package com.schcilin.payserver;

import com.schcilin.payserver.service.PayChannelService;
import com.schcilin.payserver.strategy.PayContext;
import com.schcilin.payserver.strategy.PayStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServerApplicationTests {
    @Autowired
    WebApplicationContext webApplicationConnect;
    @Value("${my.secret}")
    private String value;

    @Autowired
    private PayChannelService payChannelService;


    @Test
    public void contextLoads() {
        Object impl = webApplicationConnect.getBean("payChannelServiceImpl");
        System.out.println("获取spring 管理的bean==>" + impl);
    }

    @Test
    public void contextLoadsxx() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        payChannelService.test();
    }

    @Test
    public void contextLoadRe() throws Exception {
        PayContext payContext = new PayContext();
        BigDecimal bigDecimal = payContext.calculateRecharge("1", "1");
        System.out.println(bigDecimal);
    }

    @Test
    public void contextLoadExc() throws Exception {
      //payChannelService.testException();
        System.out.println(value);
    }

}

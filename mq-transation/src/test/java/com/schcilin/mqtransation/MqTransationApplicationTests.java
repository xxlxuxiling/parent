package com.schcilin.mqtransation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTransationApplicationTests {

    @Autowired
    private com.schcilin.mqtransation.Test test;

    @Test
    public void contextLoads() {
        test.test();
    }
    @Test
    public void testXxl() {
        test.testXxl();
    }


}

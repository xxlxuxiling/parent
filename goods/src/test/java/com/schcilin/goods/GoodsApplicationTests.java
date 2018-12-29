package com.schcilin.goods;

import com.schcilin.goods.entity.ZbglIncident;
import com.schcilin.goods.mapper.ZbglIncidentMapper;
import com.schcilin.goods.service.ZbglIncidentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsApplicationTests {
    @Autowired
    private ZbglIncidentMapper zbglIncidentMapper;



    @Test
    public  void testDemo() {
       // ZbglIncident byId = zbglIncidentService.getById("0252993f02114f70952176aa6b3e21ff");
        ZbglIncident zbglIncident = zbglIncidentMapper.selectById("0252993f02114f70952176aa6b3e21ff");
        System.out.println(zbglIncident);

    }

}


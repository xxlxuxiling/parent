package com.schcilin.redismodule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisModuleApplicationTests {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("redisKey","cluster test");
        System.out.println("11"+opsForValue.get("test"));
    }


}

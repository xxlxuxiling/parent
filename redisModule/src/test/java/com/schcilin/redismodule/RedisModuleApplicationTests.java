package com.schcilin.redismodule;

import com.schcilin.redismodule.service.RedisDistributedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisModuleApplicationTests {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedisDistributedService redisDistributedService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("redisKey", "cluster test");
        System.out.println("11" + opsForValue.get("test"));
    }

    /**
     * 模拟高并发的情景，买车票，测试高并发，使用CountDownLatch计数器
     */
    @Test
    public void testRedisLock() throws InterruptedException {
        long start = System.currentTimeMillis();

        final CountDownLatch countDownLatch = new CountDownLatch(500);
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 500; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //获取到分布式锁，执行操作
                   // redisDistributedService.tryDistributedLock();
                    //统计
                    countDownLatch.countDown();
                }
            });

        }
        //模拟到500个线程
        countDownLatch.await();
        long end = System.currentTimeMillis();
        long result= end-start;


    }

}

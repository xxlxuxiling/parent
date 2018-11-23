package com.schcilin.redismodule.controller;

import com.schcilin.redismodule.distributedLock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:
 * @Date: 2018/11/22 21:03
 * @Description: 测试redis分布式锁
 */
@Slf4j
@RestController("/redis")
public class UserOrderController {
    @Autowired
    private DistributedLock distributedLock;


    @PostMapping(value = "/order", produces = "application/json")
    public String order() {
        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        boolean isGetLock = distributedLock.tryLock(key, TimeUnit.SECONDS, 5L, 10L); //尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        Future<Boolean> lockAsync = distributedLock.tryLockAsync(key, TimeUnit.SECONDS, 5L, 10L);//尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        if (lockAsync.get()) {
                            try {

                                Thread.sleep(100); //获得锁之后可以进行相应的处理
                                System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
                            } finally {

                                distributedLock.unLock(key);
                            }
                            System.err.println("=============================" + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        return null;
    }
}

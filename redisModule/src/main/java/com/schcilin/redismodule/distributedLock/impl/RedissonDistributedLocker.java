package com.schcilin.redismodule.distributedLock.impl;

import com.schcilin.redismodule.distributedLock.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:
 * @Date: 2018/11/23 15:01
 * @Description: 分布式锁的实现
 */
@Component
public class RedissonDistributedLocker implements DistributedLock {
    //RedissonClient已经由配置类生成
    @Autowired
    private RedissonClient redissonClient;

    /**
     * lock(), 拿不到lock就不罢休，不然线程就一直block
     *
     * @param lockKey
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * timeOut为加锁时间，单位为秒
     *
     * @param lockKey
     * @param timeOut
     * @return
     */
    @Override
    public RLock lock(String lockKey, long timeOut) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeOut, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 单位由客户端定
     *
     * @param lockKey
     * @param unit
     * @param timeOut
     * @return
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeOut) {

        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeOut, unit);
        return lock;
    }

    /**
     * tryLock()，马上返回，拿到lock就返回true，不然返回false。
     * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     *
     * @param lockKey
     * @param unit
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long releaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, releaseTime, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Future<Boolean> tryLockAsync(String lockKey, TimeUnit unit, long waitTime, long releaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try{
            Future<Boolean> res = lock.tryLockAsync(waitTime, releaseTime, TimeUnit.SECONDS);
           return res;
        }  finally {
            lock.unlock();
        }
    }

    @Override
    public void unLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unLock(RLock lock) {
        lock.unlock();
    }


}

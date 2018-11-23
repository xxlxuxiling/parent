package com.schcilin.redismodule.distributedLock;

import org.redisson.api.RLock;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:
 * @Date: 2018/11/23 14:46
 * @Description: Redisson集群的分布式锁
 */
public interface DistributedLock {
    /**
     * 根据key获取锁
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 获取锁
     * @param lockKey
     * @param timeOut
     * @return
     */
    RLock lock(String lockKey,long timeOut);

    /**
     * 获取锁
     * @param lockKey
     * @param unit
     * @param timeOut
     * @return
     */
    RLock lock(String lockKey, TimeUnit unit, long timeOut);

    /**
     *
     * @param lockKey
     * @param unit
     * @param waitTime 等待时间
     * @param releaseTime 释放时间
     * @return
     */
    boolean tryLock(String lockKey,TimeUnit unit,long waitTime,long releaseTime);

    /**
     * Redisson同时还为分布式锁提供了异步执行
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param releaseTime
     * @return 通过get(方法获取值)
     */
    Future<Boolean> tryLockAsync(String lockKey, TimeUnit unit, long waitTime, long releaseTime);

    /**
     * 根据key来释放锁
     * @param lockKey
     */
    void unLock(String lockKey);

    /**
     * 根据锁来释放
     * @param lock
     */
    void unLock(RLock lock);
}

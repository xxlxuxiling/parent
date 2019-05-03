package com.schcilin.redismodule.cache.impl;

import com.schcilin.redismodule.cache.VerificationCodeCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存，会自动过期
 *
 * @Author: schcilin
 * @Date: 2019/5/3 16:32
 * @Version 1.0
 * @des:
 */
@Component
public class RedisVerificationCodeCache implements VerificationCodeCache {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public RedisVerificationCodeCache(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

    /**
     * 存在缓存操作不一致性问题
     *
     * @param cacheName
     * @param key
     * @param value
     * @param expire
     */
    @Override
    public void set(String cacheName, String key, String value, long expire) {
        redisTemplate.opsForValue().set(cacheName + ":" + key, value);
        redisTemplate.expire(cacheName + ":" + key, expire, TimeUnit.MILLISECONDS);

    }

    @Override
    public void remove(String cacheName, String key) {
        redisTemplate.delete(cacheName + ":" + key);

    }

    @Override
    public String get(String cacheName, String key) {
        return redisTemplate.opsForValue().get(cacheName + ":" + key);
    }

    /**
     * 校验value值是否一致
     *
     * @param cacheName
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean validate(String cacheName, String key, String value) {
        String originValue = this.get(cacheName, key);
        if (StringUtils.isNotEmpty(originValue)) {
            return originValue.equals(value);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean isExpire(String cacheName, String key) {
        return this.redisTemplate.opsForValue().get(cacheName + ":" + key) == null;
    }
}

package com.schcilin.redismodule.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * @Auther:
 * @Date: 2018/11/21 15:42
 * @Description:
 */
public class RedisServerUtil {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    /**
     * 设置缓存对象
     *
     * @param key 缓存key
     * @param obj 缓存value
     */
    public <T> void setObject(String key, T obj, int expireTime) {
        jedisCluster.setex(key, expireTime, JSON.toJSONString(obj));
    }

    /**
     * 获取指定key的缓存
     *
     * @param key---JSON.parseObject(value, User.class);
     */
    public String getObject(String key) {
        return jedisCluster.get(key);
    }

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    public boolean hasKey(String key) {
        return jedisCluster.exists(key);
    }


    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public void setWithExpireTime(String key, String value, int expireTime) {
        jedisCluster.setex(key, expireTime, value);

    }


    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    public String get(String key) {
        String value = jedisCluster.get(key);
        return value;
    }

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    public void delete(String key) {
        jedisCluster.del(key);
    }


}

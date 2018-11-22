package com.schcilin.redismodule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**redis单机版的分布式锁
 * @Auther:
 * @Date: 2018/11/22 13:20
 * @Description: redis分布式锁
 */
@Slf4j
@Component
public class RedisDistributedService {

    private static final String LOCKED_SUCCESS = "OK";
    private static final String NX = "NX";
    private static final String EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 获取分布式锁
     *
     * @param jedis
     * @param lockkey
     * @param uniqueId   请求标识
     * @param expireTime
     * @return 是否获取到锁
     */
    public  boolean tryDistributedLock(Jedis jedis, String lockkey, String uniqueId, long expireTime) {
        String result = jedis.set(lockkey, uniqueId, NX, EXPIRE_TIME, expireTime);
        return LOCKED_SUCCESS.equals(result);

    }

    /**
     *释放分布式锁
     * @param jedis
     * @param lockkey
     * @param uniqueId
     * @return 是否释放锁
     */
    public  boolean releaseLock(Jedis jedis, String lockkey, String uniqueId) {
        //lua脚本
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(luaScript, Collections.singletonList(lockkey), Collections.singletonList(uniqueId));
       //存在问题： Client A去加锁lockKey，然后释放锁，在执行del(lockKey)之前，这时lockKey锁expire到期失效了，此时Client B尝试加锁lockKey成功，Client A接着执行释放锁操作(del)，便释放了Client B的锁.
        /*Object result="";
        if (uniqueId.equals(jedis.get(lockkey))) {
            result = jedis.del(lockkey);
        }*/

        return RELEASE_SUCCESS.equals(result);
    }

}

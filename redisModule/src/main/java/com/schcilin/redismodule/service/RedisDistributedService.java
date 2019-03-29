package com.schcilin.redismodule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**redis单机版的分布式锁
 *
 *1 互斥性。在任意时刻，只有一个客户端能持有锁。
 * 2不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 * 3具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 * 4解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 *
 *
 * @Auther:
 * @Date: 2018/11/22 13:20
 * @Description: redis分布式锁
 */
@Slf4j
@Component
public class RedisDistributedService {

    private static final String LOCKED_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST  = "NX";
    private static final String EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 获取分布式锁
     *第一个为key，我们使用key来当锁，因为key是唯一的。
     *
     * 第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成。
     *
     * 第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
     *
     * 第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
     *
     * 第五个为time，与第四个参数相呼应，代表key的过期时间。
     *
     * 总的来说，执行上面的set()方法就只会导致两种结果：1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。2. 已有锁存在，不做任何操作。
     *
     * 心细的童鞋就会发现了，我们的加锁代码满足我们可靠性里描述的三个条件。首先，set()加入了NX参数，可以保证如果已有key存在，则函数不会调用成功，也就是只有一个客户端能持有锁，满足互斥性。其次，由于我们对锁设置了过期时间，即使锁的持有者后续发生崩溃而没有解锁，锁也会因为到了过期时间而自动解锁（即key被删除），不会发生死锁。最后，因为我们将value赋值为requestId，代表加锁的客户端请求标识，那么在客户端在解锁的时候就可以进行校验是否是同一个客户端。由于我们只考虑Redis单机部署的场景，所以容错性我们暂不考虑
     * @param jedis
     * @param lockkey
     * @param uniqueId   请求标识
     * @param expireTime
     * @return 是否获取到锁
     */
    public  boolean tryDistributedLock(Jedis jedis, String lockkey, String uniqueId, long expireTime) {
        String result = jedis.set(lockkey, uniqueId, SET_IF_NOT_EXIST, EXPIRE_TIME, expireTime);
        return LOCKED_SUCCESS.equals(result);

    }

    /**
     *释放分布式锁
     * @param jedis
     * @param lockkey
     * @param uniqueId 请求标志
     * @return 是否释放锁
     */
    public  boolean releaseLock(Jedis jedis, String lockkey, String uniqueId) {
        //lua脚本
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(luaScript, Collections.singletonList(lockkey), Collections.singletonList(uniqueId));
       //如下释放分布式锁存在问题： Client A去加锁lockKey，然后释放锁，在执行del(lockKey)之前，这时lockKey锁expire到期失效了，此时Client B尝试加锁lockKey成功，Client A接着执行释放锁操作(del)，便释放了Client B的锁.
        /*Object result="";
        if (uniqueId.equals(jedis.get(lockkey))) {
            result = jedis.del(lockkey);
        }*/

        return RELEASE_SUCCESS.equals(result);
    }

}

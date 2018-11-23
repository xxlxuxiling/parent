package com.schcilin.redismodule.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Auther:
 * @Date: 2018/11/23 12:17
 * @Description: Redisson实现的redis集群配置
 */
@Configuration
@Slf4j
public class RedissonClusterConfig {
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    @Bean
    public RedissonClient getRedissonClient() throws IOException {
        String[] nodes = clusterNodes.split(",");
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        Arrays.asList(nodes).stream().forEach(node -> node = "redis://" + node);
        RedissonClient redissonClient = null;
        Config config = new Config();
        config.useClusterServers()//这里用的是集群server
                .setScanInterval(2000)//设置集群状态扫描时间Redis cluster scan interval in milliseconds
                .addNodeAddress(nodes);
        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
        redissonClient = Redisson.create(config);
        log.info(redissonClient.getConfig().toJSON());
        return redissonClient;


    }
}

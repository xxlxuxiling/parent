package com.schcilin.mqtransation.coordinator.impl;/**
 * @Author: schcilin
 * @Date: 2019/5/23 16:22
 * @Content:
 */

import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.coordinator.DBCoordinator;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @version v1.0
 * @Author schcilin
 * @Description //TODO $
 * @Date $ $
 **/
@Component("RedisDBCoordinator")
@Slf4j
public class RedisDBCoordinatorImpl implements DBCoordinator {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void setMsgPrepare(String msgId) {
        redisTemplate.opsForSet().add(MQConstant.MQ_MSG_PREPARE, msgId);

    }

    @Override
    public void deleteMsgPrepare(String msgId) {
        redisTemplate.opsForSet().remove(MQConstant.MQ_MSG_PREPARE, msgId);
    }

    /**
     * 不是原子操作，会不会有问题？
     *
     * @param msgId
     * @param rabbitMetaMessage
     */
    @Override
    public void setMsgReady(String msgId, RabbitMetaMessage rabbitMetaMessage) {
        redisTemplate.opsForHash().put(MQConstant.MQ_MSG_READY, msgId, rabbitMetaMessage);
        redisTemplate.opsForSet().remove(MQConstant.MQ_MSG_PREPARE, msgId);

    }

    /**
     * 发送消息成功就要删除预备信息
     *
     * @param msgId
     */
    @Override
    public void setMsgSuccess(String msgId) {
        redisTemplate.opsForHash().delete(MQConstant.MQ_MSG_READY, msgId);
    }

    @Override
    public RabbitMetaMessage getMetaMsg(String msgId) {
        return (RabbitMetaMessage) redisTemplate.opsForHash().get(MQConstant.MQ_MSG_READY, msgId);
    }

    @Override
    public List<RabbitMetaMessage> getMsgReady() throws Exception {
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<RabbitMetaMessage> messages = hashOperations.values(MQConstant.MQ_MSG_READY);
        List<RabbitMetaMessage> messageAlert = new ArrayList();
        List<String> messageIds = new ArrayList<>();
        for(RabbitMetaMessage message : messages){
            /**如果消息超时，加入超时队列*/
            if(messageTimeOut(message.getMessageId())){
                messageAlert.add(message);
                messageIds.add(message.getMessageId());
            }
        }
        /**在redis中删除已超时的消息*/
        hashOperations.delete(MQConstant.MQ_MSG_READY, messageIds);
        return messageAlert;
    }


    @Override
    public List getMsgPrepare() throws Exception {
        SetOperations setOperations = redisTemplate.opsForSet();
        Set<String> messageIds = setOperations.members(MQConstant.MQ_MSG_PREPARE);
        List<String> messageAlert = new ArrayList();
        for(String messageId: messageIds){
            /**如果消息超时，加入超时队列*/
            if(messageTimeOut(messageId)){
                messageAlert.add(messageId);
            }
        }
        /**在redis中删除已超时的消息*/
        setOperations.remove(MQConstant.MQ_MSG_READY,messageAlert);
        return messageAlert;
    }

    @Override
    public Long incrResendKey(String key, String hashKey) {
       return redisTemplate.opsForHash().increment(key, hashKey, 1);
    }

    @Override
    public Long getResendValue(String key, String hashKey) {
        return this.redisTemplate.opsForHash().increment(key, hashKey,1L);
    }


    @Override
    public Long deleteResendKey(String key, String hashKey) {
        return this.redisTemplate.opsForHash().delete(key,hashKey);
    }

    boolean messageTimeOut(String messageId) throws Exception {
        String messageTime = (messageId.split(MQConstant.DB_SPLIT))[1];
        long timeGap = System.currentTimeMillis() -
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(messageTime).getTime();
        if (timeGap > MQConstant.TIME_GAP) {
            return true;
        }
        return false;
    }
}

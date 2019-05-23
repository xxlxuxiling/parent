package com.schcilin.mqtransation.coordinator;

import com.schcilin.mqtransation.pojo.RabbitMetaMessage;

import java.util.List;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 16:17
 * @Content: 保存MQ信息的协调器
 */
public interface DBCoordinator {

    /**设置消息为prepare状态*/
    void setMsgPrepare(String msgId);

    /**设置消息为ready状态，删除prepare状态*/
    void setMsgReady(String msgId, RabbitMetaMessage rabbitMetaMessage);

    /**消息发送成功，删除ready状态消息*/
    void setMsgSuccess(String msgId);

    /**从db中获取消息实体*/
    RabbitMetaMessage getMetaMsg(String msgId);

    /**获取ready状态消息*/
    List getMsgReady() throws Exception;

    /**获取prepare状态消息*/
    List getMsgPrepare() throws Exception;

    /**消息重发次数+1*/
    Long incrResendKey(String key, String hashKey);

    Long getResendValue(String key, String hashKey);
}

package com.schcilin.mqtransation.constant;/**
 * @Author: schcilin
 * @Date: 2019/5/23 16:04
 * @Content:
 */

public class MQConstant {
    /** 缓存超时时间,超时进行重发 */
    public static final long TIME_GAP = 2000;
    /** 分隔符*/
    public static final String DB_SPLIT = ",";

    /**处于prepare状态消息*/
    public static final Object MQ_MSG_PREPARE = "mq.msg.prepare";

    /**处于ready状态消息*/
    public static final Object MQ_MSG_READY = "mq.msg.ready";
}

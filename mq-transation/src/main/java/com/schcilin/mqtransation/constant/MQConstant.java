package com.schcilin.mqtransation.constant;/**
 * @Author: schcilin
 * @Date: 2019/5/23 16:04
 * @Content:
 */

public class MQConstant {
    /** 消息重发计数*/
    public static final String MQ_RESEND_COUNTER = "mq.resend.counter";
    /** 消息最大重发次数，网络卡顿等问题*/
    public static final long MAX_RETRY_COUNT = 3;

    /** 缓存超时时间,超时进行重发 */
    public static final long TIME_GAP = 2000;
    /** 分隔符*/
    public static final String DB_SPLIT = ",";

    /**处于prepare状态消息*/
    public static final Object MQ_MSG_PREPARE = "mq.msg.prepare";

    /**处于ready状态消息*/
    public static final Object MQ_MSG_READY = "mq.msg.ready";
    /**消费者重试统计key*/
    public static final String MQ_CONSUMER_RETRY_COUNT_KEY = "mq.consumer.retry.count.key";
}

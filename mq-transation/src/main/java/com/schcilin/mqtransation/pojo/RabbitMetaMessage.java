package com.schcilin.mqtransation.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 16:14
 * @Content: 自定义消息体，可以根据系统业务扩展，比如说:服务名
 */

@Setter
@Getter
public class RabbitMetaMessage implements Serializable {
    private static final long serialVersionUID = -1097321000102904701L;
    /** 业务ID*/
    String messageId;
    /**交换机*/
    String exchange;
    /**路由*/
    String routingKey;
    /***/
    Object payload;
}

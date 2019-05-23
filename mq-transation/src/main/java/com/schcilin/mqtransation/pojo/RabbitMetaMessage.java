package com.schcilin.mqtransation.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 16:14
 * @Content:
 */

@Setter
@Getter
public class RabbitMetaMessage implements Serializable {
    private static final long serialVersionUID = -1097321000102904701L;
    String messageId;
    String exchange;
    String routingKey;
    Object payload;
}

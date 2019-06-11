package com.schcilin.mqtransation.anno;

import java.lang.annotation.*;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 14:35
 * @Content: 使用注解用来无侵入的实现分布式事务
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MQTransationMessageAnno {
    /**
     * 要发送的交换机，多个以,隔开
     */
    String exchange() default "";

    /**
     * 要发送的路由key，多个以,隔开
     */
    String bindingKey() default "";
    /**
     * 要绑定队列，多个以,隔开
     */
    String bindingQueue() default "";

    /**
     * 业务名称
     */
    String bizName() default "";

    /**
     * 消息落库的处理方式db or redis
     */
    String dbCoordinator() default "";


}

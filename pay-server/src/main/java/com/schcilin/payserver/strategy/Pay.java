package com.schcilin.payserver.strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: schcilin
 * @Date: 2019/7/6 23:07
 * @Version 1.0
 * @des: 维护具体银行实现类的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//可以使用反射获取
public @interface Pay {
    /**
     * 根据channelId获取具体实现类
     * @return
     */
    String channelId();
}

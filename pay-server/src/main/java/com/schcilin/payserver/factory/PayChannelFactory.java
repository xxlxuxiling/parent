package com.schcilin.payserver.factory;

import com.schcilin.payserver.strategy.Pay;
import com.schcilin.payserver.strategy.PayStrategy;
import org.reflections.Reflections;
import sun.reflect.Reflection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: schcilin
 * @Date: 2019/7/6 15:33
 * @Version 1.0
 * @des: 支付工厂类, 主要是用来根据不同的支付渠道, 调用不用的支付实例
 */
public class PayChannelFactory {
    //饿汉式的单例模式没有现成安全问题
    private static PayChannelFactory payChannelFactory = new PayChannelFactory();

    private PayChannelFactory() {
    }

    public static PayChannelFactory getInstance() {
        return payChannelFactory;

    }
    //如果需要懒汉式,可以用双重锁检测


    public static Map<String, String> implMap = new HashMap<>();

    static {
        //根据包扫描
        Reflections reflections = new Reflections("com.schcilin.payserver.strategy.impl");
        Set<Class<?>> classSet= reflections.getTypesAnnotatedWith(Pay.class);
        for (Class clzz : classSet) {
            Pay pay =(Pay) clzz.getAnnotation(Pay.class);
            //key为channelId,value为类名
            implMap.put(pay.channelId(),clzz.getCanonicalName());
        }

    }

    /**
     * 根据channel创建具体银行实现类
     *
     * @param channelId
     * @return
     */
    public PayStrategy create(String channelId) throws Exception {
        //1.我们的具体实现类是通过k/v的形式维护的
        //2.map又是通过注解的形式维护的
        String clzzName = implMap.get(channelId);
        Class<?> aClass = Class.forName(clzzName);
        return (PayStrategy) aClass.newInstance();

    }
}

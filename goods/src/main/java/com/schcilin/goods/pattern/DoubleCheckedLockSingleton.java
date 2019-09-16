package com.schcilin.goods.pattern;

/**
 * @Author: schcilin
 * @Date: 2019/9/16 11:38
 * @Content: 双重检查锁
 */

public class DoubleCheckedLockSingleton {
    /**可以粗略的列出3个步骤
     1.首先jvm要为这个对象实例分配内存空间
     2.初始化这个对象
     3.将instance指向内存地址
     由于在处理器阶段时候，会出现系统优化的重排序问题，但要符合一些规则才能重排需，首先是一些关键字不允许中排序等其他，
     其次要保证重排序后的结果要一致（单线程情况下）*/
    private static volatile DoubleCheckedLockSingleton instance;

    private DoubleCheckedLockSingleton() {
    }

    public static DoubleCheckedLockSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLockSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockSingleton();
                }
            }
        }
        return instance;
    }

}

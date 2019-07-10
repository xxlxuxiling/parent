package com.schcilin.payserver.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: schcilin
 * @Date: 2019/6/24 9:56
 * @Content: 项目获取Spring Bean 工具类
 */


public class ProjectBeanUtils implements ApplicationContextAware {
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;

    }
}

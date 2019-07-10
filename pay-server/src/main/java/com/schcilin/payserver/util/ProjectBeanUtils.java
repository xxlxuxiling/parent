package com.schcilin.payserver.util;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @Author: schcilin
 * @Date: 2019/6/24 9:56
 * @Content: 项目获取Spring Bean 工具类
 */
@Component
public class ProjectBeanUtils implements ApplicationContextAware {
    private  static ApplicationContext applicationContext=null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;

    }
    /**把继承该类的类的成员变量(通过spring 管理的bean)注入到继承类中区
     *
     */
    public ProjectBeanUtils(){
        //加载继承该类的类,扫描带注解的成员变量
        Reflections reflections = new Reflections(this.getClass(), new FieldAnnotationsScanner());
        Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(Resource.class);
      try{
          for (Field field : fieldsAnnotatedWith) {
                //获取类成员变量类名
              String simpleName = field.getType().getSimpleName();
              //将首字母小写
              String beanName = StringUtils.uncapitalize(simpleName);
              //根据beanName去applicationContext中获取bean对象
              Object bean = applicationContext.getBean(beanName);
              if (bean == null) {
                  return;
              }
              field.setAccessible(true);
              field.set(this,bean);


          }
      }catch (Exception e){}

    }
}

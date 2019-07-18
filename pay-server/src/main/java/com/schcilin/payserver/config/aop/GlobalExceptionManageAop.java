package com.schcilin.payserver.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/7/15 10:08
 * @Content: 方法执行后，统一异常处理catch中的异常
 */
@Aspect
@Slf4j
@Component
public class GlobalExceptionManageAop {
    private static final String POITCUT_EXPRESSION = "execution(* com.schcilin.payserver.service.*.*(..))";

    @After(value = POITCUT_EXPRESSION)
    public void afterInvokeMethod() {

//        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        try {
            for (StackTraceElement stackTraceElement : stackTrace) {
                Class<? extends StackTraceElement> aClass = stackTraceElement.getClass();
                String canonicalName = aClass.getCanonicalName();
                Class<?> aClass1 = Class.forName(canonicalName);
                Object o = aClass1.newInstance();
            }
        } catch (Exception e) {
            ExceptionUtils.getStackTrace(e);
        }


    }
}

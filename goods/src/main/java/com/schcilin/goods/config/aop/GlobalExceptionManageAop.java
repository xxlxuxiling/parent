package com.schcilin.goods.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Author: schcilin
 * @Date: 2019/7/15 10:08
 * @Content: 方法执行后，统一异常处理catch中的异常
 */
@Aspect
@Slf4j
public class GlobalExceptionManageAop {
    private static final String POITCUT_EXPRESSION = "execution(* com.schcilin.goods.service.*.*(..))";

    @After(POITCUT_EXPRESSION)
    public void afterInvokeMethod(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("测试异常信息",throwable);
        }

    }
}

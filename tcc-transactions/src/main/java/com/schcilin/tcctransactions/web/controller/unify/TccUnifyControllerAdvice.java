package com.schcilin.tcctransactions.web.controller.unify;

import com.google.common.collect.ImmutableMap;
import com.schcilin.tcctransactions.anno.RequestLogging;
import com.schcilin.tcctransactions.constant.ResponseStatusCode;
import com.schcilin.tcctransactions.constant.RestStatus;
import com.schcilin.tcctransactions.exception.RestStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * TCC事务协调处理器controller统一处理器
 */
@ControllerAdvice
@Slf4j
public class TccUnifyControllerAdvice {

    private static final ImmutableMap<Class<? extends Throwable>, RestStatus> EXCEPTION_MAP;


    static {
        final ImmutableMap.Builder<Class<? extends Throwable>, RestStatus> buider = ImmutableMap.builder();
        //SpringMVC中参数类型转换异常，常见于String找不到对应的ENUM而抛出的异常
        buider.put(MethodArgumentTypeMismatchException.class, ResponseStatusCode.INVALID_PARAMS_CONVERSION);
        buider.put(UnsatisfiedServletRequestParameterException.class, ResponseStatusCode.INVALID_PARAMS_CONVERSION);
        // HTTP Request Method不存在
        buider.put(HttpRequestMethodNotSupportedException.class, ResponseStatusCode.REQUEST_METHOD_NOT_SUPPORTED);
        // 要求有RequestBody的地方却传入了NULL
        buider.put(HttpMessageNotReadableException.class, ResponseStatusCode.HTTP_MESSAGE_NOT_READABLE);
        // 其他未被发现的异常
        buider.put(Exception.class, ResponseStatusCode.SERVER_UNKNOWN_ERROR);

        EXCEPTION_MAP = buider.build();
    }


    @ResponseBody
    @RequestLogging
    @ExceptionHandler(RestStatusException.class)
    public Object restStatusException(Exception e, HttpServletRequest request) {
        // 取出存储在Shift设定在Request Scope中的ErrorEntity
        return request.getAttribute(e.getMessage());
    }
}

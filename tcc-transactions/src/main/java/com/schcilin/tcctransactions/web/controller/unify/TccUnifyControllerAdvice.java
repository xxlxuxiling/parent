package com.schcilin.tcctransactions.web.controller.unify;

import com.google.common.collect.ImmutableMap;
import com.schcilin.tcctransactions.anno.RequestLogging;
import com.schcilin.tcctransactions.constant.RequestAttributeConst;
import com.schcilin.tcctransactions.constant.ResponseStatusCode;
import com.schcilin.tcctransactions.constant.RestStatus;
import com.schcilin.tcctransactions.error.ErrorEntity;
import com.schcilin.tcctransactions.error.TccErrorResponse;
import com.schcilin.tcctransactions.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * TCC事务协调处理器controller统一处理器
 */
@ControllerAdvice
@Slf4j
public class TccUnifyControllerAdvice {

    @Value("{spring.application.name}")
    private String applicationName;

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


    @ResponseBody
    @RequestLogging
    @ExceptionHandler(IllegalValidateException.class)
    public Object illegalValidateException(Exception e, HttpServletRequest request) {
        // 取出存储在Request域中的Map
        return request.getAttribute(e.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ReservationExpireException.class, ReservationAlmostToExpireException.class})
    public void expireReservationException(Exception e, HttpServletRequest request) {
    }

    /**
     * 预留资源冲突
     *
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @RequestLogging
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PartialConfirmException.class)
    public Object partialConfirmException(PartialConfirmException e, HttpServletRequest request) {
        final TccErrorResponse error = e.getErrorResponse();
        return new ErrorEntity(ResponseStatusCode.RESERVATION_CONFLICT, error);
    }

    @ResponseBody
    @RequestLogging
    @ExceptionHandler(Exception.class)
    public ErrorEntity exception(Exception e, HttpServletRequest request) {
        log.error("request id: {}\r\nexception: {}", request.getAttribute(RequestAttributeConst.REQUEST_ID), e);
        final RestStatus status = EXCEPTION_MAP.get(e.getClass());
        ErrorEntity error;
        if (status != null) {
            error = new ErrorEntity(status);
        } else {
            // 未知异常
            error = new ErrorEntity(ResponseStatusCode.SERVER_UNKNOWN_ERROR);
            // 检测是否服务还未完全启动
            if (e instanceof IllegalStateException) {
                if (e.getMessage().contains("No instances available for " + applicationName)) {
                    error = new ErrorEntity(ResponseStatusCode.SERVICE_INITIALIZING);
                }
            }
        }
        return error;
    }
}

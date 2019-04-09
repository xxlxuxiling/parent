package com.schcilin.tcctransactions.exception;

import java.io.Serializable;

/**
 * 非法的参数异常
 */
public class IllegalValidateException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = -7024897190745766936L;

    public IllegalValidateException() {
        super();
    }

    public IllegalValidateException(String message) {
        super(message);
    }

    public IllegalValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalValidateException(Throwable cause) {
        super(cause);
    }

    protected IllegalValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

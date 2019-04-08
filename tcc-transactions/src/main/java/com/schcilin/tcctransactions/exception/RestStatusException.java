package com.schcilin.tcctransactions.exception;

/**
 * rest api 统一异常
 */
public class RestStatusException extends RuntimeException {
    static final long serialVersionUID = -7034897190745766930L;

    public RestStatusException(String message) {
        super(message);
    }

    public RestStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestStatusException(Throwable cause) {
        super(cause);
    }

    protected RestStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

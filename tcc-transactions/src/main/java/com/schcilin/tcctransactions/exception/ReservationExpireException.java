package com.schcilin.tcctransactions.exception;

/**
 * Author: schicilin
 * Date: 2019/4/9 13:32
 * Content:
 */
public class ReservationExpireException extends RuntimeException {
    static final long serialVersionUID = -7034897190745766936L;
    public ReservationExpireException() {
        super();
    }

    public ReservationExpireException(String message) {
        super(message);
    }

    public ReservationExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationExpireException(Throwable cause) {
        super(cause);
    }

    protected ReservationExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

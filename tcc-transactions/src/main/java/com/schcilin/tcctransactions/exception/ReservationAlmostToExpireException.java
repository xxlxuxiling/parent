package com.schcilin.tcctransactions.exception;

import java.io.Serializable;

/**
 * Author: schicilin
 * Date: 2019/4/9 18:43
 * Content:
 */
public class ReservationAlmostToExpireException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = -6534318119076185724L;

    public ReservationAlmostToExpireException() {
        super();
    }

    public ReservationAlmostToExpireException(String message) {
        super(message);
    }

    public ReservationAlmostToExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationAlmostToExpireException(Throwable cause) {
        super(cause);
    }

    protected ReservationAlmostToExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

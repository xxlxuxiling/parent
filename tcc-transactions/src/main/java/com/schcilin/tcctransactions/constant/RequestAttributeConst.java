package com.schcilin.tcctransactions.constant;

/**
 * Author: schicilin
 * Date: 2019/4/9 23:15
 * Content: 请求属性常量
 */
public final class RequestAttributeConst {
    public static final String DETAILS_KEY = "X-Logs-Details";
    public static final String REQUEST_BODY_KEY = "X-Request-Body";
    public static final String REQUEST_ID = "X-Request-Id";

    private RequestAttributeConst() {
        throw new IllegalStateException("do not try to use reflection");
    }
}

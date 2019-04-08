package com.schcilin.tcctransactions.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 自定义返回状态码
 */
public interface RestStatus {


    /**
     * the status codes of per restful request.
     *
     * @return 20xxx if succeed, 40xxx if client error, 50xxx if server side crash.
     */
    int code();

    /**
     * @return status enum name
     */
    String name();

    /**
     * @return message summary
     */
    String message();





}

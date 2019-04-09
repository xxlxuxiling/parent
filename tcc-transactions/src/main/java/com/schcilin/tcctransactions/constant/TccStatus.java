package com.schcilin.tcctransactions.constant;

/**
 * Author: schicilin
 * Date: 2019/4/9 21:59
 * Content: 事务管理器状态码
 */
public enum TccStatus {
    TO_BE_CONFIRMED, CONFIRMED, CONFLICT, TIMEOUT
}

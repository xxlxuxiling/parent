package com.schcilin.sysuser.controller;

import com.schcilin.common_server.protocol.Result;
import com.schcilin.common_server.util.consistentHash.util.MultiDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * @Author: schcilin
 * @Date: 2019/4/27 14:20
 * @Version 1.0
 * @des: 基础controller，一个模块一个
 */
public class BaseController {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new MultiDateFormat(), true));

    }

    /**
     * 构建成功结果
     *
     * @param msg
     * @param data
     * @return
     */
    public Result success(String msg, Object data) {
        return Result.build(Boolean.TRUE, msg, data);
    }

    /**
     * 构建失败结果
     *
     * @param msg
     * @param data
     * @return
     */
    public Result failure(String msg, Object data) {
        return Result.buildFailure(msg, data);
    }

    /**
     * 构建失败结果
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public Result failure(Integer code, String msg, Object data) {
        return Result.buildFailure(code, msg, data);
    }

    /**
     * 构建失败结果
     *
     * @param code
     * @param data
     * @return
     */
    public Result failure(Integer code, Object data) {
        return Result.buildFailure(code, null, data);
    }

    /**
     * 构建失败结果
     *
     * @param code
     * @return
     */
    public Result failure(Integer code) {
        return Result.buildFailure(code, null, null);
    }

    /**
     * 构建成功结果
     *
     * @return
     */
    public Result success() {
        return success(null, null);
    }

    /**
     * 构建成功结果带信息
     *
     * @param msg
     * @return
     */
    public Result success(String msg) {
        return success(msg, null);
    }

    /**
     * 构建成功结果待数据
     *
     * @param data
     * @return
     */
    public Result success(Object data) {
        return success(null, data);
    }

    /**
     * 构建失败结果
     *
     * @return
     */
    public Result failure() {
        return failure(0, null, null);
    }

    /**
     * 构建失败结果待数据
     *
     * @param msg
     * @return
     */
    public Result failure(String msg) {
        return failure(msg, null);
    }

    /**
     * 构建失败结果带数据
     *
     * @param data
     * @return
     */
    public Result failure(Object data) {
        return failure(0, null, data);
    }

}

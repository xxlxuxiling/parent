package com.schcilin.sysuser.service;

/**
 * @Author: schcilin
 * @Date: 2019/4/27 16:03
 * @Version 1.0
 * @des:
 */
public interface VerificationCodeService {

    /**
     * 验证码缓存名称
     */
    String VERIFICATION_CODE_CACHE_NAME = "verificationCodeCache";
    /**
     * 验证码短信发送记录缓存名称
     */
    String VERIFICATION_CODE_SMS_SEND_CACHE_NAME = "verificationCodeSmsSendCache";

    /**
     *
     判断验证码是否匹配，默认忽略大小写
     * @param token 验证码TOKEN
     * @param code 验证码值
     * @param phoneNumber 手机号码
     * @return
     */
    boolean validate(String token, String code, String phoneNumber);


    /**
     * 判断验证是否匹配
     * @param token 验证码TOKEN
     * @param code 验证码值
     * @param ignoreCase 忽略大小写
     * @return
     */
    boolean validate(String token, String code, String phoneNumber, boolean ignoreCase);
}

package com.schcilin.zuul.feign.impl;

import com.schcilin.common_server.protocol.Result;
import com.schcilin.zuul.feign.VerificationCodeClient;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/4/27 13:45
 * @Version 1.0
 * @des:
 */
@Component
public class VerificationCodeClientFallback implements VerificationCodeClient {
    @Override
    public Result<Boolean> validate(String token, String code, String subject) {
        return null;
    }
}

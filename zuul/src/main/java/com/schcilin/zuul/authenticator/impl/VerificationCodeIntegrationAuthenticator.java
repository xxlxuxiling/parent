package com.schcilin.zuul.authenticator.impl;

import com.schcilin.zuul.interceptor.IntegrationAuthentication;
import com.schcilin.zuul.model.SysUserAuthentication;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 15:04
 * @Version 1.0
 * @des: 集成认证码认证
 */
@Component
public class VerificationCodeIntegrationAuthenticator extends UserNamePasswordAuthenticator {
    private final static String VERIFICATION_CODE_AUTH_TYPE = "vc";
    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        return super.authenticate(integrationAuthentication);
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        super.prepare(integrationAuthentication);
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return super.support(integrationAuthentication);
    }
}

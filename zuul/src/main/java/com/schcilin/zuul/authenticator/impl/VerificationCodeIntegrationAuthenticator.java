package com.schcilin.zuul.authenticator.impl;

import com.schcilin.common_server.protocol.Result;
import com.schcilin.zuul.feign.VerificationCodeClient;
import com.schcilin.zuul.interceptor.IntegrationAuthentication;
import com.schcilin.zuul.model.SysUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
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
    @Autowired
    private VerificationCodeClient verificationCodeClient;

    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        return super.authenticate(integrationAuthentication);
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String vcToken = integrationAuthentication.getAuthParameter("vc_token");
        String vcCode = integrationAuthentication.getAuthParameter("vc_code");
        Result<Boolean> validate = verificationCodeClient.validate(vcToken, vcCode, null);
        if (!validate.getData()) {
            throw new OAuth2Exception("验证码错误");
        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return VERIFICATION_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}

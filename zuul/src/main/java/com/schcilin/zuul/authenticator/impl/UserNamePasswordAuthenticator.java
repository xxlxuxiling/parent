package com.schcilin.zuul.authenticator.impl;

import com.schcilin.zuul.feign.SysUserClient;
import com.schcilin.zuul.interceptor.IntegrationAuthentication;
import com.schcilin.zuul.model.SysUserAuthentication;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 13:32
 * @Version 1.0
 * @des: 密码登陆认证处理
 */
@Component
@Primary
public class UserNamePasswordAuthenticator extends AbstractPreparableIntegrationAuthenticator {
    @Autowired
    private SysUserClient sysUserClient;

    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        SysUserAuthentication sysUserAuthentication = sysUserClient.findUserByUsername(integrationAuthentication.getUserName());
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {

    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }
}

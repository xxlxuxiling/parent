package com.schcilin.zuul.authenticator.impl;

import com.schcilin.zuul.authenticator.IntegrationAuthenticator;
import com.schcilin.zuul.interceptor.IntegrationAuthentication;
import com.schcilin.zuul.model.SysUserAuthentication;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 13:28
 * @Version 1.0
 * @des: 抽象为父类
 */
public abstract class AbstractPreparableIntegrationAuthenticator implements IntegrationAuthenticator {
    @Override
    public abstract  SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract void prepare(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract boolean support(IntegrationAuthentication integrationAuthentication);

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}

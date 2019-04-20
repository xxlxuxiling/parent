package com.schcilin.zuul.authenticator;

import com.schcilin.zuul.interceptor.IntegrationAuthentication;
import com.schcilin.zuul.model.SysUserAuthentication;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 12:05
 * @Version 1.0
 * @des: 整合认证器
 */
public interface IntegrationAuthenticator {
    /**
     * 处理集成认证
     * @param integrationAuthentication
     * @return
     */
    SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication);

    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 判断是否支持集成类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /**
     * 认证结束后执行
     * @param integrationAuthentication
     */
    void  complete(IntegrationAuthentication integrationAuthentication);
}

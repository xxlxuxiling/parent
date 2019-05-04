package com.schcilin.zuul.authenticator;

import com.schcilin.zuul.interceptor.IntegrationAuthentication;

/**
 * @Author: schcilin
 * @Date: 2019/4/27 12:59
 * @Version 1.0
 * @des: 将当前集成认证信息上下文放进threadlocal中
 */
public class IntegrationAuthenticationContext {
    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication integrationAuthentication) {
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}

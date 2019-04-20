package com.schcilin.zuul.interceptor;

import com.schcilin.zuul.authenticator.IntegrationAuthenticator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 11:29
 * @Version 1.0
 * @des: 定义拦截器拦截登陆请求
 */
@Component
public class IntegrationAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARAM_NAME="auth_type";
    private static final String AUTH_TOKEN_URL="/oauth/token";
    private Collection<IntegrationAuthenticator> integrationAuthenticators;
    private ApplicationContext applicationContext;
    private RequestMatcher requestMatcher;

    public IntegrationAuthenticationFilter() {
        this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(AUTH_TOKEN_URL,"GET"),new AntPathRequestMatcher(AUTH_TOKEN_URL,"POST"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}

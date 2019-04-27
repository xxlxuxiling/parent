package com.schcilin.zuul.interceptor;

import com.google.common.collect.Lists;
import com.schcilin.zuul.authenticator.IntegrationAuthenticationContext;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 11:29
 * @Version 1.0
 * @des: 定义拦截器拦截登陆请求
 */
@Component
public class IntegrationAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARAM_NAME = "auth_type";
    private static final String AUTH_TOKEN_URL = "/oauth/token";
    private Collection<IntegrationAuthenticator> integrationAuthenticators;
    private ApplicationContext applicationContext;
    private RequestMatcher requestMatcher;

    public IntegrationAuthenticationFilter() {
        this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(AUTH_TOKEN_URL, "GET"), new AntPathRequestMatcher(AUTH_TOKEN_URL, "POST"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (requestMatcher.matches(request)) {
            //设置登录集成信息
            IntegrationAuthentication integrationAuthentication = new IntegrationAuthentication();
            integrationAuthentication.setAuthType(request.getParameter(AUTH_TYPE_PARAM_NAME));
            integrationAuthentication.setAuthParameters(request.getParameterMap());
            //将集成信息放进当前线程threadlocal中
            IntegrationAuthenticationContext.set(integrationAuthentication);
            try {
                //预处理
                this.prePare(integrationAuthentication);
                //交给下个过滤器处理
                filterChain.doFilter(request, response);
                //后置处理
                this.complete(integrationAuthentication);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                //清楚当前线程集成信息
                IntegrationAuthenticationContext.clear();
            }


        } else {

            filterChain.doFilter(request, response);
        }
    }

    /**
     * 后置处理
     *
     * @param integrationAuthentication
     */
    private void complete(IntegrationAuthentication integrationAuthentication) {
        this.integrationAuthenticators.stream().forEach(integrationAuthenticator -> {
            if (integrationAuthenticator.support(integrationAuthentication)) {
                integrationAuthenticator.complete(integrationAuthentication);
            }
        });

    }

    /**
     * 登陆认证预处理，判断是否支持认证类型
     *
     * @param integrationAuthentication
     */
    private void prePare(IntegrationAuthentication integrationAuthentication) {
        //延迟加载
        if (integrationAuthenticators == null) {
            synchronized (this) {
                Map<String, IntegrationAuthenticator> beansOfType = applicationContext.getBeansOfType(IntegrationAuthenticator.class);
                if (beansOfType != null) {
                    integrationAuthenticators = beansOfType.values();
                }

            }
            if (integrationAuthenticators == null) {
                integrationAuthenticators = Lists.newArrayList();
            }
            integrationAuthenticators.stream().forEach(integrationAuthenticator -> {
                if (integrationAuthenticator.support(integrationAuthentication)) {
                    integrationAuthenticator.prepare(integrationAuthentication);
                }

            });

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}

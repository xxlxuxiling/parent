package com.schcilin.zuul.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.schcilin.zuul.authenticator.IntegrationAuthenticationContext;
import com.schcilin.zuul.authenticator.IntegrationAuthenticator;
import com.schcilin.zuul.feign.SysUserClient;
import com.schcilin.zuul.interceptor.IntegrationAuthentication;
import com.schcilin.zuul.model.SysUserAuthentication;
import com.schcilin.zuul.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: schcilin
 * @Date: 2019/5/4 11:40
 * @Version 1.0
 * @des: 客户端需要加载用户信息的具体服务
 */
@Service
public class IntegrationUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserClient sysUserClient;
    //是否需要集成其他的认证信息
    private List<IntegrationAuthenticator> integrationAuthenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> integrationAuthenticators) {
        this.integrationAuthenticators = integrationAuthenticators;
    }

    /**
     * 根据"用户名"查找出当前用户
     *
     * @param userName 可以是电话号码，用户名等信息
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //在当前线程副本中是否找到集成认证的信息
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        if (ObjectUtil.isNull(integrationAuthentication)) {
            integrationAuthentication = new IntegrationAuthentication();

        }
        integrationAuthentication.setUserName(userName);
        SysUserAuthentication sysUserAuthentication = this.authenticate(integrationAuthentication);
        if (ObjectUtil.isNull(sysUserAuthentication)) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        User user = new User();
        BeanUtils.copyProperties(sysUserAuthentication, user);
        this.setAuthorize(user);
        return user;
    }

    /**
     * 设置授权信息,应该查数据库的是就解决
     * @param user
     */
    private void setAuthorize(User user) {
        
    }

    /**
     * 根据认证信息，认证用户
     *
     * @param integrationAuthentication
     * @return
     */
    private SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        if (CollUtil.isNotEmpty(integrationAuthenticators)) {
            for (IntegrationAuthenticator authticator : integrationAuthenticators) {
                if (authticator.support(integrationAuthentication)) {
                    return authticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }
}

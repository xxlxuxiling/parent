package com.schcilin.zuul.endpoint;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

/**
 * @Author: schcilin
 * @Date: 2019/5/3 17:14
 * @Version 1.0
 * @des: token登出接口
 */
@FrameworkEndpoint
@Api(tags = "token登出销毁接口")
public class TokenRevokeEndpoint {
    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;
}

package com.schcilin.zuul.endpoint;

import com.schcilin.common_server.protocol.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Qualifier("consumerTokenServices")//该注解的意思：该接口有多个实现类，采用的是那种实现
    private ConsumerTokenServices consumerTokenServices;

    @DeleteMapping("/oauth/token")
    @ApiOperation(value = "/oauth/token", tags = "oauth2销毁token Api")
    public Result<String> deleteAccessToken(@RequestParam String accessToken) {
        consumerTokenServices.revokeToken(accessToken);
        return Result.buildSuccess("退出登录;功");
    }
}

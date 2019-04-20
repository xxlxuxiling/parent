package com.schcilin.zuul.interceptor;

import com.sun.deploy.net.proxy.pac.PACFunctionsImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 12:15
 * @Version 1.0
 * @des: 认证内容
 */
@Getter
@Setter
public class IntegrationAuthentication {
    private String authType;
    private String userName;
    private Map<String, String[]> authParameters;

    public String getAuthParameter(String paramter) {
        String[] values = this.authParameters.get(paramter);
        if (values != null && values.length > 0) {
            return values[0];

        }
        return null;
    }

}

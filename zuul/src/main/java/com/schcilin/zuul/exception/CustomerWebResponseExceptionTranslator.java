package com.schcilin.zuul.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/5/4 13:50
 * @Version 1.0
 * @des: 异常转换器
 */
@Component
@Slf4j
public class CustomerWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {
    public static final String BAD_MSG = "坏的凭证";

    /**
     * 自定义异常处理
     * @param e
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.error("Grant Error : " + e);
        e.printStackTrace();
        OAuth2Exception oAuth2Exception;
        if (e.getMessage() != null && e.getMessage().equals(BAD_MSG)) {
            oAuth2Exception=new InvalidGrantException("用户名或者密码错误",e);
        } else if (e instanceof InternalAuthenticationServiceException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else {
            oAuth2Exception=  new UnsupportedResponseTypeException("服务内部错误", e);
        }
        return super.translate(oAuth2Exception);
    }
}

package com.schcilin.sysuser.service.impl;

import com.schcilin.redismodule.cache.VerificationCodeCache;
import com.schcilin.sysuser.service.VerificationCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**系统验证码服务类
 * @Author: schcilin
 * @Date: 2019/5/3 16:13
 * @Version 1.0
 * @des:
 */
@Service
@CacheConfig(cacheNames={VerificationCodeService.VERIFICATION_CODE_CACHE_NAME,VerificationCodeService.VERIFICATION_CODE_SMS_SEND_CACHE_NAME})
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Autowired
    private VerificationCodeCache verificationCodeCache;

    @Override
    public boolean validate(String token, String code, String phoneNumber) {
        return this.validate(token, code, phoneNumber,true);
    }

    @Override
    public boolean validate(String token, String code, String phoneNumber, boolean ignoreCase) {
        if (StringUtils.isEmpty(code)) {
            return Boolean.FALSE;
        }
        if (StringUtils.isNotEmpty(phoneNumber)) {
            token=phoneNumber+"@"+token;
        }
        String value = this.verificationCodeCache.get(VERIFICATION_CODE_CACHE_NAME,token);
        if (StringUtils.isEmpty(value)) {
            return Boolean.FALSE;
        }
        return ignoreCase?code.equalsIgnoreCase(value):code.equals(value);
    }
}

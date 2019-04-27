package com.schcilin.zuul.feign;

import com.schcilin.common_server.protocol.Result;
import com.schcilin.zuul.config.ZuulCustomFeignClientConfig;
import com.schcilin.zuul.feign.impl.VerificationCodeClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: schcilin
 * @Date: 2019/4/27 13:41
 * @Version 1.0
 * @des: 验证码登录
 */
@FeignClient(name = "sysUserService",fallback = VerificationCodeClientFallback.class,configuration = ZuulCustomFeignClientConfig.class)
public interface VerificationCodeClient {
    @GetMapping("/api/v1/uc/code/validate")
    Result<Boolean> validate(@RequestParam("token") String token, @RequestParam("code") String code, @RequestParam("subject") String subject);

}

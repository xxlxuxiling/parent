package com.schcilin.sysuser.controller;

import com.schcilin.common_server.protocol.Result;
import com.schcilin.sysuser.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: schcilin
 * @Date: 2019/4/27 14:19
 * @Version 1.0
 * @des: 验证码登录信息controller
 */
@RestController
@RequestMapping("/uc/verificationCodeController/v1")
@Api(value = "/uc/verificationCodeController/v1", tags = "验证码登录信息controller")
public class VerificationCodeController extends BaseController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/validate")
    @ApiOperation("校验验证码")
    public Result<Boolean> validate(String token, String code, String subject){
        return this.success(this.verificationCodeService.validate(token,code,subject));
    }
}

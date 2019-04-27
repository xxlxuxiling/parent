package com.schcilin.sysuser.controller;

import io.swagger.annotations.Api;
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
}

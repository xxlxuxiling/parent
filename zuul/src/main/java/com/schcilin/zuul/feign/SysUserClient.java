package com.schcilin.zuul.feign;

import com.schcilin.common_server.dto.user.SysUserDTO;
import com.schcilin.common_server.protocol.Result;
import com.schcilin.common_server.vo.user.SysUserVO;
import com.schcilin.zuul.feign.impl.SysUserClientFallBack;
import com.schcilin.zuul.model.SysUserAuthentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 14:06
 * @Version 1.0
 * @des:
 */
@FeignClient(value = "sysUserService",fallback = SysUserClientFallBack.class)
public interface SysUserClient {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/uc/user/findUserByUsername/{username}")
    SysUserAuthentication findUserByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param phoneNumber 手机号
     * @return UserVo
     */
    @GetMapping("/uc/user/findUserByPhoneNumber/{phoneNumber}")
    SysUserAuthentication findUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber);

    /**
     * 根据OpenId查询用户信息
     * @param type
     * @param token
     * @return UserVo
     */
    @GetMapping("/uc/user/findUserBySocial/{type}/{token}")
    SysUserAuthentication findUserBySocial(@PathVariable("type") String type, @PathVariable("token") String token);

    /**
     * @param phoneNumber
     * @param sourceCode
     * @return
     */
    @PostMapping("/uc/register/add")
    Result<String> register(@RequestParam("phoneNumber") String phoneNumber,
                            @RequestParam("sourceCode") String sourceCode);

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/uc/user/save")
    Result<SysUserDTO> save(@RequestBody SysUserDTO user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/uc/user/update")
    Result<SysUserDTO> update(SysUserDTO user);

    /**
     *
     * @Title: createSysUser @Description: 创建保险管理员 @param @return 设定文件 @return
     * Result<Boolean> 返回类型 @throws
     */
    @PostMapping("/uc/user/save")
    SysUserDTO createSysUser(@RequestParam("username") String username, @RequestParam("name") String name,
                             @RequestParam("password") String password, @RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("email") String email);



    @GetMapping("/uc/user/delete/{username}")
    Result<Boolean> deleteSysUser(@PathVariable("username") String username);

    @GetMapping("/uc/user/{id}")
    SysUserVO findUserById(@PathVariable("id") Long id);
}

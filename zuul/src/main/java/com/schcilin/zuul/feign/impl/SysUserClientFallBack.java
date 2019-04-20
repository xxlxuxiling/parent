package com.schcilin.zuul.feign.impl;

import com.schcilin.common_server.dto.user.SysUserDTO;
import com.schcilin.common_server.protocol.Result;
import com.schcilin.common_server.vo.user.SysUserVO;
import com.schcilin.zuul.feign.SysUserClient;
import com.schcilin.zuul.model.SysUserAuthentication;
import org.springframework.stereotype.Component;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 14:44
 * @Version 1.0
 * @des: 系统用户远程调用失败
 */
@Component
public class SysUserClientFallBack implements SysUserClient {

    @Override
    public SysUserAuthentication findUserByUsername(String username) {
        return null;
    }

    @Override
    public SysUserAuthentication findUserByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public SysUserAuthentication findUserBySocial(String type, String token) {
        return null;
    }

    @Override
    public Result<String> register(String phoneNumber, String sourceCode) {
        return null;
    }

    @Override
    public Result<SysUserDTO> save(SysUserDTO user) {
        return null;
    }

    @Override
    public Result<SysUserDTO> update(SysUserDTO user) {
        return null;
    }

    @Override
    public SysUserDTO createSysUser(String username, String name, String password, String phoneNumber, String email) {
        return null;
    }

    @Override
    public Result<Boolean> deleteSysUser(String username) {
        return null;
    }

    @Override
    public SysUserVO findUserById(Long id) {
        return null;
    }
}

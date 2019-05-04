package com.schcilin.zuul.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 12:39
 * @Version 1.0
 * @des:
 */
@Getter
@Setter
public class SysUserAuthentication implements Serializable {

    private static final long serialVersionUID = -3332151368471216089L;
    private String id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String status;

    private String name;

    private String type;
}

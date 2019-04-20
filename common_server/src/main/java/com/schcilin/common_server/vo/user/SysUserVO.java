package com.schcilin.common_server.vo.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: schcilin
 * @Date: 2019/4/20 14:45
 * @Version 1.0
 * @des: 用户查询类
 */
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = -9139665328976331864L;
    private Long id;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码盐
     */
    private String salt;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 状态:  Y-可用  N-不可用
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除状态: Y-已删除,N-未删除
     */
    private String deleted;
    private String avatar;

    /**
     * 用户类型
     */
    private String type;
}

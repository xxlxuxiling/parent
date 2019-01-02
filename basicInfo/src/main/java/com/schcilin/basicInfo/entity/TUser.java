package com.schcilin.basicInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author schcilin
 * @since 2019-01-02
 */
@TableName("t_user")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * ID
     */
         private String id;

        /**
     * 用户名
     */
         private String userName;

        /**
     * 密码
     */
         private String password;

        /**
     * 状态
     */
         private Integer status;

        /**
     * 电话
     */
         private String cellPhone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @Override
    public String toString() {
        return "TUser{" +
        "id=" + id +
        ", userName=" + userName +
        ", password=" + password +
        ", status=" + status +
        ", cellPhone=" + cellPhone +
        "}";
    }
}

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
@TableName("t_role")
public class TRole implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * ID
     */
         private String id;

        /**
     * 权限名称
     */
         private String roleName;

        /**
     * 状态
     */
         private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TRole{" +
        "id=" + id +
        ", roleName=" + roleName +
        ", status=" + status +
        "}";
    }
}

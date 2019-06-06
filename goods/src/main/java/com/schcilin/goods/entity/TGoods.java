package com.schcilin.goods.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author schcilin
 * @since 2019-06-04
 */
@TableName("t_goods")
public class TGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String goodName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        return "TGoods{" +
        "id=" + id +
        ", userId=" + userId +
        ", goodName=" + goodName +
        "}";
    }
}

package com.schcilin.payserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author schcilin
 * @since 2019-06-23
 */
@TableName("pay_channel")
public class PayChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

        /**
     * 支付渠道
     */
         private String channel;

        /**
     * 渠道名称
     */
         private String name;

        /**
     * 渠道code
     */
         private String code;

        /**
     * 英文简写
     */
         private String simpleName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return "PayChannel{" +
        "id=" + id +
        ", channel=" + channel +
        ", name=" + name +
        ", code=" + code +
        ", simpleName=" + simpleName +
        "}";
    }
}

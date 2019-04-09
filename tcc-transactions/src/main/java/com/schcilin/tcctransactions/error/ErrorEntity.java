package com.schcilin.tcctransactions.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schcilin.tcctransactions.constant.RestStatus;
import com.schcilin.tcctransactions.util.Jacksons;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author: schicilin
 * Date: 2019/4/9 22:23
 * Content:
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class ErrorEntity implements Serializable {
    private static final long serialVersionUID = -9112917477095022777L;

    /**
     * [M] 平台状态码
     */
    @JsonProperty("code")
    private int code;

    /**
     * [M] 错误信息
     */
    @JsonProperty("msg")
    private String msg;

    /**
     * [C] 详细错误信息
     */
    @JsonProperty("details")
    private Object details;

    public ErrorEntity(RestStatus code) {
        this(code, null);
    }


    public ErrorEntity(RestStatus statusCodes, Object details) {
        this.code = statusCodes.code();
        this.msg = statusCodes.message();
        if (details != null) {
            this.details = details;
        }
    }

    @Override
    public String toString() {
        return "ErrorEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", details=" + Jacksons.parse(details) +
                '}';
    }
}

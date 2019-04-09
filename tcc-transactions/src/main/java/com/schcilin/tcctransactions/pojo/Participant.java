package com.schcilin.tcctransactions.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.schcilin.tcctransactions.constant.TccStatus;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Author: schicilin
 * Date: 2019/4/9 21:06
 * Content: 参与者
 */
public class Participant implements Serializable {
    private static final long serialVersionUID = 5495514210446279335L;
    @URL
    @ApiModelProperty(value = "资源URI", required = true, example = "http://www.example.com/part/123")
    private String uri;

    @ApiModelProperty(value = "过期时间, ISO标准", required = true, example = "2017-03-20T14:00:41+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime expireTime;

    @ApiModelProperty(value = "发起confirm的时间, ISO标准", hidden = true, example = "2017-03-20T14:00:41+08:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime executeTime;

    @ApiModelProperty(value = "参与方返回的错误码", hidden = true, example = "451")
    private ResponseEntity<?> participantErrorResponse;

    @ApiModelProperty(value = "预留资源的状态", hidden = true, example = "TO_BE_CONFIRMED")
    private TccStatus tccStatus = TccStatus.TO_BE_CONFIRMED;
}

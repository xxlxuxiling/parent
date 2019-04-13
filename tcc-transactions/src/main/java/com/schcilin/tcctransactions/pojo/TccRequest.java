package com.schcilin.tcctransactions.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Author: schicilin
 * Date: 2019/4/10 0:50
 * Content: tcc请求
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class TccRequest implements Serializable {

    private static final long serialVersionUID = -4553097927126346211L;

    @Valid
    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "参与方提供的链接集合", required = true)
    private List<Participant> participantLinks;
}

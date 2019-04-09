package com.schcilin.tcctransactions.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.schcilin.tcctransactions.pojo.Participant;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Author: schicilin
 * Date: 2019/4/9 19:34
 * Content:
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","fieldHandler"},ignoreUnknown = true)//Hibernate 这种ORM框架 通过实体类间相互关联来表示数据库表数据的外键。在使用Jackson序列化时就会出现无限循环或递归的问题导致序列化报错
public class TccErrorResponse implements Serializable {
    private static final long serialVersionUID = -111167286642213696L;
    @Valid
    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "参与方提供的链接集合", required = true)
    private List<Participant> participantLinks;
}

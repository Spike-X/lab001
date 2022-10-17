package com.aircraft.codelab.pioneer.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 2021-08-12
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuUpdateVo extends SysMenuCreatVo {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @NotNull(message = "主键id不能为空", groups = Update.class)
    @Length(min = 18, message = "主键id不存在")
    private String id;
}

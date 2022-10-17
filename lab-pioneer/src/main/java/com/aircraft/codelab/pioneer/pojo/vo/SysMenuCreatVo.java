package com.aircraft.codelab.pioneer.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 2021-08-16
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class SysMenuCreatVo {
    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id", position = 1)
    @Length(min = 18, message = "父菜单id不存在", groups = Save.class)
    private String parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", position = 2)
    @NotBlank(message = "菜单名称不能为空")
    @Length(max = 30, message = "菜单名称不能超过30个字符")
    private String menuName;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型", position = 3)
    @NotNull(message = "菜单类型不能为空")
    @Range(min = 0, max = 2, message = "菜单类型为0~2整数")
    private Integer menuType;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", position = 4)
    @Range(min = 0, max = 50, message = "显示顺序为0~50整数")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 5)
    @Length(max = 100, message = "备注不能超过100个字符")
    private String remark;

    public interface Save extends Default {
    }

    public interface Update extends Default {
    }
}

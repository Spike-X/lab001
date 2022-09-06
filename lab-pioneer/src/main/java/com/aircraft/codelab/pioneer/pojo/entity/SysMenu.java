package com.aircraft.codelab.pioneer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 2021-08-10
 * sys_menu
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseDO {
    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型
     */
    private Integer menuType;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除（1是，0否）
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Byte deleted;

    private static final long serialVersionUID = 1L;
}

package com.aircraft.codelab.labcore.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 2021-08-16
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseDO {
    private String name;

    private String password;
}

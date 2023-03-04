package com.aircraft.codelab.pioneer.pojo.entity;

import com.aircraft.codelab.pioneer.enums.ContractStateEnum;
import com.aircraft.codelab.pioneer.enums.UpdateEnum;
import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(name = "u_id", ordinal = 1)
    private String uId;

    @JSONField(ordinal = 2)
    private String name;

    @JSONField(ordinal = 3)
    private String password;

    private ContractStateEnum.CONTRACT_SIGNING contractSigningEnum;
}

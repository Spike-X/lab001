package com.aircraft.codelab.pioneer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 2022-12-17
 * 法定日期表
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@TableName("t_legal_date")
@EqualsAndHashCode(callSuper = true)
public class LegalDate extends BaseDO {
    private LocalDate legalDate;

    private int dateType;

    private String description;
}

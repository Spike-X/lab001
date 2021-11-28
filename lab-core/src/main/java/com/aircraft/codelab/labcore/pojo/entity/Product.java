package com.aircraft.codelab.labcore.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 2021-09-23
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@TableName("product")
public class Product {
    private Long id;
    private String name;
    private BigDecimal accountBalance;
    @Version
    private Integer version;
}

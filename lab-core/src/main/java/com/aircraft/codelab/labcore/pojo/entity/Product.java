package com.aircraft.codelab.labcore.pojo.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * 2021-09-23
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Float price;
    @Version
    private Integer version;
}

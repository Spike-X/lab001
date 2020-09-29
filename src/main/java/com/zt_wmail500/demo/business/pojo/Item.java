package com.zt_wmail500.demo.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: demo
 * @description: lambda测试实体类
 * @author: tao.zhang
 * @create: 2020-08-09 22:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Item {

    private Integer id;

    private String name;

    private Double price;
}

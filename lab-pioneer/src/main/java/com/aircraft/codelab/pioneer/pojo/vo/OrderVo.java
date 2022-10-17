package com.aircraft.codelab.pioneer.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 2022-09-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderVo {
    @Range(min = 10)
//    @Size //无法使用
//    @Length //无法使用
    Integer itemId;
    //    @Range //可以为null不能为"" 入参必须为数字类型
    @Length //可以为null
//    @Size //可以为null
//    @NotNull // 不能为null可以为""
    @NotBlank // 不能为null且不能为""
//    @NotEmpty // 不能为null且不能为""
            String itemName;
    @Range(min = 5) //不能为null
//    @Length //无法使用
//        @Size //无法使用
            int number;
}

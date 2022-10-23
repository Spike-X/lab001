package com.aircraft.codelab.pioneer.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 2021-08-16
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserVo {
    // @Size 无法使用
    // @Length 无法使用
    @NotNull
    @Range(max = 2, message = "最大值不能超过{max}") //最大整数 小数失效
//    @Digits(integer = 2, fraction = 0, message = "整数位位数上限{integer}，小数位位数上限{fraction}") //小数位位数失效
    private Long id;
    @Length(message = "名称不能超过个 {max} 字符", max = 1)
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String username;
    //@Valid @Length //无法校验list<非对象类型>
//    @Length //无法使用
//    @Range //无法使用
    @Size(max = 1) //可以为null
//    @NotNull //不能为null可以为""
//    @NotBlank //无法使用
    @NotEmpty
    private List<@NotBlank @Length(message = "taskList不能超过个 {max} 字符", max = 1) String> taskList;
}

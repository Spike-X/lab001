package com.aircraft.codelab.pioneer.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class CreatOrderVo {
    private String externalNumber;
    @NotNull
    @AssertTrue
    private Boolean expressRequired;
    @Past
    private LocalDateTime creatTime;
    @Future
    private LocalDateTime payTime;
    @Email
    private String email;
    //    @CreditCardNumber //可以为null不能为"" 银行卡，信用卡16/19位Luhn校验算法
    private String creditCard;
    @Pattern(regexp = "(?:0|86|\\+86)?1[3-9]\\d{9}", message = "不合法的手机号码") //可以为null不能为""
    private String mobilePhone;
    @NotNull
    @Digits(integer = 1, fraction = 2, message = "整数位位数上限{integer}，小数位位数上限{fraction}")
    @DecimalMin("0.01") //可以为null 输入001出错
    private BigDecimal price;
    @NotNull
    @Valid
    private UserVO userVO;
    @NotEmpty
    @Size(max = 1,message = "下单量最大上限{max}")
    @Valid
    private List<OrderVo> orderVoList;
}

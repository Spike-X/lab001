package com.aircraft.codelab.labcore.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 2021-11-28
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class RealUserContractDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证号码
     */
    private String identityCard;

    /**
     * 手机号码
     */
    private String cellphoneNo;

    private List<LoanContractDto> loanContractDtoList;
}

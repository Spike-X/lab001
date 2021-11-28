package com.aircraft.codelab.labcore.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class UserContractDto {
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

    /**
     * 借贷合同流水号
     */
    private String contractNo;

    /**
     * 面签状态：0-待签，1-隐私，2-身份证，3-人脸，4-语音合成，5-语音识别，6-签字(结束)，7-取消
     */
    private Byte contractState;

    /**
     * 合同创建时间
     */
    private LocalDateTime createTime;
}

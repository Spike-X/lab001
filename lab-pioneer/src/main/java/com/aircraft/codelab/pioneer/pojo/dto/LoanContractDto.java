package com.aircraft.codelab.pioneer.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 2021-11-28
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class LoanContractDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 借贷合同流水号
     */
    private String contractNo;

    /**
     * 面签状态：0-待签，1-隐私，2-身份证，3-人脸，4-语音合成，5-语音识别，6-签字，7-结束，8-取消
     */
    private Byte contractState;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

package com.aircraft.codelab.labcore.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class LoanContract implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户表主键id
     */
    private Long userId;

    /**
     * 借贷合同流水号
     */
    private String contractNo;

    /**
     * 面签状态：0-待签，1-隐私，2-身份证，3-人脸，4-语音合成，5-语音识别，6-签字，7-结束，8-取消
     */
    private Byte contractState;

    /**
     * 借贷产品‌名称
     */
    private String loanProduct;

    /**
     * 借贷金额
     */
    private BigDecimal loanAmount;

    /**
     * 产品详情
     */
    private String productDetail;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
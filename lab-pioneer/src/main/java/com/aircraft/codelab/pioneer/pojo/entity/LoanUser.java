package com.aircraft.codelab.pioneer.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class LoanUser implements Serializable {
    /**
     * 主键
     */
    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
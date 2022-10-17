package com.aircraft.codelab.pioneer.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 2021-12-10
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class UpdateTaskVo {
    private Long id;

    /**
     * 多余字段
     */
    private Long userId;

    private String productName;

    private LocalDateTime updateTime;
}

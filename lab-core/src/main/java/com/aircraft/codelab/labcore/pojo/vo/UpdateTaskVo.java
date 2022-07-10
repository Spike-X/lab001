package com.aircraft.codelab.labcore.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

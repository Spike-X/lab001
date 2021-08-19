package com.aircraft.codelab.labcore.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 2021-08-16
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@Builder(toBuilder = true)
public class UserVO {
    private Long id;
    private String name;
}

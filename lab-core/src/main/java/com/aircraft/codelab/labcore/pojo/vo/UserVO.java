package com.aircraft.codelab.labcore.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserVO {
    private Long id;
    private String name;
    private List<String> taskList;
}

package com.aircraft.codelab.labcore.controller.redisson;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2022-07-12
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DelayDto extends BaseMessage {
    private String taskNo;
    private int retryNum;
}

package com.aircraft.codelab.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * 2022-07-25
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class DelayMessage implements Serializable {
    private int retryTime;
    private String TaskNo;

    private static final long serialVersionUID = 1L;
}

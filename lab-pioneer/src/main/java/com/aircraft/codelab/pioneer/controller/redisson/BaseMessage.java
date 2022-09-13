package com.aircraft.codelab.pioneer.controller.redisson;

import lombok.*;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息的序列id,使用该字段移除消息
     */
    private Long serialId;

    /**
     * 过期时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 时长,实际的过期时间为 timeUnit * expire
     */
    private long expire;
}

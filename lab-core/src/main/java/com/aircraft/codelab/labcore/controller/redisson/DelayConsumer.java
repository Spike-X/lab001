package com.aircraft.codelab.labcore.controller.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 2022-07-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Component
public class DelayConsumer<T> {
    @Autowired
    private RDelayedQueue<DelayDto> delayedQueue;

    private static final int RETRY_MAX = 11;

    @Async("asyncThread")
    public void accept(DelayDto qryDto) {
        log.debug("dto: {}", qryDto);
        qryDto.setRetryNum(qryDto.getRetryNum() + 1);
        if (qryDto.getRetryNum() > RETRY_MAX) {
            return;
        }
        delayedQueue.offer(qryDto, 6, TimeUnit.SECONDS);
    }
}

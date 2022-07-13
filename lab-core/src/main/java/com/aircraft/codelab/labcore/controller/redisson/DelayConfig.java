package com.aircraft.codelab.labcore.controller.redisson;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 2022-07-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@Configuration
public class DelayConfig {
    @Resource
    private RedissonClient redissonClient;

    @Bean("blockQueue")
    public RBlockingDeque<DelayDto> getBlockQueue() {
        return redissonClient.getBlockingDeque("delay:task:result");
    }

    @Bean("delayedQueue")
    public RDelayedQueue<DelayDto> getDelayQueue() {
        RBlockingDeque<DelayDto> blockingDeque = getBlockQueue();
        return redissonClient.getDelayedQueue(blockingDeque);
    }
}

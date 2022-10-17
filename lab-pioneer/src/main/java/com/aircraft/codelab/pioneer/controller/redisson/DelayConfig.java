package com.aircraft.codelab.pioneer.controller.redisson;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;

/**
 * 2022-07-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@Configuration
public class DelayConfig {
//    @Resource
    private RedissonClient redissonClient;

//    @Bean("blockingQueue")
    public RBlockingDeque<DelayDto> initBlockingDeque() {
        return redissonClient.getBlockingDeque("delay:task:result");
    }

//    @Bean("delayedQueue")
    public RDelayedQueue<DelayDto> initDelayedQueue() {
        RBlockingDeque<DelayDto> blockingDeque = initBlockingDeque();
        return redissonClient.getDelayedQueue(blockingDeque);
    }
}

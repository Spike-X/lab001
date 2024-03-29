package com.aircraft.codelab.pioneer.controller.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 2022-03-25
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
public class RedissonTest {
    @Lazy
    @Resource
    private RedissonClient redissonClient;

    @Resource
    private DelayProducer delayProducer;

    private RRateLimiter rateLimiter;

    //    @PostConstruct
    public void initRateLimiter() {
        rateLimiter = redissonClient.getRateLimiter("key");
        rateLimiter.trySetRate(RateType.OVERALL, 15, 1, RateIntervalUnit.SECONDS);
    }

    @GetMapping("/rateLimiter")
    public String rateLimiter() {
        boolean acquire = rateLimiter.tryAcquire(1);
        return "";
    }

    @GetMapping("/delayQueue")
    public void delayQueue() {
        DelayDto delayDto = new DelayDto();
        delayDto.setTaskNo("0000");
        delayDto.setRetryNum(0);
        delayProducer.accept(delayDto);
    }

    @GetMapping("/delayQueue1")
    public void delayQueue1() {
        DelayDto delayDto = new DelayDto();
        delayDto.setTaskNo("1111");
        delayDto.setRetryNum(0);
        delayProducer.accept(delayDto);
    }
}

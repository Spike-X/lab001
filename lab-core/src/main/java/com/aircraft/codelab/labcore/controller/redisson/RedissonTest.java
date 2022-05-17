package com.aircraft.codelab.labcore.controller.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
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
//    @Resource
    private RedissonClient redissonClient;
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
}

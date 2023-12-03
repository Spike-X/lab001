package com.aircraft.codelab.pioneer.controller.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
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
    @Resource
    private DelayProducer delayProducer;

//    @Lazy
    @Resource
    private RedissonClient redissonClient;
    private RRateLimiter rateLimiter;

    @PostConstruct
    public void initRateLimiter() {
        rateLimiter = redissonClient.getRateLimiter("limiter");
        // l为访问数，l1为单位时间 最大流速 = 每3秒钟产生1个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 1, 3, RateIntervalUnit.SECONDS);
    }

    @GetMapping("/rateLimiter")
    public String rateLimiter() {
        log.info("rateLimiter =====>");
//        boolean acquire = rateLimiter.tryAcquire(1);
        // 同步阻塞方法,控制速率
        rateLimiter.acquire(1);
        log.info("rateLimiter end =====>");
        return "1";
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

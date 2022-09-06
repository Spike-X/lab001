package com.aircraft.codelab.pioneer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

/**
 * 2022-03-04
 *
 * @author tao.zhang
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class SpringJob {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public TaskScheduler taskOneScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 每5秒执行一次(默认是单线程) 下一个任务会等上一个运行完再执行
     */
    @Async("taskOneScheduler")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void taskOne() {
    }

    /**
     * 首次任务执行的延迟时间
     * fixedDelay:上一次任务执行结束到下一次执行开始的间隔时间,单位为ms
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void taskTwo() {
    }

    public void addTask(String taskNo, Instant instant) {
        Instant plus = instant.plus(Duration.ofHours(5).plusMinutes(4));
        redisTemplate.opsForZSet().add("delay:queue", taskNo, plus.toEpochMilli());
    }

    //    @Scheduled(cron = "0/5 * * * * ?")
    public void delayTask() {
        long nowSecond = Instant.now().toEpochMilli();
        // 查询当前时间的所有任务
        Set<Object> range = redisTemplate.opsForZSet().range("delay:queue", 0, nowSecond);
        // 删除已经执行的任务
        redisTemplate.opsForZSet().remove("delay:queue", 0);
    }
}

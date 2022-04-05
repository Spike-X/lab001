package com.aircraft.codelab.labcore.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.security.SecureRandom;
import java.time.*;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 2022-01-25
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
@Slf4j
public class DelayQueue01 {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "singleThreadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    private static final String DELAY_QUEUE_KEY = "delay:task:zset";

//    @PostConstruct
    public void initSet() {
        LocalDateTime now = LocalDateTime.now();
        SecureRandom random = new SecureRandom();
        producer((long) random.nextInt(10000000), now);
        consumer();
    }

    public void producer(Long taskId, LocalDateTime localDateTime) {
        // 10位时间戳
        long nowSecond = localDateTime.toEpochSecond(ZoneOffset.ofHours(8));
        LocalDateTime dateTimeSecond = LocalDateTime.ofEpochSecond(nowSecond, 0, ZoneOffset.ofHours(8));
        // 13位时间戳
        long nowMilli = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        LocalDateTime dateTimeMilli = LocalDateTime.ofInstant(Instant.ofEpochMilli(nowMilli), ZoneId.systemDefault());

        long nanoTime = System.nanoTime();
        long epochMilli = Instant.now().toEpochMilli();
        log.debug("taskId: {},datetime: {},millisecond: {}", taskId, localDateTime, nowMilli);
        Boolean result = redisTemplate.opsForZSet().add(DELAY_QUEUE_KEY, taskId, nowMilli);
        if (Objects.nonNull(result) && Boolean.FALSE.equals(result)) {
            log.error("Failed to add task!");
        }
    }

    /**
     * 单机适用 消息不会丢失
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void consumer() {
        threadPoolExecutor.execute(() -> {
            while (true) {
                // score从小到大排列
                Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(DELAY_QUEUE_KEY, 0, -1);
                if (CollectionUtils.isEmpty(typedTuples)) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                for (ZSetOperations.TypedTuple<Object> next : typedTuples) {
                    Object value = next.getValue();
                    Double createMillis = next.getScore();
                    assert createMillis != null;
                    long currentMillis = Clock.systemDefaultZone().millis();
                    long deadline = 6 * 60 * 60 * 1000L;
                    long timeDiff = (long) (currentMillis - createMillis);
                    if (timeDiff >= deadline) {
                        // 消费任务
                        // todo
                        // 删除key
                        Long result = redisTemplate.opsForZSet().remove(DELAY_QUEUE_KEY, value);
                        if (Objects.nonNull(result) && result == 1L) {
                            log.debug("task deleted,taskId: {}", value);
                        }
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 多消费者适用
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void consume() {
        threadPoolExecutor.execute(() -> {
            while (true) {
                // score从小到大排列
                Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(DELAY_QUEUE_KEY, 0, -1);
                if (CollectionUtils.isEmpty(typedTuples)) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                for (ZSetOperations.TypedTuple<Object> next : typedTuples) {
                    Object value = next.getValue();
                    Double createMillis = next.getScore();
                    assert createMillis != null;
                    long currentMillis = Clock.systemDefaultZone().millis();
                    long deadline = 6 * 60 * 60 * 1000L;
                    long timeDiff = (long) (currentMillis - createMillis);
                    if (timeDiff >= deadline) {
                        // 删除key
                        Long result = redisTemplate.opsForZSet().remove(DELAY_QUEUE_KEY, value);
                        if (Objects.nonNull(result) && result == 1L) {
                            log.debug("task deleted,taskId: {}", value);
                            // 删除成功才消费任务
                            // todo
                        }
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

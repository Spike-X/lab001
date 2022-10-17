package com.aircraft.codelab.pioneer.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 2022-05-08
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class DelayQueue02 {
    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource(name = "singleThreadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    private static final String DELAY_QUEUE_KEY = "delay:task:video";

    @PostConstruct
    public void init() {
        consume();
    }

    public void addQueue(String taskNo, LocalDateTime deadline) {
        // 13位时间戳
        long deadlineMilli = deadline.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        log.info("taskNo: {},deadline: {},deadlineMilli: {}", taskNo, deadline, deadlineMilli);

        Boolean result = redisTemplate.opsForZSet().add(DELAY_QUEUE_KEY, taskNo, deadlineMilli);
        if (Objects.nonNull(result) && Boolean.FALSE.equals(result)) {
            log.error("failed to add task! {}", taskNo);
        }
    }

    /**
     * 多消费者适用
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void consume() {
        threadPoolExecutor.execute(() -> {
            while (true) {
                // score从小到大排列
                long nowMilli = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                Set<String> pendingTask = redisTemplate.opsForZSet().range(DELAY_QUEUE_KEY, 0, nowMilli);
                if (CollectionUtils.isEmpty(pendingTask)) {
                    // 业务时效性不强，此处手动阻塞线程，降低系统资源消耗
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                    continue;
                }
                pendingTask.forEach(taskNo -> {
                    Long result = redisTemplate.opsForZSet().remove(DELAY_QUEUE_KEY, taskNo);
                    // 删除成功才消费任务
                    if (Objects.nonNull(result) && result == 1L) {
                        log.info("task deleted,taskNo: {}", taskNo);
                    }
                });
            }
        });
    }
}

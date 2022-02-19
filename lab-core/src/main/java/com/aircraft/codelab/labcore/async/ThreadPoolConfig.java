package com.aircraft.codelab.labcore.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 2021-10-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

//    private static final int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;

    private static final int corePoolSize = 4;      // 核心线程数（默认线程数）线程池创建时候初始化的线程数
    private static final int maxPoolSize = 8;      // 最大线程数 线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
    private static final long keepAliveTime = 60L;     // 允许线程空闲时间（单位：默认为秒）当超过了核心线程之外的线程在空闲时间到达之后会被销毁
    private static final int queueCapacity = 100;    // 缓冲队列数 用来缓冲执行任务的队列
    private static final String threadNamePrefix = "pool-Test";    // 线程池名前缀 方便我们定位处理任务所在的线程池

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("po-detail-pool-%d").build();
        // 单个任务200ms,业务高峰期8个线程，10s超时
        // 10 * 1000ms / 200ms * 8 = 400个任务 向上取512 极端情况丢弃任务
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(4, 8, 60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(512), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        THREAD_POOL_EXECUTOR.allowCoreThreadTimeOut(true);
    }

    @Bean
    public ThreadPoolExecutor chargeThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueCapacity), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.allowsCoreThreadTimeOut();
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    @Bean
    public ThreadPoolExecutor mailThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(true).build();
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueCapacity),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    public ThreadPoolExecutor singleThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(true).build();
        return new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}

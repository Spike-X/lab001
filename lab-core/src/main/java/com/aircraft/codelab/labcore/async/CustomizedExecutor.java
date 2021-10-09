package com.aircraft.codelab.labcore.service.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2021-06-29
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class CustomizedExecutor {

    public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, workQueue, new NameTreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    static class NameTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            log.debug("thread {} has been created", t.getName());
            return t;
        }
    }
}

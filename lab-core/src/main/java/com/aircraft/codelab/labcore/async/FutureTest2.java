package com.aircraft.codelab.labcore.service.thread;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 2021-06-30
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class FutureTest2 {
    private static final ThreadPoolExecutor threadPoolExecutor = CustomizedExecutor.newThreadPoolExecutor
            (2, 4, 10000L, new ArrayBlockingQueue<>(1));

    public void callableSubmit() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int poolSize = threadPoolExecutor.getPoolSize();
        log.debug("线程池线程数量: {}", poolSize);
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Future<String> submit = threadPoolExecutor.submit(new ThreadTest3(i));
            futureList.add(submit);
        }
        futureList.forEach(task -> {
            try {
                log.info("---> " + task.get(4L, TimeUnit.SECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        log.debug("time: {}", stopwatch.stop());
    }
}

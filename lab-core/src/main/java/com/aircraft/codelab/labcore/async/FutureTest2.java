package com.aircraft.codelab.labcore.async;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
            Future<String> submit = threadPoolExecutor.submit(new ThreadTest(i));
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

    static class ThreadTest implements Callable<String> {
        private int i;

        public ThreadTest(int i) {
            this.i = i;
        }

        @Override
        public String call() throws Exception {
            i++;
            return Thread.currentThread().getName() + "返回值: " + i;
        }
    }
}

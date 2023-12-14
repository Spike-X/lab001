package com.aircraft.codelab.pioneer.async;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 2021-06-28
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class FutureTest1 {

    private static final ThreadPoolExecutor threadPoolExecutor = CustomizedExecutor.newThreadPoolExecutor
            (2, 4, 10000L, new ArrayBlockingQueue<>(2));

    // 不简洁
    public void CompletableFutureTask() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int poolSize = threadPoolExecutor.getPoolSize();
        log.debug("线程池线程数量: {}", poolSize);

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            Thread thread = Thread.currentThread();
            log.debug("子线程callback: {}", thread);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, threadPoolExecutor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            Thread thread = Thread.currentThread();
            log.debug("子线程future: {}", thread);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future";
        }, threadPoolExecutor);

        String path = "FilePath";
        CompletableFuture<String> thenCombine = future1.thenCombine(future2, (__, f2) -> {
            log.debug("子线程filepath: {}", Thread.currentThread());
            log.debug("f2: {}", f2);
            return returnMethod(path);
        });

//        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
//            log.debug("子线程filepath: {}", Thread.currentThread());
//            return returnMethod(path);
//        }, threadPoolExecutor);
//
//        CompletableFuture.allOf(completableFuture, future, supplyAsync).join();
//        try {
//            log.info("{}", completableFuture.get());
//            log.info("{}", future.get());
//            log.info("{}", supplyAsync.get());
//        } catch (InterruptedException | ExecutionException e) {
//            log.error(e.getCause().getMessage(), e);
//        }

        log.debug("{}", thenCombine.join());
        log.debug("time: {}", stopwatch.stop());
    }


    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            // Some computation
            return "Hello";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            // Some computation
            return "World";
        });

        CompletableFuture<String> thenCombine = future1.thenCombine(future2, (__, f2) -> {
            // Some computation using the results of future1 and future2
            return __ + " " + f2;
        });

        thenCombine.thenAccept(System.out::println);
    }


    private String returnMethod(String path) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return path.toLowerCase(Locale.ROOT);
    }
}

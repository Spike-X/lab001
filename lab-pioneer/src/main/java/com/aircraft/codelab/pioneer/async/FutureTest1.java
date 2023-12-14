package com.aircraft.codelab.pioneer.async;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
        /*CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
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

        thenCombine.thenAccept(System.out::println);*/

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            Thread thread1 = Thread.currentThread();
            log.debug("thread1: {}", thread1.getName() + "_" + thread1.getId());
            // 执行耗时操作
//                int a = 1/0;
            return "result1";
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            Thread thread2 = Thread.currentThread();
            log.debug("thread2: {}", thread2.getName() + "_" + thread2.getId());
            // 执行耗时操作
            try {
//                int a = 1/0;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
            return 42;
        });

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2);
        Thread thread3 = Thread.currentThread();
        log.debug("thread3: {}", thread3.getName() + "_" + thread3.getId());
        CompletableFuture<Void> finalResult = allFutures.thenComposeAsync(voidResult -> {
            Thread thread4 = Thread.currentThread();
            log.debug("thread4: {}", thread4.getName() + "_" + thread4.getId());
            if (!future1.isCompletedExceptionally() && !future2.isCompletedExceptionally()) {
                return CompletableFuture.completedFuture(null)
                        .thenAccept(ignored -> {
                            try {
                                String result1 = future1.get();
                                Integer result2 = future2.get();
                                log.debug("result1: {}, result2: {}", result1, result2);
                                Thread thread5 = Thread.currentThread();
                                log.debug("thread5: {}", thread5.getName() + "_" + thread5.getId());
                                // 执行操作处理返回值
                            } catch (InterruptedException | ExecutionException e) {
                                log.error(e.getMessage(), e);
                                // 处理获取返回值时的异常情况
                            }
                        });
            } else {
                return CompletableFuture.completedFuture(null);
            }
        });
        Thread thread6 = Thread.currentThread();
        log.debug("thread6: {}", thread6.getName() + "_" + thread6.getId());
        finalResult.join(); // 等待所有操作完成
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

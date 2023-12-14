package com.aircraft.codelab.pioneer.async;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FutureTest4 {

    private static final Logger log = LoggerFactory.getLogger(FutureTest4.class);

    /**
     * 通义灵码对FutureTest3里面的streamSubmit优化方案 待检验
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建一个线程池
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(10);

        Stopwatch stopwatch = Stopwatch.createStarted();
        int poolSize = ((ThreadPoolExecutor) threadPoolExecutor).getPoolSize();
        log.debug("线程池线程数量: {}", poolSize);

        // 任务列表
        List<Integer> taskList = Arrays.asList(1, 3, 5, 7, 9);

        // 使用CompletableFuture处理异步任务
        CompletableFuture<Void> allComplete = CompletableFuture.allOf(
                taskList.stream()
                        .map(task -> submitAsyncCalculation(task, threadPoolExecutor))
                        .toArray(CompletableFuture<?>[]::new)
        );

        // 等待所有任务完成
        allComplete.join();

        // 关闭线程池
        threadPoolExecutor.shutdown();
        if (!threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            threadPoolExecutor.shutdownNow();
        }

        log.debug("resultList: {},time: {}", assembleResultList(taskList), stopwatch.stop());
    }

    private static <T> CompletableFuture<Void> submitAsyncCalculation(T task, ExecutorService executorService) {
        return CompletableFuture.runAsync(() -> {
            try {
                int result = calc(task);
                log.info("Result of task " + task + ": " + (result + 1));
            } catch (Exception e) {
                log.error("Error processing task: ", e);
            }
        }, executorService);
    }

    private static List<String> assembleResultList(List<Integer> taskList) {
        List<String> resultList = new ArrayList<>(taskList.size());
        for (int number : taskList) {
            resultList.add(Integer.toString(number + 1));
        }
        return resultList;
    }

    private static int calc(Object task) {
        // 这里应该放置具体的计算逻辑
        return ((Number) task).intValue() + 1;
    }

    private static Stopwatch assembleStopwatch() {
        // 这里应该从已有代码中提取Stopwatch的创建逻辑，而不是在main方法中直接创建
        return Stopwatch.createStarted();
    }
}

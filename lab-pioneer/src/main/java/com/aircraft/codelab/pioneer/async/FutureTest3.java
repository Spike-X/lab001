package com.aircraft.codelab.pioneer.async;

import com.aircraft.codelab.pioneer.pojo.vo.UserVo;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 2021-07-02
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class FutureTest3 {
    private static final ThreadPoolExecutor threadPoolExecutor = CustomizedExecutor.newThreadPoolExecutor
            (10, 15, 10000L, new ArrayBlockingQueue<>(5));

    // 结果集偶尔少数据！！！
    public void streamSubmit() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int poolSize = threadPoolExecutor.getPoolSize();
        log.debug("线程池线程数量: {}", poolSize);
        // 结果集
        List<String> resultList = new ArrayList<>();
        List<Integer> taskList = Arrays.asList(1, 3, 5, 7, 9);
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture<?>[] cfs = taskList.stream()
                .map(integer -> CompletableFuture.supplyAsync(() -> calc(integer), threadPoolExecutor)
                        .thenApply(h -> Integer.toString(h + 1))
                        .whenComplete((s, e) -> {
                            if (e != null) {
                                log.error(e.getMessage(), e);
                            }
                            resultList.add(s);
                        })
                ).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs).join();
        log.debug("resultList: {},time: {}", resultList, stopwatch.stop());
    }

    public void streamSub() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int poolSize = threadPoolExecutor.getPoolSize();
        log.debug("线程池线程数量: {}", poolSize);
        List<Integer> taskList = Arrays.asList(1, 3, 5, 7, 9);
        List<CompletableFuture<String>> completableList = taskList.stream()
                .map(this::calcAsync)
                .map(f -> f.thenApply(i -> Integer.toString(i + 1)))
                .map(f -> f.whenComplete((s, e) -> {
                    if (e != null) {
                        log.error(e.getMessage(), e);
                    }
                })).collect(Collectors.toList());
        List<String> resultList = completableList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        log.debug("resultList: {},time: {}", resultList, stopwatch.stop());
    }

    public void futureAllOf() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Integer integer = 1;
        Long aLong = 2L;
        List<CompletableFuture<?>> completableFutures = Lists.newArrayList(
                // 3秒
                CompletableFuture.supplyAsync(() -> calc(integer), threadPoolExecutor),
                // <1秒
                CompletableFuture.supplyAsync(() -> calcObj(aLong), threadPoolExecutor));

        CompletableFuture<Void> future = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
        future.join();
        log.debug("time: {}", stopwatch.stop());
    }

    private CompletableFuture<Integer> calcAsync(Integer i) {
        return CompletableFuture.supplyAsync(() -> calc(i), threadPoolExecutor);
    }

    private int calc(Integer i) {
        try {
            if (i == 1) {
                //任务1耗时3秒
                Thread.sleep(3000);
            } else if (i == 5) {
                //任务5耗时5秒
                Thread.sleep(5000);
            } else {
                //其它任务耗时1秒
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    private UserVo calcObj(Long i) {
        UserVo userVo = new UserVo();
        userVo.setId(i);
        return userVo;
    }
}

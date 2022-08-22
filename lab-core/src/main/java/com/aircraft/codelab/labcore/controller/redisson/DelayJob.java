package com.aircraft.codelab.labcore.controller.redisson;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 2022-07-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Component
public class DelayJob implements ApplicationRunner {
//    @Resource
    private DelayProducer delayProducer;

//    @Resource
    private RBlockingDeque<DelayDto> blockingDeque;

//    @Resource
    private RDelayedQueue<DelayDto> delayedQueue;

    @Resource(name = "mailThreadPoolExecutor")
    private ThreadPoolExecutor executor;

    @Resource(name = "singleThreadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run(ApplicationArguments args) {
        /*threadPoolExecutor.execute(() -> {
            while (true) {
                try {
                    DelayDto dto = blockingDeque.take();
                    log.info("listen 从队列中获取需要查询的任务信息：{}", JSON.toJSONString(dto));
                    CompletableFuture.runAsync(() -> delayProducer.accept(dto), executor);
                } catch (InterruptedException e) {
                    log.error("listen InterruptedException,error msg:{}", ExceptionUtils.getMessage(e));
                }
            }
        });*/
        log.info("Redission延迟队列启动成功");
    }

    /*@Override
    public void run(String... args) {
        new Thread(() -> {
            while (true) {
                try {
                    DelayDto dto = blockingDeque.take();
                    log.info("listen 从队列中获取需要查询的任务信息：{}", JSON.toJSONString(dto));
                    synthesisResultConsumer.accept(dto);
                } catch (InterruptedException e) {
                    log.error("listen InterruptedException,error msg:{}", ExceptionUtils.getMessage(e));
                }
            }
        }).start();
        log.info("Redission延迟队列启动成功");
    }*/
}

package com.aircraft.codelab.labcore.controller.redisson;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RBlockingDeque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 2022-07-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Component
public class DelayJob implements ApplicationRunner {
    @Autowired
    private DelayConsumer<DelayDto> synthesisResultConsumer;

    @Autowired
    private RBlockingDeque<DelayDto> blockingDeque;

    @Override
    public void run(ApplicationArguments args) {
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

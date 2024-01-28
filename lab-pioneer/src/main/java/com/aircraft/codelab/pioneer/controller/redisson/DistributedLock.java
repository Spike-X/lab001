package com.aircraft.codelab.pioneer.controller.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 2024-01-28
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
public class DistributedLock {
    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/rLock/{id}")
    public String rLock(@PathVariable String id) {
        RLock rLock = redissonClient.getLock("wallet:update:" + id);
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            log.info("我获得了锁！！！");
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("服务异常", e);
        } finally {
            rLock.unlock();
            log.info("我释放了锁！！");
        }
        return "1";
    }
}

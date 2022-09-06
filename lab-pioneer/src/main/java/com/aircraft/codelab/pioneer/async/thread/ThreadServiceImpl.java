package com.aircraft.codelab.pioneer.async.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * 2021-11-04
 *
 * @author tao.zhang
 * @since 1.0
 */
@Service
@Slf4j
public class ThreadServiceImpl implements ThreadService {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    private final LongAdder longAdder = new LongAdder();
    private int count;

    @Override
    public int getAndIncrement() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count = count + 1;
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!atomicInteger.compareAndSet(current, next));
        log.debug("count: {}", count);
        log.debug("*****第{}次访问*****", next);
        return next;
    }
}

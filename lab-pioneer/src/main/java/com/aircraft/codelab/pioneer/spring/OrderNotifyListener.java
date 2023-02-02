package com.aircraft.codelab.pioneer.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 2023-02-02
 * 时间监听
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class OrderNotifyListener {
    @Async("asyncThread")
    @EventListener
    public void notify(PlaceOrderEvent event) {
        log.info("[afterPlaceOrder] notify.");
    }
}

package com.aircraft.codelab.pioneer.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 2023-02-02
 * 事件监听
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class OrderLogListener {
    @Async("asyncThread")
    @EventListener
    public void orderLog(PlaceOrderEvent event) {
        log.info("[afterPlaceOrder] log.");
        PlaceOrderEventMessage placeOrderEventMessage = (PlaceOrderEventMessage) event.getSource();
        PlaceOrderEventMessage placeOrderEventMessage1 = event.getPlaceOrderEventMessage();
    }
}

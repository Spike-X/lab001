package com.aircraft.codelab.pioneer.spring;

import org.springframework.context.ApplicationEvent;

/**
 * 2023-02-02
 * Spring Event 实现发布/订阅模式
 *
 * @author tao.zhang
 * @since 1.0
 */
public class PlaceOrderEvent extends ApplicationEvent {
    private final PlaceOrderEventMessage placeOrderEventMessage;

    public PlaceOrderEvent(PlaceOrderEventMessage source) {
        super(source);
        this.placeOrderEventMessage = source;
    }

    public PlaceOrderEventMessage getPlaceOrderEventMessage() {
        return placeOrderEventMessage;
    }
}

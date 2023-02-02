package com.aircraft.codelab.pioneer.spring;

import lombok.Data;

/**
 * 2023-02-02
 * 事件的消息体
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class PlaceOrderEventMessage {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 下单用户ID
     */
    private String userId;
}

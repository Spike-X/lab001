package com.aircraft.codelab.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 2022-07-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@Configuration
public class Config {
    final static String QUEUE_NAME = "delayed_goods_order";
    final static String QUEUE_NAME_1 = "delayed_goods_order_1";
    final static String EXCHANGE_NAME = "my.delayed.exchange";
    final static String X_DELAYED_MESSAGE = "x-delayed-message";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME_1);
    }

    /**
     * 最后一个 args 参数中，指定了交换机消息分发的类型，这个类型就是大家熟知的 direct、fanout、topic 以及 header 几种，
     * 用了哪种类型，将来交换机分发消息就按哪种方式来。
     *
     * @return CustomExchange
     */
    @Bean
    CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", ExchangeTypes.DIRECT);
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange(EXCHANGE_NAME, X_DELAYED_MESSAGE, true, false, args);
    }

    @Bean
    Binding binding() {
        // 绑定队列到交换机
        return BindingBuilder.bind(queue()).to(customExchange()).with(QUEUE_NAME).noargs();
    }
}

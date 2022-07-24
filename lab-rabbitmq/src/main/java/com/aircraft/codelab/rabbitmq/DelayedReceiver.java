package com.aircraft.codelab.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 2022-07-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
public class DelayedReceiver {
    @RabbitListener(queues = Config.QUEUE_NAME)
    @RabbitHandler
    public void process(String msg) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("接收时间:" + format);
        System.out.println("消息内容：" + msg);
    }

    @RabbitListener(queues = Config.QUEUE_NAME_1)
    @RabbitHandler
    public void process(DelayMessage delayMessage) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("接收时间:" + format);
        System.out.println("消息内容：" + delayMessage.toString());
    }
}

package com.aircraft.codelab.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 2022-07-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
public class DelayedSender {
    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("发送时间：" + format);

        rabbitTemplate.convertAndSend(Config.EXCHANGE_NAME, Config.QUEUE_NAME, msg, message -> {
            message.getMessageProperties().setDelay(10000);
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
    }

    public void sendVo(DelayMessage msg) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("发送时间：" + format);
        rabbitTemplate.convertAndSend(Config.QUEUE_NAME_1, msg);
    }
}

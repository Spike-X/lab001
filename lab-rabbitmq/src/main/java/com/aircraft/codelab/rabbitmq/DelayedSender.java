package com.aircraft.codelab.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
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
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, message,
                msg -> {
                    msg.getMessageProperties().setDelay(10 * 1000);
                    msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return msg;
                });
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("发送时间：" + format);
    }

    public void sendVo(DelayMessage msg) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("发送时间：" + format);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.QUEUE_NAME_1, msg);
    }

    public void sendLazy(String message, Integer delayTime) {
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, message,
                msg -> {
                    //发送消息的时候的延迟时长 单位ms
                    msg.getMessageProperties().setDelay(delayTime);
                    return msg;
                });
    }

    public void sendLazy(String message, int delayTime) {
        Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .setHeader("x-delay", 3000).build();
        rabbitTemplate.send(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, msg);
    }


}

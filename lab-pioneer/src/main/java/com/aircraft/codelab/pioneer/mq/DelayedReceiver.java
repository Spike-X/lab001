package com.aircraft.codelab.pioneer.mq;

import com.aircraft.codelab.rabbitmq.DelayedQueueConfig;
import com.aircraft.codelab.rabbitmq.DelayMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 2022-07-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Component
public class DelayedReceiver {
    @RabbitListener(queues = DelayedQueueConfig.QUEUE_NAME_1)
    @RabbitHandler
    public void process(String msg) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("接收时间:" + format);
        System.out.println("消息内容：" + msg);
    }

    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message) {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }

    @RabbitListener(queues = DelayedQueueConfig.QUEUE_NAME_1)
    @RabbitHandler
    public void process(DelayMessage delayMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            log.info("接收时间: {}", format);
            System.out.println("消息内容：" + delayMessage.toString());
            int a = 100 / 0;
            // 业务逻辑处理成功，告诉RabbitMQ，已经接收到消息并做了处理了。这样消息队列这条消息才算真正消费成功
//            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 处理过程中，发生了不是业务逻辑的错误异常，则不答复ACK，这样MQ会认为这条消息未成功消费，所以会重新把该条消息放回队列中，直到ACK正常答复
//            channel.basicNack(tag, false, true);
        }
    }
}

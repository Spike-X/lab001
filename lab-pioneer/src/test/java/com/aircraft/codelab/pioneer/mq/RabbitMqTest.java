package com.aircraft.codelab.pioneer.mq;

import com.aircraft.codelab.rabbitmq.DelayMessage;
import com.aircraft.codelab.rabbitmq.DelayedSender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 2022-07-24
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class RabbitMqTest {
    @Resource
    private DelayedSender sender;

    @Test
    void delayedMessage() throws InterruptedException {
        sender.send("Hi Admin.");
        TimeUnit.SECONDS.sleep(300);
    }

    @Test
    void delayedMessage1() throws InterruptedException {
        sender.sendVo("delayMessage");
        TimeUnit.SECONDS.sleep(300);
    }

    @Test
    void sendLazy() throws InterruptedException {
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setRetryTime(1);
        delayMessage.setTaskNo("RW123124");
        sender.sendLazy(delayMessage, 100);
        TimeUnit.SECONDS.sleep(300);
    }

    @Test
    void sendLazy1() throws InterruptedException {
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setRetryTime(1);
        delayMessage.setTaskNo("RW123124");
        sender.sendLazy("delayMessage", 10);
        TimeUnit.SECONDS.sleep(300);
    }
}

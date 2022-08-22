package com.aircraft.codelab.labcore.mq;

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
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setRetryTime(1);
        delayMessage.setTaskNo("RW123124");
        sender.sendVo(delayMessage);
        TimeUnit.SECONDS.sleep(300);
    }
}

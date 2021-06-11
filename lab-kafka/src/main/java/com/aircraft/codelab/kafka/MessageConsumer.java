/*
 * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aircraft.codelab.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 2020-12-06
 * KafkaConsumer
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
@Slf4j
public class MessageConsumer {
    /**
     * 批量消费
     *
     * @param records List<ConsumerRecord>
     * @param ack     Acknowledgment
     */
//    @KafkaListener(topicPartitions = {@TopicPartition(topic = "${spring.kafka.template.default-topic}", partitions = {"0", "1", "2"})}, containerFactory = "containerFactory")
    public void receiveMessage(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        try {
            records.forEach(record -> {
                Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                if (kafkaMessage.isPresent()) {
                    log.info("key:{},value:{},partition:{},offset:{}", record.key(), record.value(), record.partition(), record.offset());
                }
            });
        } catch (Exception e) {
            log.error("Kafka监听异常" + e.getMessage(), e);
        } finally {
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = {"${spring.kafka.template.default-topic}"})
    public void receiveMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        try {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                log.info("key:{},value:{},partition:{},offset:{}", record.key(), record.value(), record.partition(), record.offset());
            }
        } catch (Exception e) {
            log.error("Kafka监听异常" + e.getMessage(), e);
        } finally {
            ack.acknowledge();
        }
    }
}

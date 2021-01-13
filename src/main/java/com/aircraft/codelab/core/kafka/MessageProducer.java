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

package com.aircraft.codelab.core.kafka;

import lombok.extern.slf4j.Slf4j;

/**
 * 2020-12-06
 * KafkaProducer
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
@Slf4j
public class MessageProducer {
@Value("${spring.kafka.template.default-topic}")
    private String topicName;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendAsync(String key, Object message) {
        String value = "";
        try {
            value = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

        // 默认异步发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        // 消息回调
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                int partition = result.getRecordMetadata().partition();
                long offset = result.getRecordMetadata().offset();
                log.info("send success: key={},value={},partition={},offset={}", key, message, partition, offset);
            }

            @Override
            public void onFailure(@NonNull Throwable ex) {
                log.error("send message={} failure: detail message={}", message, ex.getMessage());
            }
        });

        future.addCallback(result -> {
                    assert result != null;
                    int partition = result.getRecordMetadata().partition();
                    long offset = result.getRecordMetadata().offset();
                    log.info("send success: key={},value={},partition={},offset={}", key, message, partition, offset);
                },
                ex -> log.error("send message={} failure: detail message={}", message, ex.getMessage()));
    }
}

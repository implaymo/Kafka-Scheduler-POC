/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.event.driven.notifier.domain.entities.Notification;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<UUID, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        String topicName = "notificationTopic";
        kafkaTemplate.send(topicName, notification.getId(), notification);
        log.info("Message sent to Kafka: {}", notification);
    }
}

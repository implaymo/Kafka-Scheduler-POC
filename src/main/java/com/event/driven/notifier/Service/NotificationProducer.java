/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.event.driven.notifier.Domain.Notification;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, Notification> kafkaTemplate;
    private final String topicName = "notificationTopic";

    public void sendNotification(Notification notification) {
        UUID uuid = UUID.randomUUID();
        notification.setNotificationID(uuid.toString());
        kafkaTemplate.send(topicName, notification.getNotificationID(), notification);
        log.info("Message sent to Kafka: {}", notification);
    }
}

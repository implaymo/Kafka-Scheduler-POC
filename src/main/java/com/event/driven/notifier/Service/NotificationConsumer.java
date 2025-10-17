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

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.event.driven.notifier.Domain.Notification;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;

    @KafkaListener(topics = "notificationTopic", groupId = "group-id")
    public void notificationListener(Notification notification) {
        Instant triggerTime = Instant.now().plus(1, ChronoUnit.MINUTES);

        log.info("Scheduling notification for: {}", triggerTime);

        taskScheduler.schedule(
                () -> notificationService.sendAndSaveNotification(notification),
                triggerTime
        );    }
}

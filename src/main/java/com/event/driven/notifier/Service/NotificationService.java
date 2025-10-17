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

import org.springframework.stereotype.Service;

import com.event.driven.notifier.Domain.Notification;
import com.event.driven.notifier.Repository.NotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendAndSaveNotification(Notification notification) {
        log.info("Sending notification: {}", notification);
        notificationRepository.save(notification);
        log.info("Notification sent and saved to database!");
    }
}

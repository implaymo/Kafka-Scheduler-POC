/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.event.driven.notifier.Domain.Notification;
import com.event.driven.notifier.Dto.NotificationDto;
import com.event.driven.notifier.Mapper.NotificationMapperInterface;
import com.event.driven.notifier.Service.NotificationProducer;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducer notificationProducer;
    private final NotificationMapperInterface notificationMapper;


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody NotificationDto notificationDto) {
        try {
            Notification notification = notificationMapper.toEntity(notificationDto);
            notificationProducer.sendNotification(notification);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Message " + notification.getMessage());
        } catch (Exception e) {
            log.info("Exception {}", String.valueOf(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ERROR: " + e);
        }
    }
}

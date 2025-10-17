package com.event.driven.notifier.Dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */

class NotificationDtoTest {

    @Test
    void shouldCreateNotificationDto() {
        // arrange
        // act
        NotificationDto notificationDto = NotificationDto
                .builder()
                .message("Message")
                .build();
        // assert
        assertNotNull(notificationDto);
    }

    @Test
    void shouldNotCreateNotificationDtoIfMessageNull() {
        // arrange
        // act
        // assert
        assertThrows(NullPointerException.class, () -> {
            NotificationDto.builder().build();
        });
    }
}
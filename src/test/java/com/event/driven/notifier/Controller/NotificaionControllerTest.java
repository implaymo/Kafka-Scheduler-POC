
/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */


package com.event.driven.notifier.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import com.event.driven.notifier.Dto.NotificationDto;
import com.event.driven.notifier.TestContainersConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainersConfiguration.class)
class NotificationControllerTest {


    RestTestClient client;


    @BeforeEach
    void setUp(WebApplicationContext context) {
        client = RestTestClient.bindToApplicationContext(context)
                .build();
    }

    @Test
    void shouldSendOkStatusCodeIfRequestExecutedSuccessfully() {

        NotificationDto notificationDto = NotificationDto.builder()
                .message("Message")
                .build();

        client.post().uri(uriBuilder -> uriBuilder.path("/send").build())
                .header("Content-Type", "application/json")
                .body(notificationDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldSendBadRequestStatusCodeIfBadRequest() {
        // arrange
        // act & assert
        client.post().uri(uriBuilder -> uriBuilder.path("/send").build())
                .header("Content-Type", "application/json")
                .body("{}")
                .exchange()
                .expectStatus().isBadRequest();
    }
}

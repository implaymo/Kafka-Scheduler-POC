package com.event.driven.notifier.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.event.driven.notifier.Domain.Notification;
import com.event.driven.notifier.Dto.NotificationDto;
import com.event.driven.notifier.Mapper.NotificationMapperInterface;
import com.event.driven.notifier.Service.NotificationProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
@WebMvcTest
class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    NotificationMapperInterface notificationMapperInterface;

    @MockitoBean
    NotificationProducer notificationProducer;


    @Test
    void shouldSendOkStatusCodeIfRequestExecutedSuccessfully() throws Exception {
        NotificationDto notificationDto = NotificationDto
                .builder()
                .message("Message")
                .build();

        Notification notification = Notification
                .builder()
                .message("Message")
                .build();
        String jsonContent = objectMapper.writeValueAsString(notificationDto);
        when(notificationMapperInterface.toEntity(notificationDto)).thenReturn(notification);

        mockMvc.perform(post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSendBadRequestStatusCodeIfBadRequest() throws Exception {
        // arrange
        // act & assert
        mockMvc.perform(post("/send")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
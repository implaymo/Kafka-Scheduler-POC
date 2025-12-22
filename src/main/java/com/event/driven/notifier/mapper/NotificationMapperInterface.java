/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.mapper;

import org.mapstruct.Mapper;

import com.event.driven.notifier.domain.Notification;
import com.event.driven.notifier.dto.NotificationDto;


@Mapper(componentModel =  "spring")
public interface NotificationMapperInterface {
    Notification toEntity(NotificationDto notificationDto);
}

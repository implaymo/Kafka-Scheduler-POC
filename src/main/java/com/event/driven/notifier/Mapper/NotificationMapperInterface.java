/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.Mapper;

import org.mapstruct.Mapper;

import com.event.driven.notifier.Domain.Notification;
import com.event.driven.notifier.Dto.NotificationDto;


@Mapper(componentModel =  "spring")
public interface NotificationMapperInterface {
    Notification toEntity(NotificationDto notificationDto);
}

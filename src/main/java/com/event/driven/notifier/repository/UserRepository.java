/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event.driven.notifier.domain.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}

/*
 * Copyright (c) Present Technologies Lda., All Rights Reserved.
 * (www.present-technologies.com)
 *
 * This software is the proprietary information of Present Technologies Lda.
 * Use is subject to license terms.
 */
package com.event.driven.notifier.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.driven.notifier.dto.UserRegisterDto;

@RestController("/auth")
public class AuthController {


    @PostMapping("/register")
    public void register(UserRegisterDto userDto) {

    }
}

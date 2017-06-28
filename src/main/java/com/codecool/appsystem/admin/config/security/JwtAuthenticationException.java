package com.codecool.appsystem.admin.config.security;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    JwtAuthenticationException(String msg) {
        super(msg);
    }

}


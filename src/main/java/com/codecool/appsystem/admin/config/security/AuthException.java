package com.codecool.appsystem.admin.config.security;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    public AuthException(String msg) {
        super(msg);
    }

}

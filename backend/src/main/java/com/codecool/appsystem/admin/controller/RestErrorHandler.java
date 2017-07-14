package com.codecool.appsystem.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestErrorHandler.class);

    @Value("${server.frontend.basepath}")
    private String serverUrl;

    @Value("${server.redirecturl.errorpath}")
    private String errorPath;

    @ExceptionHandler(Exception.class)
    public void onError(HttpServletResponse httpServletResponse, Exception e) throws IOException {

        log.error(e.getMessage(),e);
        String url = UriComponentsBuilder
                .fromUriString(serverUrl)
                .path(errorPath)
                .queryParam("errorMessage", e.getMessage())
                .build()
                .toString();
        httpServletResponse.sendRedirect(url);
    }

}

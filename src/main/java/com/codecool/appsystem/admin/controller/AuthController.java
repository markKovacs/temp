package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.service.Auth0TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private Auth0TokenService auth0TokenService;

    @Value("${server.frontend.basepath}")
    private String serverUrl;

    @Value("${server.redirecturl.loginpath}")
    private String loginPath;

    @Value("${server.redirecturl.errorpath}")
    private String errorPath;

    @RequestMapping(method = RequestMethod.GET)
    public void login(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(
                auth0TokenService.getAuthRedirectUrl()
        );
    }

    @RequestMapping("/api/login/callback")
    public void callback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription,
            HttpServletResponse httpServletResponse
    ) throws IOException, AuthException {

        log.trace("Callback, code: {}, state: {}", code, state);

        if(!StringUtils.isEmpty(error) || !StringUtils.isEmpty(errorDescription)){
            throw new com.codecool.appsystem.admin.config.security.AuthException(errorDescription);
        }

        if(!StringUtils.isEmpty(code)) {

            String jwtToken = auth0TokenService.authenticate(code, state);

            String url = UriComponentsBuilder
                    .fromUriString(serverUrl)
                    .path(loginPath)
                    .queryParam("jwt", jwtToken)
                    .build()
                    .toString();

            log.trace("Redirecting user to login url: {}", url);

            httpServletResponse.sendRedirect(url);

        }

    }

}

package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.config.security.JwtTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.security.auth.message.AuthException;
import java.net.URI;
import java.util.Date;
import java.util.Map;

@Service
public class Auth0TokenService {

    private static final Logger log = LoggerFactory.getLogger(Auth0TokenService.class);

    @Value("${auth0.oauth.clientId}")
    private String clientId;

    @Value("${auth0.oauth.secretKey}")
    private String clientSecret;

    @Value("${auth0.oauth.tokenUrl}")
    private String tokenUrl;

    @Value("${server.redirecturl.basepath}")
    private String redirectUrlBasePath;

    @Value("${auth0.oauth.tokenExchangeUrl}")
    private String tokenExchangeURL;

    @Value("${auth0.oauth.userInfoUrl}")
    private String userInfoUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    public String getAuthRedirectUrl(){

        return UriComponentsBuilder.fromUriString(tokenUrl)
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUrlBasePath + "/api/login/callback")
                .queryParam("scope", "email")
                .queryParam("additional-parameter", "access_type=offline")
                .build().toString();
    }

    public String authenticate(String code, String state) throws AuthException {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUrlBasePath + "/api/login/callback");
        body.add("code", code);
        body.add("grant_type", "authorization_code");

        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(tokenExchangeURL, body, Map.class);

        log.trace("Callback token exchange request sent, response: {}", mapResponseEntity);

        String accessToken = (String) mapResponseEntity.getBody().get("access_token");

        Integer expiresIn = (Integer) mapResponseEntity.getBody().get("expires_in");

        // accessToken expires is given in seconds, convert it to milliseconds
        Date expires = new Date(new Date().getTime() + expiresIn * 1000);

        try {

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Authorization", "Bearer " + accessToken);

            RequestEntity<Map> request = new RequestEntity<Map>(headers, HttpMethod.GET, new URI(userInfoUrl));

            ResponseEntity<Map> userInfoData = restTemplate.exchange(
                    request,
                    Map.class
            );

            log.trace("User info received: {}", userInfoData.getBody());


            String jwtToken = jwtTokenGenerator.generateToken((String) userInfoData.getBody().get("email"), accessToken);

            log.trace("JWT Token generated: {}", jwtToken);

            return jwtToken;

        } catch (Exception e){
            log.error("Error occurred during authentication: {}", e.getMessage(), e);
            throw new AuthException(e.getMessage());
        }


    }

}

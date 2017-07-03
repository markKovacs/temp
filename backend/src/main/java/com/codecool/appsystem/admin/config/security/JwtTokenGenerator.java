package com.codecool.appsystem.admin.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

@Component
public class JwtTokenGenerator {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenGenerator.class);

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @PostConstruct
    public void checkSecretKey(){
        Assert.hasLength(jwtSecretKey, "JWT secret key must be provided.");
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param email of the logged in user
     * @return the JWT token
     */
    public String generateToken(String email, String token) {

        Claims claims = Jwts.claims()
                .setSubject(email);

        claims.put("email", email);
        claims.put("token", token);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtSecretKey.getBytes(Charset.forName("UTF-8")))
                .compact();
    }

}

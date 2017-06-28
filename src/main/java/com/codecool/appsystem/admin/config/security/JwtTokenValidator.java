package com.codecool.appsystem.admin.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class JwtTokenValidator {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenValidator.class);

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    String parseToken(String token) {

        log.trace("Validating JWT token: {}", token);

        String email = null;

        try {

            Claims body = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(Charset.forName("UTF-8")))
                    .parseClaimsJws(token)
                    .getBody();

            email = body.getSubject();

        } catch (JwtException e) {
            log.error("Error in token validation", e);
        }

        log.trace("Token validated, user: {}", email);

        return email;
    }

}

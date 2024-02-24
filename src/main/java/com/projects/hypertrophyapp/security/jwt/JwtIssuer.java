package com.projects.hypertrophyapp.security.jwt;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties jwtProperties;

    public String issueToken(String username, List<String> roles) {
        return JWT.create()
                    .withSubject(username)
                    .withExpiresAt(Instant.now().plus(Duration.of(8, ChronoUnit.HOURS)))
                    .withClaim("authority",roles)
                    .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}

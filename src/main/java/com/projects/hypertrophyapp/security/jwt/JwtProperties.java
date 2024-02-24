package com.projects.hypertrophyapp.security.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("hypertrophy.security.jwt")
public class JwtProperties {

    private String secretKey;
}

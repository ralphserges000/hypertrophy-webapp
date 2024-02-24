package com.projects.hypertrophyapp.security.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.projects.hypertrophyapp.security.UserPrincipalAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtDecoder decoder;
    private final JwtToUserPrincipalConverter converter;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(JwtDecoder decoder, JwtToUserPrincipalConverter converter, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.decoder = decoder;
        this.converter = converter;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            extractJwtFromRequest(request)
                .map(decoder::decode)
                .map(converter::convertDecodedJwtToUserPrincipal)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
            
            filterChain.doFilter(request, response);

        }catch(TokenExpiredException expiredTokenException) {
            resolver.resolveException(request, response, null, expiredTokenException);
        }
        
    }

    private Optional<String> extractJwtFromRequest(HttpServletRequest request) {
        var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) return Optional.of(token.substring(7));

        return Optional.empty();

    }

}

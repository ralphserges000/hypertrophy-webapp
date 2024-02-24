package com.projects.hypertrophyapp.security.exceptionhandler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UnauthorizedAccessHandler implements AuthenticationEntryPoint {

    private HandlerExceptionResolver resolver;
    
    public UnauthorizedAccessHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
                    AuthenticationException authException) throws IOException, ServletException {
                        
        resolver.resolveException(request, response, null, authException);
    }

}

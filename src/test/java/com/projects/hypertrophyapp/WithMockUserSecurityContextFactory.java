package com.projects.hypertrophyapp;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.projects.hypertrophyapp.security.UserPrincipal;
import com.projects.hypertrophyapp.security.UserPrincipalAuthenticationToken;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser>{

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        var authorities = Arrays.stream(annotation.authorities())
                                .map(SimpleGrantedAuthority::new)
                                .toList();

        var principal = UserPrincipal.builder()
                                    .username(annotation.username())
                                    .password("pw123")
                                    .authorities(authorities)
                                    .build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
        return context;
    }

}

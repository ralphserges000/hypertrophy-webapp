package com.projects.hypertrophyapp.security.jwt;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projects.hypertrophyapp.security.UserPrincipal;

@Component
public class JwtToUserPrincipalConverter {

    public UserPrincipal convertDecodedJwtToUserPrincipal(DecodedJWT jwt) {
        return UserPrincipal.builder()
                            .username(jwt.getSubject())
                            .authorities(extractAuthorityFromClaims(jwt))
                            .build();
    }

    private List<SimpleGrantedAuthority> extractAuthorityFromClaims(DecodedJWT jwt) {
        var claim = jwt.getClaim("authority");

        if(claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}

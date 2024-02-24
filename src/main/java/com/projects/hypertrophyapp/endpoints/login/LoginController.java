package com.projects.hypertrophyapp.endpoints.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.hypertrophyapp.security.UserPrincipal;
import com.projects.hypertrophyapp.security.jwt.JwtIssuer;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authManager;

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponse login(@RequestBody LoginRequest loginReq, HttpServletResponse response) {

        var authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList();
        var token = jwtIssuer.issueToken(principal.getUsername(),roles);

        return LoginResponse.builder()
                            .token(token)
                            .build();
    }
}

package com.projects.hypertrophyapp.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projects.hypertrophyapp.endpoints.registration.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService{

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repo.findUserByUsername(username).orElseThrow();
        return UserPrincipal.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                            .build();
    }
}

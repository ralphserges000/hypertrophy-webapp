package com.projects.hypertrophyapp.endpoints.registration;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class NewUserRegistrationController {

    private final UserRepository repo;
    private final PasswordEncoder pwEncoder;
    

    @PostMapping("/users/registration")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<PublicFacingUserEntity> createNewUser(@RequestBody PrivateUserEntity newUser) {
        
        if(!repo.findUserByUsername(newUser.getUsername()).isEmpty()) {
            PublicFacingUserEntity alrTakenResp = PublicFacingUserEntity.builder()
                            .username(newUser.getUsername())
                            .additionalComment("username already taken")
                            .build();
            return new ResponseEntity<>(alrTakenResp,HttpStatus.BAD_REQUEST);
        }
        else {
            String encodedPassword = pwEncoder.encode(newUser.getPassword());
            newUser.setPassword(encodedPassword);
            repo.save(newUser);
            PublicFacingUserEntity okResp = PublicFacingUserEntity.builder()
                            .username(newUser.getUsername())
                            .additionalComment("account has been created successfully")
                            .build();
            return new ResponseEntity<>(okResp,HttpStatus.OK);
        }
    }
}

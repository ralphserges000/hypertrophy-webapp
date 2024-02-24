package com.projects.hypertrophyapp.endpoints.registration;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Document(collection="users")
public class PrivateUserEntity {

    @NonNull private String username;
    @NonNull private String password;
    private String role = "ROLE_user";
     
}

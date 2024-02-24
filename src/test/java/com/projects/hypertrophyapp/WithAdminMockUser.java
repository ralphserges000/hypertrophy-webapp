package com.projects.hypertrophyapp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(authorities = "ROLE_admin")
public @interface WithAdminMockUser {

}

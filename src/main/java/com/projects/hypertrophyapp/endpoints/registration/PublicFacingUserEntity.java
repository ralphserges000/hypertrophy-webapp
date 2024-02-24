package com.projects.hypertrophyapp.endpoints.registration;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PublicFacingUserEntity {
    private String username;
    private String additionalComment;

}

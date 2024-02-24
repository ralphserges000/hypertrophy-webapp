package com.projects.hypertrophyapp.endpoints.workoutroutine.entities;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewWorkoutRoutineResponse {
    private String routineId;
    private String username;
    private LocalDateTime dateCreated;
    private String responseComment; 
}

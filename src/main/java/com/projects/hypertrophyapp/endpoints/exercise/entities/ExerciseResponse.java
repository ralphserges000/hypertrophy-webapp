package com.projects.hypertrophyapp.endpoints.exercise.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseResponse {
    private String exerciseId;
    private String name;
    private BodyParts bodyPart;
    private String toolUsed;
    private String comment;
}

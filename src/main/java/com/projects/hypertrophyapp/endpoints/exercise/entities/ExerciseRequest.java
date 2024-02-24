package com.projects.hypertrophyapp.endpoints.exercise.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseRequest {

    @NonNull private String name;
    @NonNull private BodyParts bodyPart;
    @NonNull private String toolUsed;

}

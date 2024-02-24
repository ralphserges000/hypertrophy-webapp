package com.projects.hypertrophyapp.endpoints.exercise.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "exercise_selections")
public class Exercise{
    @Id private String id;
    private String name;
    private BodyParts bodyPart;
    private String toolUsed;
    private String creatorUsername;
}

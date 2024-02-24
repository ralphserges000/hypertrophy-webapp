package com.projects.hypertrophyapp.endpoints.workoutroutine.progression;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.ExerciseVolumePerSet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document("exercise_volume_progression")
public class ExerciseVolumeProgression {
    @Id private String id;
    private LocalDateTime dateCompleted;
    private String username;
    private String exerciseId;
    private double totalVolume;
    private Set<ExerciseVolumePerSet> allSets;
}

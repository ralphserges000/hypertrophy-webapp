package com.projects.hypertrophyapp.endpoints.workoutroutine.stats;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class WorkoutRoutineCompleteStats {
    private int totalExercisesCompleted;
    private Map<String, WorkoutVolumePerSet> totalVolumePerExercise;
    private String comments;
}

package com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSetRequest {
    private String routineId;
    private Set<WorkoutSet> allSetsInARoutine;

}

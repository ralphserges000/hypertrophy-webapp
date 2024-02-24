package com.projects.hypertrophyapp.endpoints.workoutroutine.stats;

import java.util.Set;

import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.ExerciseVolumePerSet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class WorkoutVolumePerSet {
    private String toolUsed;
    private Set<ExerciseVolumePerSet> allSets;
    private double totalVolume;
}

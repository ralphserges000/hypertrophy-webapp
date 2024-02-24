package com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset;

import java.util.Set;

import com.projects.hypertrophyapp.endpoints.exercise.entities.Exercise;

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
public class WorkoutSet {
    private Exercise exercise;
    private Set<ExerciseVolumePerSet> allExerciseVolumes;
}


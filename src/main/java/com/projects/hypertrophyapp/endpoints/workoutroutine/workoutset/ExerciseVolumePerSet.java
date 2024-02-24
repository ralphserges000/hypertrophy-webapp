package com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset;

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
public class ExerciseVolumePerSet {
    private int setNum;
    private int totalReps;
    private double weight;
}

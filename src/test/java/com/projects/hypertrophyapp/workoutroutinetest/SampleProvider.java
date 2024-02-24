package com.projects.hypertrophyapp.workoutroutinetest;

import java.util.Set;

import com.projects.hypertrophyapp.endpoints.exercise.entities.BodyParts;
import com.projects.hypertrophyapp.endpoints.exercise.entities.Exercise;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.ExerciseVolumePerSet;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.WorkoutSet;

public class SampleProvider {

    public static Set<WorkoutSet> provideWorkoutSetsSamples() {

        Exercise chestExercise = Exercise.builder()
                                    .id("chestExercise11")
                                    .name("bench press")
                                    .bodyPart(BodyParts.CHEST)
                                    .toolUsed("barbell")
                                    .creatorUsername("testuser1")
                                    .build();
        Exercise chestExercise2 = Exercise.builder()
                                    .id("chestExercise11")
                                    .name("push press")
                                    .bodyPart(BodyParts.CHEST)
                                    .toolUsed("dumbbell")
                                    .creatorUsername("testuser1")
                                    .build();
        Exercise chestExercise3 = Exercise.builder()
                                    .id("chestExercise11")
                                    .name("push up")
                                    .bodyPart(BodyParts.CHEST)
                                    .toolUsed("body weight")
                                    .creatorUsername("testuser1")
                                    .build();

        Set<ExerciseVolumePerSet> allSets = Set.of(
            ExerciseVolumePerSet.builder()
                .setNum(1)
                .totalReps(10)
                .weight(52.5)
                .build(),
            ExerciseVolumePerSet.builder()
                .setNum(2)
                .totalReps(12)
                .weight(42.5)
                .build(),
            ExerciseVolumePerSet.builder()
                .setNum(3)
                .totalReps(11)
                .weight(32.5)
                .build());

        return Set.of(
            WorkoutSet.builder().exercise(chestExercise).allExerciseVolumes(allSets).build(),
            WorkoutSet.builder().exercise(chestExercise2).allExerciseVolumes(allSets).build(),
            WorkoutSet.builder().exercise(chestExercise3).allExerciseVolumes(allSets).build()
        );
    }
}

package com.projects.hypertrophyapp.endpoints.workoutroutine.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.projects.hypertrophyapp.endpoints.workoutroutine.WorkoutRoutineRepository;
import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.NewWorkoutRoutineResponse;
import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.WorkoutRoutine;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewWorkoutRoutineService {
    private final WorkoutRoutineRepository workoutRoutineRepo;

    @Async
    public CompletableFuture<NewWorkoutRoutineResponse> saveUnfinishedWorkoutRoutine(WorkoutRoutine newRoutine) {

        workoutRoutineRepo.save(newRoutine);

        return CompletableFuture.completedFuture(
            NewWorkoutRoutineResponse.builder()
                .routineId(newRoutine.getRoutineId())
                .username(newRoutine.getUsername())
                .dateCreated(newRoutine.getDateTimeCreated())
                .responseComment("successfully created new workout routine.")
                .build()
        );
    }
}

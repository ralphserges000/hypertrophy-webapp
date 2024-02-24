package com.projects.hypertrophyapp.endpoints.workoutroutine;

import org.springframework.web.bind.annotation.RestController;

import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.NewWorkoutRoutineResponse;
import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.WorkoutRoutine;
import com.projects.hypertrophyapp.endpoints.workoutroutine.services.CompletedWorkoutRoutineService;
import com.projects.hypertrophyapp.endpoints.workoutroutine.services.NewWorkoutRoutineService;
import com.projects.hypertrophyapp.endpoints.workoutroutine.stats.WorkoutRoutineCompleteStats;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.WorkoutSetRequest;
import com.projects.hypertrophyapp.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
public class WorkoutRoutineController {

    private final CompletedWorkoutRoutineService completedRoutineService;
    private final NewWorkoutRoutineService newRoutineService;

    @PostMapping("/users/new/workoutroutine")
    public CompletableFuture<NewWorkoutRoutineResponse> createNewWorkoutRoutine(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        WorkoutRoutine newRoutine = WorkoutRoutine.builder()
                                        .routineId(UUID.randomUUID().toString())
                                        .username(userPrincipal.getUsername())
                                        .dateTimeCreated(LocalDateTime.now())
                                        .workoutSets(new LinkedHashSet<>())
                                        .completed(false)
                                        .build();

        return newRoutineService.saveUnfinishedWorkoutRoutine(newRoutine);
    }

    @PostMapping("/users/completed/workoutroutine")
    public CompletableFuture<ResponseEntity<WorkoutRoutineCompleteStats>> completeWorkoutRoutine(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody WorkoutSetRequest completedSets) {
        CompletableFuture<WorkoutRoutineCompleteStats> stats = completedRoutineService.saveFinishedWorkoutRoutine(completedSets);

        return stats.thenApply(result -> {
            if(result.getTotalVolumePerExercise() == null) return new ResponseEntity<WorkoutRoutineCompleteStats>(result,HttpStatus.BAD_REQUEST);
            return new ResponseEntity<WorkoutRoutineCompleteStats>(result,HttpStatus.OK); 
        });
    }
}

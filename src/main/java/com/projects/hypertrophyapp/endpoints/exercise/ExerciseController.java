package com.projects.hypertrophyapp.endpoints.exercise;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.hypertrophyapp.endpoints.exercise.entities.BodyParts;
import com.projects.hypertrophyapp.endpoints.exercise.entities.Exercise;
import com.projects.hypertrophyapp.endpoints.exercise.entities.ExerciseRequest;
import com.projects.hypertrophyapp.endpoints.exercise.entities.ExerciseResponse;
import com.projects.hypertrophyapp.security.UserPrincipal;

@RestController
@RequiredArgsConstructor

public class ExerciseController {

    private final ExerciseService service;

    @GetMapping("/exercises")
    public CompletableFuture<List<Exercise>> getExercisesByBodyPart(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam BodyParts bodypart) {
        return service.getExerciseListByBodyPartsAndUsername(bodypart, userPrincipal.getUsername());
        
    }

    @PostMapping("/users/new/exercise")
    public CompletableFuture<ResponseEntity<ExerciseResponse>> addNewExerciseByUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ExerciseRequest newExercise) {
        return service.addNewExerciseToRepo(newExercise, userPrincipal.getUsername());
    }
}

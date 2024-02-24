package com.projects.hypertrophyapp.endpoints.exercise;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.projects.hypertrophyapp.endpoints.exercise.entities.BodyParts;
import com.projects.hypertrophyapp.endpoints.exercise.entities.Exercise;
import com.projects.hypertrophyapp.endpoints.exercise.entities.ExerciseRequest;
import com.projects.hypertrophyapp.endpoints.exercise.entities.ExerciseResponse;
import com.projects.hypertrophyapp.endpoints.exercise.repositories.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repo;

    @Async
    public CompletableFuture<List<Exercise>> getExerciseListByBodyPartsAndUsername(BodyParts bodypart, String username) {
        Optional<List<Exercise>> exercises = repo.findByBodyPartsAndUsername(bodypart, username);
        return CompletableFuture.completedFuture(
            exercises.orElse(Collections.emptyList())
        );
    }

    @Async
    public CompletableFuture<ResponseEntity<ExerciseResponse>> addNewExerciseToRepo(ExerciseRequest newExercise, String username) {
        Optional<Exercise> exerciseExistence = repo.findByBodyPartsAndUsernameAndExerciseName(newExercise.getBodyPart(),username,newExercise.getName());
        if(exerciseExistence.isPresent()) return CompletableFuture.completedFuture(getEntityAlreadyExistResponse(newExercise,username));
            
        var isExerciseSaved = saveNewExerciseToDB(newExercise,username);
        if(isExerciseSaved.isPresent()) return CompletableFuture.completedFuture(getSuccessSaveEntityResponse(isExerciseSaved.get(), username));

        return CompletableFuture.completedFuture(getDBFailedToSaveEntityResponse(newExercise,username));
    }

    private ResponseEntity<ExerciseResponse> getEntityAlreadyExistResponse(ExerciseRequest newExercise, String username) {
        return new ResponseEntity<>(
            new ExerciseResponse(
                null, newExercise.getName(), 
                newExercise.getBodyPart(), newExercise.getToolUsed(), 
                "already exist"), 
            HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<ExerciseResponse> getSuccessSaveEntityResponse(Exercise savedExercise, String username) {
        return new ResponseEntity<>(
            new ExerciseResponse(
                savedExercise.getId(),savedExercise.getName(), 
                savedExercise.getBodyPart(), savedExercise.getToolUsed(), 
                "new exercise successfully added"), 
            HttpStatus.CREATED);
    }

    private ResponseEntity<ExerciseResponse> getDBFailedToSaveEntityResponse(ExerciseRequest newExercise, String username) {
        return new ResponseEntity<>(
            new ExerciseResponse(
                null, newExercise.getName(), 
                newExercise.getBodyPart(), newExercise.getToolUsed(), 
                "unable to save new exercise. issue has to lodged and support will look into it asap."), 
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Optional<Exercise> saveNewExerciseToDB(ExerciseRequest newExercise, String username) {
        Exercise validNewExercise = Exercise.builder()
                                            .name(newExercise.getName())
                                            .bodyPart(newExercise.getBodyPart())
                                            .toolUsed(newExercise.getToolUsed())
                                            .creatorUsername(username)
                                            .build();

        return Optional.ofNullable(repo.save(validNewExercise));
    }
}

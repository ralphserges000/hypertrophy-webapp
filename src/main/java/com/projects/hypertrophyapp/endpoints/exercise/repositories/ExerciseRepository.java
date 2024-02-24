package com.projects.hypertrophyapp.endpoints.exercise.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.projects.hypertrophyapp.endpoints.exercise.entities.BodyParts;
import com.projects.hypertrophyapp.endpoints.exercise.entities.Exercise;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {

    @Query("{'bodyPart': ?0, 'creatorUsername': { $in: [?1, 'admin'] }}")
    Optional<List<Exercise>> findByBodyPartsAndUsername(BodyParts bodyPart, String inputUsername);

    @Query("{'bodyPart': ?0, 'name': ?2, 'creatorUsername': { $in: [?1, 'admin'] }}")
    Optional<Exercise> findByBodyPartsAndUsernameAndExerciseName(BodyParts bodyParts, String username, String exerciseName);

    Optional<List<Exercise>> findByIdIn(Set<String> exerciseIds);
}

package com.projects.hypertrophyapp.endpoints.workoutroutine.progression;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseVolumeProgressionRepository extends MongoRepository<ExerciseVolumeProgression,String>{

    Optional<ExerciseVolumeProgression> findFirstByUsernameAndExerciseIdOrderByTotalVolumeDesc(String username, String exerciseId);

}

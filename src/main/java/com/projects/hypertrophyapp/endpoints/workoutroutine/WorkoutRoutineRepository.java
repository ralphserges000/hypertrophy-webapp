package com.projects.hypertrophyapp.endpoints.workoutroutine;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.WorkoutRoutine;

@Repository
public interface WorkoutRoutineRepository extends MongoRepository<WorkoutRoutine,String>{

}

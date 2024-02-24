package com.projects.hypertrophyapp.endpoints.workoutroutine.entities;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.WorkoutSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("workoutroutine")
public class WorkoutRoutine {
    @Id private String routineId;
    private String username;
    private LocalDateTime dateTimeCreated;
    private Set<WorkoutSet> workoutSets;
    private boolean completed;

}

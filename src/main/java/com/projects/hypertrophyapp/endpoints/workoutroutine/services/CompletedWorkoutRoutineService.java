package com.projects.hypertrophyapp.endpoints.workoutroutine.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.projects.hypertrophyapp.endpoints.workoutroutine.WorkoutRoutineRepository;
import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.WorkoutRoutine;
import com.projects.hypertrophyapp.endpoints.workoutroutine.progression.ExerciseVolumeProgression;
import com.projects.hypertrophyapp.endpoints.workoutroutine.progression.ExerciseVolumeProgressionRepository;
import com.projects.hypertrophyapp.endpoints.workoutroutine.stats.WorkoutRoutineCompleteStats;
import com.projects.hypertrophyapp.endpoints.workoutroutine.stats.WorkoutVolumePerSet;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.ExerciseVolumePerSet;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.WorkoutSet;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.WorkoutSetRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompletedWorkoutRoutineService {

    private final WorkoutRoutineRepository workoutRoutineRepo;
    private final ExerciseVolumeProgressionRepository volumeProgressionRepo;
    
    @Async
    public CompletableFuture<WorkoutRoutineCompleteStats> saveFinishedWorkoutRoutine(WorkoutSetRequest completedSets) {
        Optional<WorkoutRoutine> routineToBeUpdated = workoutRoutineRepo.findById(completedSets.getRoutineId());
        if(!routineToBeUpdated.isPresent()) 
            return CompletableFuture.completedFuture(
                WorkoutRoutineCompleteStats.builder()
                    .totalExercisesCompleted(0)
                    .totalVolumePerExercise(null)
                    .comments("No workout routine is found").build()
        );

        WorkoutRoutine newlyUpdatedCompleteRoutine = persistCompletedWorkoutRoutine(routineToBeUpdated.get(),completedSets);
        saveAllExercisesVolumeProgression(newlyUpdatedCompleteRoutine);
        
        return CompletableFuture.completedFuture(
            processRoutineForStats(newlyUpdatedCompleteRoutine)
        );
    }

    private WorkoutRoutine persistCompletedWorkoutRoutine(WorkoutRoutine completedRoutine,WorkoutSetRequest completedSets) {
        completedRoutine.setWorkoutSets(completedSets.getAllSetsInARoutine());
        completedRoutine.setCompleted(true);
        return workoutRoutineRepo.save(completedRoutine);
    }

    private List<ExerciseVolumeProgression> saveAllExercisesVolumeProgression(WorkoutRoutine completedRoutine) {
        return volumeProgressionRepo.saveAll(
            completedRoutine.getWorkoutSets()
                .stream()
                .map(workoutSet -> convertWorkoutSetToExerciseVolumeProgression(workoutSet, completedRoutine.getUsername()))
                .toList()
        );
    }

    private ExerciseVolumeProgression convertWorkoutSetToExerciseVolumeProgression(WorkoutSet workoutSet, String username) {
        return ExerciseVolumeProgression.builder()
                                    .dateCompleted(LocalDateTime.now())
                                    .username(username)
                                    .exerciseId(workoutSet.getExercise().getId())
                                    .totalVolume(calculateTotalVolume(workoutSet.getAllExerciseVolumes()))
                                    .allSets(workoutSet.getAllExerciseVolumes())
                                    .build();
    }

    private WorkoutRoutineCompleteStats processRoutineForStats(WorkoutRoutine routine) {
        int totalExerciseCompleted = routine.getWorkoutSets().size();
        Map<String,WorkoutVolumePerSet> totalVolumePerExercise = routine.getWorkoutSets().stream().collect(Collectors.toMap(
            workoutSet -> workoutSet.getExercise().getName(),
            workoutSet -> WorkoutVolumePerSet.builder()
                                .toolUsed(workoutSet.getExercise().getToolUsed())
                                .allSets(workoutSet.getAllExerciseVolumes())
                                .totalVolume(calculateTotalVolume(workoutSet.getAllExerciseVolumes()))
                                .build()
        ));

        return WorkoutRoutineCompleteStats.builder()
                                            .totalExercisesCompleted(totalExerciseCompleted)
                                            .totalVolumePerExercise(totalVolumePerExercise)
                                            .comments("total_volume = sum of each set_volume where each set_volume = total_reps x total_weight ")
                                            .build();
    }

    private double calculateTotalVolume(Set<ExerciseVolumePerSet> exerciseVolumePerSet) {
        return exerciseVolumePerSet.stream().mapToDouble(volume -> volume.getTotalReps() * volume.getWeight()).sum();
    }
}

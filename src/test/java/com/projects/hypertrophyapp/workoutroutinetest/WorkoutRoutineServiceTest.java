package com.projects.hypertrophyapp.workoutroutinetest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projects.hypertrophyapp.endpoints.workoutroutine.WorkoutRoutineRepository;
import com.projects.hypertrophyapp.endpoints.workoutroutine.entities.WorkoutRoutine;
import com.projects.hypertrophyapp.endpoints.workoutroutine.progression.ExerciseVolumeProgressionRepository;
import com.projects.hypertrophyapp.endpoints.workoutroutine.services.CompletedWorkoutRoutineService;
import com.projects.hypertrophyapp.endpoints.workoutroutine.stats.WorkoutRoutineCompleteStats;
import com.projects.hypertrophyapp.endpoints.workoutroutine.workoutset.WorkoutSetRequest;

@ExtendWith(MockitoExtension.class)
public class WorkoutRoutineServiceTest {

    @Mock private WorkoutRoutineRepository workoutRoutineRepo;
    @Mock private ExerciseVolumeProgressionRepository volumeProgressionRepo;
    @InjectMocks private CompletedWorkoutRoutineService svc;

    @Test
    public void test_ifRoutineIdNotFound_thenEmptyWorkoutRoutineCompleteStatsShouldReturn() throws Exception {
        WorkoutSetRequest completedSet = WorkoutSetRequest.builder()
                                            .routineId("xxxx")
                                            .build();
        when(workoutRoutineRepo.findById(eq(completedSet.getRoutineId()))).thenReturn(Optional.empty());

        var result = svc.saveFinishedWorkoutRoutine(completedSet).get();
        assertNull(result.getTotalVolumePerExercise());
    }

    @Test
    public void test_ifRoutineIdIsFound_thenCorrectWorkoutRoutineCompleteStatsShouldReturn() throws Exception {
        var workoutSetSample = SampleProvider.provideWorkoutSetsSamples();
        WorkoutSetRequest completedSet = WorkoutSetRequest.builder()
                                            .routineId("xxxx")
                                            .allSetsInARoutine(workoutSetSample)
                                            .build();
        WorkoutRoutine sampleRoutine = WorkoutRoutine.builder()
                                        .routineId("xxxx")
                                        .username("testuser1")
                                        .dateTimeCreated(LocalDateTime.now())
                                        .workoutSets(new LinkedHashSet<>())
                                        .completed(false)
                                        .build();
        when(workoutRoutineRepo.findById(eq(completedSet.getRoutineId()))).thenReturn(Optional.of(sampleRoutine));
        when(workoutRoutineRepo.save(any(WorkoutRoutine.class))).thenReturn(sampleRoutine);
        when(volumeProgressionRepo.saveAll(any())).thenReturn(null);

        WorkoutRoutineCompleteStats stats = svc.saveFinishedWorkoutRoutine(completedSet).get();
        assertTrue(stats.getTotalExercisesCompleted() == 3);
        assertTrue(stats.getTotalVolumePerExercise().get("push press").getTotalVolume() ==  1392.5);
        assertTrue(sampleRoutine.isCompleted() == true);
    }
}

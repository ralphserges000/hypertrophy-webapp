package com.projects.hypertrophyapp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.hypertrophyapp.endpoints.exercise.ExerciseService;
import com.projects.hypertrophyapp.endpoints.exercise.entities.BodyParts;
import com.projects.hypertrophyapp.endpoints.exercise.entities.Exercise;
import com.projects.hypertrophyapp.endpoints.exercise.entities.ExerciseRequest;
import com.projects.hypertrophyapp.endpoints.exercise.repositories.ExerciseRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseControllerTest {
   
    @MockBean private ExerciseRepository repo;
    @InjectMocks private ExerciseService service;
    @Autowired private MockMvc mockMvc;

    @Test
    void test_getExercisesByBodyPart_unauthorizedAccess() throws Exception {
        mockMvc.perform(get("/exercises")
                .param("bodypart",BodyParts.CHEST.name()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void test_getExercisesByBodyPart() throws Exception {

        List<Exercise> allExercises = new ArrayList<>();
        allExercises.add(Exercise.builder().name("chest press").bodyPart(BodyParts.CHEST).toolUsed("barbell").creatorUsername("testuser").build());
        allExercises.add(Exercise.builder().name("bench press").bodyPart(BodyParts.CHEST).toolUsed("dumbbell").creatorUsername("admin").build());
        allExercises.add(Exercise.builder().name("squat").bodyPart(BodyParts.LEGS).toolUsed("barbell").creatorUsername("admin").build());
        allExercises.add(Exercise.builder().name("chest fly").bodyPart(BodyParts.CHEST).toolUsed("machine").creatorUsername("testuser").build());
       
        Optional<List<Exercise>> chestExerciseOnly = Optional.of(allExercises.stream()
                                .filter(exercise -> exercise.getBodyPart() == BodyParts.CHEST)
                                .toList());
        when(repo.findByBodyPartsAndUsername(eq(BodyParts.CHEST), eq("testuser"))).thenReturn(chestExerciseOnly);

        MvcResult mvcResult = mockMvc.perform(get("/exercises")
                                .param("bodypart",BodyParts.CHEST.name()))
                                .andExpect(request().asyncStarted())
                                .andDo(MockMvcResultHandlers.log())
                                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }


    @Test
    void test_addNewExercise_byUser_unauthorizedAccess() throws Exception {
        mockMvc.perform(get("/users/new/exercise")
                .param("bodypart",BodyParts.CHEST.name()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void test_userAccessing_addNewExerciseUser() throws Exception {
        when(repo.findByBodyPartsAndUsernameAndExerciseName(any(), any(), any())).thenReturn(Optional.empty());
        Exercise newExercise = Exercise.builder()
                    .name("lat pulldown")
                    .bodyPart(BodyParts.BACK)
                    .toolUsed("machine")
                    .creatorUsername("testuser")
                    .build();
        when(repo.save(any(Exercise.class))).thenReturn(newExercise);
        ExerciseRequest req = new ExerciseRequest(newExercise.getName(),newExercise.getBodyPart(), newExercise.getToolUsed());

        MvcResult mvcResult = mockMvc.perform(post("/users/new/exercise")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(req))
                            .param("bodypart",BodyParts.CHEST.name()))
                            .andExpect(request().asyncStarted())
                            .andDo(MockMvcResultHandlers.log())
                            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is(newExercise.getName())));
    }
    
}

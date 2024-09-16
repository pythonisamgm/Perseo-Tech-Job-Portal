package com.example.demo.controllers;

import com.example.demo.dto.experience.ExperienceConverter;
import com.example.demo.dto.experience.ExperienceDTO;
import com.example.demo.models.Experience;
import com.example.demo.models.User;
import com.example.demo.services.ExperienceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExperienceControllerTest {

    @Mock
    private ExperienceServiceImpl experienceService;

    @Mock
    private ExperienceConverter experienceConverter;

    @InjectMocks
    private ExperienceController experienceController;

    private MockMvc mockMvc;

    private ExperienceDTO experienceDTO1;
    private ExperienceDTO experienceDTO2;
    private List<ExperienceDTO> experienceDTOList = new ArrayList<>();
    private List<Experience> experienceList = new ArrayList<>();
    private Experience experience1;
    private Experience experience2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(experienceController).build();

        experienceDTO1 = new ExperienceDTO(1L, "Company1", "Position1",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                "Description1", 1L);

        experienceDTO2 = new ExperienceDTO(2L, "Company2", "Position2",
                LocalDateTime.of(2021, 2, 1, 0, 0),
                LocalDateTime.of(2022, 2, 1, 0, 0),
                "Description2", 2L);

        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);

        experience1 = new Experience(1L, "Company1", "Position1",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                "Description1", user1);

        experience2 = new Experience(2L, "Company2", "Position2",
                LocalDateTime.of(2021, 2, 1, 0, 0),
                LocalDateTime.of(2022, 2, 1, 0, 0),
                "Description2", user2);

        experienceDTOList = List.of(experienceDTO1, experienceDTO2);
        experienceList = List.of(experience1, experience2);


        when(experienceConverter.experienceToDto(any(Experience.class))).thenReturn(experienceDTO1);
        when(experienceConverter.dtoToExperience(any(ExperienceDTO.class))).thenReturn(experience1);
        when(experienceService.getAllExperiences()).thenReturn(experienceList);
        when(experienceService.getExperienceById(anyLong())).thenReturn(Optional.of(experience1));
        when(experienceService.createExperience(any(Experience.class))).thenReturn(experience1);
        when(experienceService.updateExperience(any(Experience.class))).thenReturn(experience1);
        doNothing().when(experienceService).deleteExperienceById(anyLong());
        doNothing().when(experienceService).deleteAllExperiences();
    }

    @Test
    void createExperience() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String experienceJson = "{"
                + "\"id\": 1,"
                + "\"companyName\": \"Company1\","
                + "\"position\": \"Position1\","
                + "\"startDate\": \"2020-01-01T00:00:00\","
                + "\"endDate\": \"2021-01-01T00:00:00\","
                + "\"description\": \"Description1\","
                + "\"userId\": 1"
                + "}";

        String expectedResponseBody = objectMapper.writeValueAsString(experienceDTO1);

        mockMvc.perform(post("/api/v1/experiences/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experienceJson))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }


    @Test
    void getAllExperiences() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String expectedResponseBody = objectMapper.writeValueAsString(experienceDTOList);

        mockMvc.perform(get("/api/v1/experiences/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }

    @Test
    void getExperienceById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String expectedResponseBody = objectMapper.writeValueAsString(experienceDTO1);

        mockMvc.perform(get("/api/v1/experiences/experience/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }

    @Test
    void updateExperience() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Crear el JSON actualizado como una cadena
        String updatedExperienceJson = "{"
                + "\"id\": 1,"
                + "\"companyName\": \"Company1\","
                + "\"position\": \"Position1\","
                + "\"startDate\": \"2020-01-01T00:00:00\","
                + "\"endDate\": \"2021-01-01T00:00:00\","
                + "\"description\": \"Description1\","
                + "\"userId\": 1"
                + "}";

        String expectedResponseBody = objectMapper.writeValueAsString(experienceDTO1);

        when(experienceConverter.dtoToExperience(any(ExperienceDTO.class))).thenReturn(experience1);
        when(experienceService.updateExperience(any(Experience.class))).thenReturn(experience1);

        mockMvc.perform(put("/api/v1/experiences/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedExperienceJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    // Comparar el cuerpo de la respuesta con el esperado usando JSONAssert
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }


    @Test
    void deleteExperienceById() throws Exception {
        mockMvc.perform(delete("/api/v1/experiences/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllExperiences() throws Exception {
        mockMvc.perform(delete("/api/v1/experiences/delete/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}

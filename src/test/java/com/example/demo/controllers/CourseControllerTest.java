package com.example.demo.controllers;

import com.example.demo.dto.course.CourseConverter;
import com.example.demo.dto.course.CourseDTO;
import com.example.demo.models.Course;
import com.example.demo.services.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseControllerTest {

    @Mock
    private CourseServiceImpl courseService;

    @Mock
    private CourseConverter courseConverter;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    private CourseDTO courseDTO1;
    private CourseDTO courseDTO2;
    private List<CourseDTO> courseDTOList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();

        courseDTO1 = new CourseDTO(1L, "Title1", "Description1", 100.0, "2020-01-01T00:00:00", List.of(1L));
        courseDTO2 = new CourseDTO(2L, "Title2", "Description2", 200.0, "2021-02-01T00:00:00", List.of(2L));

        Course course1 = new Course(1L, "Title1", "Description1", 100.0, null, null);
        Course course2 = new Course(2L, "Title2", "Description2", 200.0, null, null);

        courseDTOList = List.of(courseDTO1, courseDTO2);
        courseList = List.of(course1, course2);

        when(courseConverter.courseToDto(any(Course.class))).thenReturn(courseDTO1).thenReturn(courseDTO2);
        when(courseConverter.dtoToCourse(any(CourseDTO.class))).thenReturn(course1);
        when(courseService.getAllCourses()).thenReturn(courseList);
        when(courseService.getCourseById(anyLong())).thenReturn(Optional.of(course1));
        when(courseService.createCourse(any(Course.class))).thenReturn(course1);
        when(courseService.updateCourse(any(Course.class))).thenReturn(course1);
        doNothing().when(courseService).deleteCourseById(anyLong());
        doNothing().when(courseService).deleteAllCourses();
    }

    @Test
    void createCourse() throws Exception {
        String courseJson = "{"
                + "\"id\": 1,"
                + "\"title\": \"Title1\","
                + "\"description\": \"Description1\","
                + "\"price\": 100.0,"
                + "\"createdAt\": \"2020-01-01T00:00:00\","
                + "\"usersId\": [1],"
                + "\"shoppingCartId\": 1"
                + "}";

        mockMvc.perform(post("/api/v1/courses/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(courseJson));
    }

    @Test
    void getAllCourses() throws Exception {

        String expectedResponseBody = new ObjectMapper().writeValueAsString(courseDTOList);


        mockMvc.perform(get("/api/v1/courses/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }


    @Test
    void getCourseById() throws Exception {
        mockMvc.perform(get("/api/v1/courses/course/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(courseDTO1), responseBody, false);
                });
    }

    @Test
    void updateCourse() throws Exception {
        String updatedCourseJson = "{"
                + "\"id\": 1,"
                + "\"title\": \"Title1\","
                + "\"description\": \"Description1\","
                + "\"price\": 100.0,"
                + "\"createdAt\": \"2020-01-01T00:00:00\","
                + "\"usersId\": [1],"
                + "\"shoppingCartId\": 1"
                + "}";

        when(courseConverter.dtoToCourse(any(CourseDTO.class))).thenReturn(course1);

        mockMvc.perform(put("/api/v1/courses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCourseJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    try {
                        JSONAssert.assertEquals(updatedCourseJson, responseBody, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void deleteCourseById() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllCourses() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/delete/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

package com.example.demo.controllers;

import com.example.demo.dto.user.UserConverter;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.models.ERole;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import com.example.demo.services.UserServiceImpl;
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

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private UserDTO userDTO1;
    private UserDTO userDTO2;
    private List<UserDTO> userDTOList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDTO1 = new UserDTO(1L, "user1", "user1@example.com", "password1", ERole.USER, "github", "null", List.of(1L), List.of(1L), 1L);
        userDTO2 = new UserDTO(2L, "user2", "user2@example.com", "password2", ERole.ADMIN, "github", "null", List.of(1L), List.of(1L), 1L);
        user1 = new User(1L, "user1", "user1@example.com", "password1", ERole.USER, "github", "token123", new ArrayList<>(), new ArrayList<>(), new ShoppingCart());
        user2 = new User(2L, "user2", "user2@example.com", "password2", ERole.ADMIN, "github", "token123", new ArrayList<>(), new ArrayList<>(), new ShoppingCart());
        userDTOList = List.of(userDTO1, userDTO2);
        userList = List.of(user1, user2);

        when(userConverter.dtoToUser(any(UserDTO.class))).thenReturn(user1);
        when(userConverter.userToDto(any(User.class))).thenReturn(userDTO1);
        when(userService.getAllUsers()).thenReturn(userList);
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user1));
        when(userService.createUser(any(User.class))).thenReturn(user1);
        when(userService.updateUser(any(User.class))).thenReturn(user1);
        doNothing().when(userService).deleteUserById(anyLong());
        doNothing().when(userService).deleteAllUsers();
    }

    @Test
    void createUser() throws Exception {
        String userJson = "{"
                + "\"userId\": 1,"
                + "\"username\": \"user1\","
                + "\"email\": \"user1@example.com\","
                + "\"password\": \"password1\","
                + "\"role\": \"USER\""
                + "}";

        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(userJson));
    }

    @Test
    void getAllUsers() throws Exception {
        String expectedResponseBody = new ObjectMapper().writeValueAsString(userDTOList);

        mockMvc.perform(get("/api/v1/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(userDTO1), responseBody, false);
                });
    }

    @Test
    void updateUser() throws Exception {
        String updatedUserJson = "{"
                + "\"userId\": 1,"
                + "\"username\": \"user1\","
                + "\"email\": \"user1@example.com\","
                + "\"password\": \"password1\","
                + "\"role\": \"USER\""
                + "}";

        when(userConverter.dtoToUser(any(UserDTO.class))).thenReturn(user1);

        mockMvc.perform(put("/api/v1/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    try {
                        JSONAssert.assertEquals(updatedUserJson, responseBody, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
    }
}
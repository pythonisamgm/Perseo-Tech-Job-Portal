package com.example.demo.controllers;

import com.example.demo.dto.shoppingCart.ShoppingCartConverter;
import com.example.demo.dto.shoppingCart.ShoppingCartDTO;
import com.example.demo.models.Course;
import com.example.demo.models.ERole;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import com.example.demo.services.ShoppingCartServiceImpl;
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

class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartConverter shoppingCartConverter;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    private MockMvc mockMvc;

    private ShoppingCartDTO shoppingCartDTO1;
    private ShoppingCartDTO shoppingCartDTO2;
    private List<ShoppingCartDTO> shoppingCartDTOList = new ArrayList<>();
    private List<ShoppingCart> shoppingCartList = new ArrayList<>();
    private ShoppingCart shoppingCart1;
    private ShoppingCart shoppingCart2;
    private User user;
    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();

        // Initialize test data
        user = new User(1L, "user1", "user1@example.com", "password1", ERole.USER, "github", "token123", new ArrayList<>(), new ArrayList<>(), new ShoppingCart());
        course = new Course(1L, "Course 1", "Description", 100.00, "null", new ArrayList<>(), shoppingCart1);

        shoppingCart1 = new ShoppingCart(1L, user, List.of(course));
        shoppingCart2 = new ShoppingCart(2L, user, List.of(course));

        shoppingCartDTO1 = new ShoppingCartDTO(1L, 1L, List.of(1L));
        shoppingCartDTO2 = new ShoppingCartDTO(2L, 1L, List.of(1L));

        shoppingCartDTOList = List.of(shoppingCartDTO1, shoppingCartDTO2);
        shoppingCartList = List.of(shoppingCart1, shoppingCart2);

        when(shoppingCartConverter.dtoToShoppingCart(any(ShoppingCartDTO.class))).thenReturn(shoppingCart1);
        when(shoppingCartConverter.shoppingCartToDto(any(ShoppingCart.class))).thenReturn(shoppingCartDTO1);
        when(shoppingCartService.getAllShoppingCarts()).thenReturn(shoppingCartList);
        when(shoppingCartService.getShoppingCartById(anyLong())).thenReturn(Optional.of(shoppingCart1));
        when(shoppingCartService.createShoppingCart(any(ShoppingCart.class))).thenReturn(shoppingCart1);
        when(shoppingCartService.updateShoppingCart(any(ShoppingCart.class))).thenReturn(shoppingCart1);
        doNothing().when(shoppingCartService).deleteShoppingCartById(anyLong());
        doNothing().when(shoppingCartService).deleteAllShoppingCarts();
        when(shoppingCartService.addCourseToCart(anyLong(), anyLong())).thenReturn(shoppingCart1);
        when(shoppingCartService.removeCourseFromCart(anyLong(), anyLong())).thenReturn(shoppingCart1);
    }

    @Test
    void createShoppingCart() throws Exception {
        String shoppingCartJson = "{"
                + "\"id\": 1,"
                + "\"userId\": 1,"
                + "\"courseIds\": [1]"
                + "}";

        when(shoppingCartConverter.shoppingCartToDto(any(ShoppingCart.class))).thenReturn(shoppingCartDTO1);

        mockMvc.perform(post("/api/v1/shoppingCart/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shoppingCartJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(shoppingCartJson));
    }

    @Test
    void getAllShoppingCarts() throws Exception {
        String expectedResponseBody = new ObjectMapper().writeValueAsString(shoppingCartDTOList);

        mockMvc.perform(get("/api/v1/shoppingCart/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
                });
    }

    @Test
    void getShoppingCartById() throws Exception {
        mockMvc.perform(get("/api/v1/shoppingCart/cart/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(shoppingCartDTO1), responseBody, false);
                });
    }

    @Test
    void updateShoppingCart() throws Exception {
        String updatedCartJson = "{"
                + "\"id\": 1,"
                + "\"userId\": 1,"
                + "\"courseIds\": [1]"
                + "}";

        when(shoppingCartConverter.shoppingCartToDto(any(ShoppingCart.class))).thenReturn(shoppingCartDTO1);

        mockMvc.perform(put("/api/v1/shoppingCart/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCartJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    try {
                        JSONAssert.assertEquals(updatedCartJson, responseBody, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void deleteShoppingCartById() throws Exception {
        mockMvc.perform(delete("/api/v1/shoppingCart/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllShoppingCarts() throws Exception {
        mockMvc.perform(delete("/api/v1/shoppingCart/delete/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addCourseToCart() throws Exception {
        String updatedCartJson = "{"
                + "\"id\": 1,"
                + "\"userId\": 1,"
                + "\"courseIds\": [1]"
                + "}";

        when(shoppingCartConverter.shoppingCartToDto(any(ShoppingCart.class))).thenReturn(shoppingCartDTO1);

        mockMvc.perform(put("/api/v1/shoppingCart/1/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(updatedCartJson, responseBody, false);
                });
    }

    @Test
    void removeCourseFromCart() throws Exception {
        String updatedCartJson = "{"
                + "\"id\": 1,"
                + "\"userId\": 1,"
                + "\"courseIds\": [1]"
                + "}";

        when(shoppingCartConverter.shoppingCartToDto(any(ShoppingCart.class))).thenReturn(shoppingCartDTO1);

        mockMvc.perform(delete("/api/v1/shoppingCart/1/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println("Response Body: " + responseBody);
                    JSONAssert.assertEquals(updatedCartJson, responseBody, false);
                });
    }
}

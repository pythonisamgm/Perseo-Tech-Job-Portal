package com.example.demo.dto.user;

import com.example.demo.models.Course;
import com.example.demo.models.ERole;
import com.example.demo.models.Experience;
import com.example.demo.models.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;  // ENUM para USER, ADMIN
    private String oauthProvider;  // Proveedor de OAuth (GitHub, LinkedIn)
    private String oauthToken;  // Token de OAuth si es un login con GitHub
    private List<Experience> experiences;
    private List<Course> courseList;
    private ShoppingCart shoppingCart;
}

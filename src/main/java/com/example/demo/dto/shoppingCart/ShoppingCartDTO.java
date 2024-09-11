package com.example.demo.dto.shoppingCart;

import com.example.demo.models.Course;
import com.example.demo.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCartDTO {
    private Long id;
    private User user;
    private List<Course> courses;

}

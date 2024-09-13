package com.example.demo.dto.course;

import com.example.demo.dto.shoppingCart.ShoppingCartDTO;
import com.example.demo.dto.user.UserDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CourseDTO {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private String createdAt;
    private List<UserDTO> usersList;
    private ShoppingCartDTO shoppingCart;

}




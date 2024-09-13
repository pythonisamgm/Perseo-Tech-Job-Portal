package com.example.demo.dto.shoppingCart;

import com.example.demo.dto.course.CourseDTO;
import com.example.demo.dto.user.UserDTO;
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
    private UserDTO user;
    private List<CourseDTO> courses;

}

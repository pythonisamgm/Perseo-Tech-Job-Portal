package com.example.demo.dto.shoppingCart;

import com.example.demo.models.Course;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import com.example.demo.services.CourseServiceImpl;
import com.example.demo.services.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingCartConverter {

    private final ModelMapper modelMapper;
    private final CourseServiceImpl courseService;
    private final UserServiceImpl userService;

    public ShoppingCartConverter(ModelMapper modelMapper, CourseServiceImpl courseService, UserServiceImpl userService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
        this.userService = userService;
    }

    public ShoppingCartDTO shoppingCartToDto(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            return null;
        }

        ShoppingCartDTO shoppingCartDTO = modelMapper.map(shoppingCart, ShoppingCartDTO.class);
        List<Long> courseIds = shoppingCart.getCourses() != null ?
                shoppingCart.getCourses().stream().map(Course::getId).collect(Collectors.toList()) :
                null;
        shoppingCartDTO.setCourseIds(courseIds);
        if (shoppingCart.getUser() != null) {
            shoppingCartDTO.setUserId(shoppingCart.getUser().getUserId());
        }

        return shoppingCartDTO;
    }

    public ShoppingCart dtoToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        if (shoppingCartDTO == null) {
            return null;
        }

        ShoppingCart shoppingCart = modelMapper.map(shoppingCartDTO, ShoppingCart.class);
        List<Course> courses = shoppingCartDTO.getCourseIds() != null ?
                shoppingCartDTO.getCourseIds().stream()
                        .map(id -> courseService.getCourseById(id).orElse(null))
                        .filter(course -> course != null)
                        .collect(Collectors.toList()) :
                null;
        shoppingCart.setCourses(courses);

        if (shoppingCartDTO.getUserId() != null) {
            User user = userService.getUserById(shoppingCartDTO.getUserId()).orElse(null);
            shoppingCart.setUser(user);
        }

        return shoppingCart;
    }
}

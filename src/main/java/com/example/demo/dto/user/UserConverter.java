package com.example.demo.dto.user;

import com.example.demo.models.Course;
import com.example.demo.models.Experience;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import com.example.demo.services.interfaces.CourseService;
import com.example.demo.services.interfaces.ExperienceService;
import com.example.demo.services.interfaces.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final ExperienceService experienceService;
    private final ShoppingCartService shoppingCartService;

    public UserConverter(ModelMapper modelMapper, CourseService courseService, ExperienceService experienceService, ShoppingCartService shoppingCartService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
        this.experienceService = experienceService;
        this.shoppingCartService = shoppingCartService;
    }

    public UserDTO userToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.setExperienceIds(user.getExperiences() != null ? user.getExperiences().stream().map(Experience::getId).toList() : null);
        userDTO.setCourseIds(user.getCourseList() != null ? user.getCourseList().stream().map(Course::getId).toList() : null);
        userDTO.setShoppingCartId(user.getShoppingCart() != null ? user.getShoppingCart().getId() : null);

        return userDTO;
    }

    public User dtoToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        if (userDTO.getShoppingCartId() != null) {
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartService.getShoppingCartById(userDTO.getShoppingCartId());
            optionalShoppingCart.ifPresent(user::setShoppingCart);
        }

        if (userDTO.getExperienceIds() != null) {
            user.setExperiences(userDTO.getExperienceIds().stream().map(id -> experienceService.getExperienceById(id).orElse(null)).filter(exp -> exp != null).toList());
        }

        if (userDTO.getCourseIds() != null) {
            user.setCourseList(userDTO.getCourseIds().stream().map(id -> courseService.getCourseById(id).orElse(null)).filter(course -> course != null).toList());
        }

        return user;
    }
}

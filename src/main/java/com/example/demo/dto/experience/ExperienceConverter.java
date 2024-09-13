package com.example.demo.dto.experience;

import com.example.demo.models.Experience;
import com.example.demo.models.User;
import com.example.demo.services.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExperienceConverter {

    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;

    @Autowired
    public ExperienceConverter(ModelMapper modelMapper, UserServiceImpl userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public ExperienceDTO experienceToDto(Experience experience) {
        if (experience == null) {
            return null;
        }

        ExperienceDTO experienceDTO = modelMapper.map(experience, ExperienceDTO.class);
        if (experience.getUser() != null) {
            experienceDTO.setUserId(experience.getUser().getUserId());
        }

        return experienceDTO;
    }

    public Experience dtoToExperience(ExperienceDTO experienceDTO) {
        if (experienceDTO == null) {
            return null;
        }

        Experience experience = modelMapper.map(experienceDTO, Experience.class);
        if (experienceDTO.getUserId() != null) {
            Optional<User> user = userService.getUserById(experienceDTO.getUserId());
            user.ifPresent(experience::setUser);
        }

        return experience;
    }
}

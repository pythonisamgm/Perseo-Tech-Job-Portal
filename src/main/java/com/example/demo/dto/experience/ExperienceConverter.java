package com.example.demo.dto.experience;

import com.example.demo.models.Experience;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ExperienceConverter {
    @Autowired
    ModelMapper modelMapper;

    public ExperienceDTO experienceToDto (Experience experience){
        return modelMapper.map (experience, ExperienceDTO.class);

    }

    public Experience dtoToExperience (ExperienceDTO experienceDTO){
        return modelMapper.map(experienceDTO, Experience.class);
    }
}

package com.example.demo.dto.course;

import com.example.demo.models.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseConverter {
    @Autowired
    ModelMapper modelMapper;

    public CourseDTO courseToDto (Course course){
        return modelMapper.map (course, CourseDTO.class);
    }
    public Course dtoToCourse (CourseDTO courseDTO){
        return modelMapper.map(courseDTO, Course.class);
    }
}

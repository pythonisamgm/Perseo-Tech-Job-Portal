package com.example.demo.dto.course;

import com.example.demo.models.Course;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CourseConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Course, CourseDTO>() {
            @Override
            protected void configure() {
                map().setCreatedAt(source.getCreatedAt());
            }
        });

        modelMapper.addMappings(new PropertyMap<CourseDTO, Course>() {
            @Override
            protected void configure() {
                map(source.getCreatedAt(), destination.getCreatedAt());
            }
        });
    }

    public CourseDTO courseToDto(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    public Course dtoToCourse(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }
}

package com.example.demo.services.interfaces;

import com.example.demo.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    public Course createCourse(Course course);

    public List<Course> getAllCourses();

    public Optional<Course> getCourseById(Long id);

    public Course updateCourse(Course updatedCourse);

    public void deleteCourseById(Long id);

    public void deleteAllCourses();
}

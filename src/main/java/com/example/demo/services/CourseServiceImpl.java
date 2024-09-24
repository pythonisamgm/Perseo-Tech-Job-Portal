package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.repositories.ICourseRepository;
import com.example.demo.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all courses", e);
        }
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        try {
            return courseRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving course by id", e);
        }
    }

    @Override
    public Course updateCourse(Course updatedCourse) {
        Optional<Course> existingCourse = courseRepository.findById(updatedCourse.getId());
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            course.setPrice(updatedCourse.getPrice());
            course.setCreatedAt(updatedCourse.getCreatedAt());
            course.setUsersList(updatedCourse.getUsersList());
            return courseRepository.save(course);
        }
        return null;
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }
}

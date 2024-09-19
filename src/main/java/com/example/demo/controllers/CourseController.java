package com.example.demo.controllers;

import com.example.demo.dto.course.CourseConverter;
import com.example.demo.dto.course.CourseDTO;
import com.example.demo.models.Course;
import com.example.demo.services.CourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CourseController {

    @Autowired
    private final CourseServiceImpl courseService;

    @Autowired
    private final CourseConverter courseConverter;


    @PostMapping("/create")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseConverter.dtoToCourse(courseDTO);
        Course createdCourse = courseService.createCourse(course);
        CourseDTO createdCourseDTO = courseConverter.courseToDto(createdCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourseDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDTO> courseDTOs = courses.stream()
                .map(courseConverter::courseToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDTOs);
    }


    @GetMapping("/course/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isPresent()) {
            CourseDTO courseDTO = courseConverter.courseToDto(courseOpt.get());
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        Course course = courseConverter.dtoToCourse(courseDTO);

        if (course == null) {
            return ResponseEntity.badRequest().body(null); // Or handle this case in a way that fits your needs
        }
        course.setId(id);
        Course updatedCourse = courseService.updateCourse(course);

        if (updatedCourse != null) {
            CourseDTO updatedCourseDTO = courseConverter.courseToDto(updatedCourse);
            return ResponseEntity.ok(updatedCourseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllCourses() {
        courseService.deleteAllCourses();
        return ResponseEntity.noContent().build();
    }
}


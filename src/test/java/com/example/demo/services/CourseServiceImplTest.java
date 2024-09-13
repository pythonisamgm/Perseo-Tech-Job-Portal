package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.models.ShoppingCart;
import com.example.demo.repositories.ICourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course1;
    private Course course2;
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicialización de datos de prueba
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Curso de Java");
        course1.setDescription("Aprende Java desde cero");
        course1.setPrice(100.0);
        course1.setCreatedAt("2023-01-01");
        course1.setShoppingCart(shoppingCart);

        course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Curso de Spring");
        course2.setDescription("Aprende Spring Boot");
        course2.setPrice(120.0);
        course2.setCreatedAt("2023-02-01");
        course2.setShoppingCart(shoppingCart);
    }

    @Test
    void testCreateCourse() {
        when(courseRepository.save(course1)).thenReturn(course1);

        Course result = courseService.createCourse(course1);

        assertNotNull(result);
        assertEquals(course1.getId(), result.getId());
        assertEquals(course1.getTitle(), result.getTitle());
        verify(courseRepository, times(1)).save(course1);
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));

        Optional<Course> result = courseService.getCourseById(1L);

        assertTrue(result.isPresent());
        assertEquals(course1.getId(), result.get().getId());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCourse() {
        Course updatedCourse = new Course();
        updatedCourse.setId(1L);
        updatedCourse.setTitle("Curso de Java Avanzado");
        updatedCourse.setDescription("Aprende Java avanzado");
        updatedCourse.setPrice(150.0);
        updatedCourse.setCreatedAt("2023-03-01");
        updatedCourse.setShoppingCart(shoppingCart);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));
        when(courseRepository.save(course1)).thenReturn(updatedCourse);

        Course result = courseService.updateCourse(updatedCourse);

        assertNotNull(result);
        assertEquals("Curso de Java Avanzado", result.getTitle());
        assertEquals(150.0, result.getPrice());
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(course1);
    }

    @Test
    void testDeleteCourseById() {
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourseById(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAllCourses() {
        doNothing().when(courseRepository).deleteAll();

        courseService.deleteAllCourses();

        verify(courseRepository, times(1)).deleteAll();
    }
}

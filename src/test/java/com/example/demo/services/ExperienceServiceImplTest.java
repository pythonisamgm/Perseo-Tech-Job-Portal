package com.example.demo.services;

import com.example.demo.models.Experience;
import com.example.demo.models.User;
import com.example.demo.repositories.IExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExperienceServiceImplTest {

    @Mock
    private IExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    private Experience experience1;
    private Experience experience2;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup initial test data
        user = new User();
        user.setUserId(1L);
        user.setUsername("john_doe");

        experience1 = new Experience();
        experience1.setId(1L);
        experience1.setCompanyName("Company A");
        experience1.setPosition("Developer");
        experience1.setStartDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        experience1.setEndDate(LocalDateTime.of(2021, 1, 1, 0, 0));
        experience1.setDescription("Worked on various projects.");
        experience1.setUser(user);

        experience2 = new Experience();
        experience2.setId(2L);
        experience2.setCompanyName("Company B");
        experience2.setPosition("Senior Developer");
        experience2.setStartDate(LocalDateTime.of(2021, 2, 1, 0, 0));
        experience2.setEndDate(LocalDateTime.of(2022, 2, 1, 0, 0));
        experience2.setDescription("Led development teams.");
        experience2.setUser(user);
    }

    @Test
    void testCreateExperience() {
        when(experienceRepository.save(experience1)).thenReturn(experience1);

        Experience result = experienceService.createExperience(experience1);

        assertEquals(experience1.getId(), result.getId());
        assertEquals(experience1.getCompanyName(), result.getCompanyName());
        verify(experienceRepository, times(1)).save(experience1);
    }

    @Test
    void testGetAllExperiences() {
        List<Experience> experiences = Arrays.asList(experience1, experience2);

        when(experienceRepository.findAll()).thenReturn(experiences);

        List<Experience> result = experienceService.getAllExperiences();

        assertEquals(2, result.size());
        verify(experienceRepository, times(1)).findAll();
    }

    @Test
    void testGetExperienceById() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience1));

        Optional<Experience> result = experienceService.getExperienceById(1L);

        assertTrue(result.isPresent());
        assertEquals(experience1.getId(), result.get().getId());
        verify(experienceRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateExperience() {
        Experience updatedExperience = new Experience();
        updatedExperience.setId(1L);
        updatedExperience.setCompanyName("Updated Company");
        updatedExperience.setPosition("Updated Position");
        updatedExperience.setStartDate(LocalDateTime.of(2022, 1, 1, 0, 0));
        updatedExperience.setEndDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        updatedExperience.setDescription("Updated description");
        updatedExperience.setUser(user);

        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience1));
        when(experienceRepository.save(experience1)).thenReturn(updatedExperience);

        Experience result = experienceService.updateExperience(updatedExperience);

        assertNotNull(result);
        assertEquals("Updated Company", result.getCompanyName());
        assertEquals("Updated Position", result.getPosition());
        assertEquals("Updated description", result.getDescription());
        verify(experienceRepository, times(1)).findById(1L);
        verify(experienceRepository, times(1)).save(experience1);
    }

    @Test
    void testDeleteExperienceById() {
        doNothing().when(experienceRepository).deleteById(1L);

        experienceService.deleteExperienceById(1L);

        verify(experienceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAllExperiences() {
        doNothing().when(experienceRepository).deleteAll();

        experienceService.deleteAllExperiences();

        verify(experienceRepository, times(1)).deleteAll();
    }
}

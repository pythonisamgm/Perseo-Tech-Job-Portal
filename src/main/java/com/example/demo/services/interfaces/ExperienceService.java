package com.example.demo.services.interfaces;

import com.example.demo.models.Experience;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    public Experience createExperience(Experience experience);

    public List<Experience> getAllExperiences();

    public Optional<Experience> getExperienceById(Long id);

    public Experience updateExperience(Experience updatedExperience);

    public void deleteExperienceById(Long id);

    public void deleteAllExperiences();
}

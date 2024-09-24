package com.example.demo.services;

import com.example.demo.models.Experience;
import com.example.demo.models.User;
import com.example.demo.repositories.IExperienceRepository;
import com.example.demo.services.interfaces.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private IExperienceRepository experienceRepository;

    @Override
    public Experience createExperience(Experience experience) {
        return experienceRepository.save(experience);
    }
    @Override
    public List<Experience> getAllExperiences() {
        try {
            return experienceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all experiences", e);
        }
    }
    @Override
    public Optional<Experience> getExperienceById(Long id) {
        try {
            return experienceRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving experience by id", e);
        }
    }
    @Override
    public Experience updateExperience(Experience updatedExperience) {
        Optional<Experience> existingExperience = experienceRepository.findById(updatedExperience.getId());
        if (existingExperience.isPresent()) {
            Experience experience = existingExperience.get();
            experience.setCompanyName(updatedExperience.getCompanyName());
            experience.setPosition(updatedExperience.getPosition());
            experience.setStartDate(updatedExperience.getStartDate());
            experience.setEndDate(updatedExperience.getEndDate());
            experience.setDescription(updatedExperience.getDescription());
            experience.setUser(updatedExperience.getUser());
            return experienceRepository.save(experience);
        }
        return null;
    }
    @Override
    public void deleteExperienceById(Long id) {
        experienceRepository.deleteById(id);
    }
    @Override
    public void deleteAllExperiences() {
        experienceRepository.deleteAll();
    }
    @Override
    public List<Experience> getExperiencesByUser(User user) {
        try {
            return experienceRepository.findByUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving experiences by user", e);
        }
    }
}


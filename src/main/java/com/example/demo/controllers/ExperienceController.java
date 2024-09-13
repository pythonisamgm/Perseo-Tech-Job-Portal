package com.example.demo.controllers;

import com.example.demo.dto.experience.ExperienceConverter;
import com.example.demo.dto.experience.ExperienceDTO;
import com.example.demo.models.Experience;
import com.example.demo.services.ExperienceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/experiences")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExperienceController {

    @Autowired
    private final ExperienceServiceImpl experienceService;

    @Autowired
    private final ExperienceConverter experienceConverter;

    @PostMapping("/create")
    public ResponseEntity<ExperienceDTO> createExperience(@RequestBody ExperienceDTO experienceDTO) {
        Experience experience = experienceConverter.dtoToExperience(experienceDTO);
        Experience createdExperience = experienceService.createExperience(experience);
        ExperienceDTO createdExperienceDTO = experienceConverter.experienceToDto(createdExperience);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExperienceDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExperienceDTO>> getAllExperiences() {
        List<Experience> experiences = experienceService.getAllExperiences();
        List<ExperienceDTO> experienceDTOs = experiences.stream()
                .map(experienceConverter::experienceToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(experienceDTOs);
    }

    @GetMapping("/experience/{id}")
    public ResponseEntity<ExperienceDTO> getExperienceById(@PathVariable Long id) {
        Optional<Experience> experienceOpt = experienceService.getExperienceById(id);
        if (experienceOpt.isPresent()) {
            ExperienceDTO experienceDTO = experienceConverter.experienceToDto(experienceOpt.get());
            return ResponseEntity.ok(experienceDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExperienceDTO> updateExperience(@PathVariable Long id, @RequestBody ExperienceDTO experienceDTO) {
        Experience experience = experienceConverter.dtoToExperience(experienceDTO);
        experience.setId(id); // Asegurarse de que el ID sea el correcto
        Experience updatedExperience = experienceService.updateExperience(experience);
        if (updatedExperience != null) {
            ExperienceDTO updatedExperienceDTO = experienceConverter.experienceToDto(updatedExperience);
            return ResponseEntity.ok(updatedExperienceDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllExperiences() {
        experienceService.deleteAllExperiences();
        return ResponseEntity.noContent().build();
    }
}

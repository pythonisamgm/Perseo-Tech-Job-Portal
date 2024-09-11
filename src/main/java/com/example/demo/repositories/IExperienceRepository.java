package com.example.demo.repositories;

import com.example.demo.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExperienceRepository extends JpaRepository<Experience, Long> {
}

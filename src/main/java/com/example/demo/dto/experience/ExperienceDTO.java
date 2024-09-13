package com.example.demo.dto.experience;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ExperienceDTO {

    private Long id;
    private String companyName;
    private String position;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private Long userId;

}



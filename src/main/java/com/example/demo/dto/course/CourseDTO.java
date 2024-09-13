package com.example.demo.dto.course;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CourseDTO {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private String createdAt;
    private List<Long> usersId;
    private Long shoppingCartId;

}




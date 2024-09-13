package com.example.demo.dto.payment;

import com.example.demo.dto.course.CourseDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.models.Course;
import com.example.demo.models.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {


    private Long id;
    private Course course;
    private Double totalAmount;
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String method;
    private UserDTO user;
    private CourseDTO onlineCourse;


}

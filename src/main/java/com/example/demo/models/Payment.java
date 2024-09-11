package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    //de este attribute lo mismo se puede prescindir pq está linkeado por id
    @Column
    private Course course;

    @Column
    private Double totalAmount;

    @Column
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column
    private PaymentStatus paymentStatus;

    @Column
    private String method;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course onlineCourse;


}


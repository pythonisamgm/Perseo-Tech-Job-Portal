package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private String createdAt;

    @ManyToMany(mappedBy = "courseList")
    @JsonBackReference
    private List<User> usersList;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @JsonManagedReference
    private ShoppingCart shoppingCart;

}


package com.sparta.curtain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="category_name", nullable = false)
    private String category_name;
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
//    private List<Post> Posts = new ArrayList<>();


}

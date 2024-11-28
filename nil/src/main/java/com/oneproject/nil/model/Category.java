package com.oneproject.nil.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; // Category name
}

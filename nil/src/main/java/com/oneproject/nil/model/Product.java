package com.oneproject.nil.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Change to Long if you're using Long for id fields

    private String name;
    private String description;
    private String brand;
    private BigDecimal price;

    @ManyToOne
    private Category category;  // Keep this as is to maintain the relationship with Category entity

    private int stockQuantity;

    public Integer getCategoryId() {
        // Return the ID of the associated Category, or null if the category is not set
        return category != null ? category.getId() : null;
    }
}

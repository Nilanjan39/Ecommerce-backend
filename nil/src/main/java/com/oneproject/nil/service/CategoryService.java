package com.oneproject.nil.service;

import com.oneproject.nil.model.Category;
import com.oneproject.nil.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepo repo;

    // Retrieve all categories
    public List<Category> getAllCategories() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            logger.error("Error retrieving categories: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve categories", e);
        }
    }

    // Retrieve category by ID
    public Category getCategoryById(int id) {
        try {
            return repo.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error retrieving category by ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to retrieve category", e);
        }
    }

    // Check if category exists by ID
    public boolean existsById(int id) {
        try {
            return repo.existsById(id);
        } catch (Exception e) {
            logger.error("Error checking existence of category with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to check category existence", e);
        }
    }

    // Add a new category
    public void addCategory(Category category) {
        try {
            repo.save(category);  // Save category object
        } catch (Exception e) {
            logger.error("Error while saving category: {}", e.getMessage());
            throw new RuntimeException("Failed to add category", e);
        }
    }

    // Update an existing category
    public void updateCategory(Category category) {
        try {
            if (!repo.existsById(category.getId())) {
                throw new IllegalArgumentException("Category not found with ID: " + category.getId());
            }
            repo.save(category);  // Save updated category
        } catch (Exception e) {
            logger.error("Error while updating category: {}", e.getMessage());
            throw new RuntimeException("Failed to update category", e);
        }
    }

    // Delete a category by ID
    public void deleteCategory(int id) {
        try {
            if (!repo.existsById(id)) {
                throw new IllegalArgumentException("Category not found with ID: " + id);
            }
            repo.deleteById(id);  // Delete category by ID
        } catch (Exception e) {
            logger.error("Error while deleting category with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete category", e);
        }
    }
}

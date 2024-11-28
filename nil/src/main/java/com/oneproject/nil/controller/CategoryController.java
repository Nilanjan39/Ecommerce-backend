package com.oneproject.nil.controller;

import com.oneproject.nil.model.Category;
import com.oneproject.nil.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    // Retrieve all categories
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCategories() {
        List<Category> categories = service.getAllCategories();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Categories retrieved successfully!");
        response.put("status", HttpStatus.OK.value());
        response.put("data", categories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Retrieve a category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable int categoryId) {
        Map<String, Object> response = new HashMap<>();
        Category category = service.getCategoryById(categoryId);
        if (category != null) {
            response.put("message", "Category retrieved successfully!");
            response.put("status", HttpStatus.OK.value());
            response.put("data", category);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Category not found!");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Add a category
    @PostMapping
    public ResponseEntity<Map<String, Object>> addCategory(@RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.addCategory(category);  // Save category
            response.put("message", "Category added successfully!");
            response.put("status", HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Failed to add category. Please try again.");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a category
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!service.existsById(category.getId())) {
                response.put("message", "Category not found!");
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            service.updateCategory(category);
            response.put("message", "Category updated successfully!");
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("message", "Failed to update category. Please try again.");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable int categoryId) {
        Map<String, Object> response = new HashMap<>();
        Category category = service.getCategoryById(categoryId);
        if (category != null) {
            service.deleteCategory(categoryId);
            response.put("message", "Category deleted successfully!");
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Category not found!");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

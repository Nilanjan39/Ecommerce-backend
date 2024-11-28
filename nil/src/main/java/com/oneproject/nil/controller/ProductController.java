package com.oneproject.nil.controller;

import com.oneproject.nil.mapper.ProductMapper;
import com.oneproject.nil.model.Product;
import com.oneproject.nil.model.ProductSearchFilter;
import com.oneproject.nil.request.ProductReq;
import com.oneproject.nil.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper productMapper; // Inject the mapper

    // Retrieve all products
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts() {
        List<Product> products = service.getAllProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Products retrieved successfully!");
        response.put("status", HttpStatus.OK.value());
        response.put("data", products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Retrieve a product by ID
    @GetMapping("/{prodId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable int prodId) {
        Map<String, Object> response = new HashMap<>();
        ProductReq productReq = service.getProductById(prodId);  // Correct return type
        if (productReq != null) {
            response.put("message", "Product retrieved successfully!");
            response.put("status", HttpStatus.OK.value());
            response.put("data", productReq);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Product not found!");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    // Add a new product
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductReq productReq) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validate if description or categoryId is null/empty
            if (productReq.getDescription() == null || productReq.getDescription().isEmpty()) {
                response.put("message", "Description cannot be empty.");
                response.put("status", HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (productReq.getCategoryId() == 0) { // Assuming 0 means invalid or unassigned
                response.put("message", "Category ID cannot be empty or zero.");
                response.put("status", HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Directly pass the ProductReq to the service
            service.addProduct(productReq);  // Pass ProductReq, not Product

            response.put("message", "Product added successfully!");
            response.put("status", HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("message", "Failed to add product. Please try again.");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a product
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody ProductReq productReq) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!service.existsById(productReq.getId())) {
                response.put("message", "Product not found!");
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Map ProductReq to Product entity using ProductMapper
            Product product = productMapper.productReqToProduct(productReq);

            service.updateProduct(product);
            response.put("message", "Product updated successfully!");
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("message", "Failed to update product. Please try again.");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a product
    @DeleteMapping("/{prodId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable int prodId) {
        Map<String, Object> response = new HashMap<>();
        ProductReq product = service.getProductById(prodId);
        if (product != null) {
            service.deleteProduct(prodId);
            response.put("message", "Product deleted successfully!");
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Product not found!");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Search products based on some filters
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestBody ProductSearchFilter filter) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Product> products = service.searchProducts(
                    filter.getKeyword(),
                    filter.getMinPrice(),
                    filter.getMaxPrice(),
                    filter.getCategoryId()
            );
            if (!products.isEmpty()) {
                response.put("message", "Products found!");
                response.put("status", HttpStatus.OK.value());
                response.put("data", products);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "No products found for the given criteria.");
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("message", "Failed to search products. Please try again.");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

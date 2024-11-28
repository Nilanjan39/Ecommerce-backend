package com.oneproject.nil.service;

import com.oneproject.nil.model.Product;
import com.oneproject.nil.model.Category;
import com.oneproject.nil.repo.ProductRepo;
import com.oneproject.nil.repo.CategoryRepo;
import com.oneproject.nil.data.ProductSpecifications;
import com.oneproject.nil.request.ProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    // Method to convert Product entity to ProductReq DTO
    public ProductReq convertToProductReq(Product product) {
        ProductReq productReq = new ProductReq();
        productReq.setId(product.getId());
        productReq.setName(product.getName());
        productReq.setDescription(product.getDescription());
        productReq.setBrand(product.getBrand());
        productReq.setPrice(product.getPrice());
        productReq.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : 0); // Ensure you handle categoryId correctly
        productReq.setStockQuantity(product.getStockQuantity());
        return productReq;
    }

    // Add a new product
    public void addProduct(ProductReq productReq) {
        // Validate categoryId and map to Category
        Category category = categoryRepo.findById(productReq.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Map ProductReq to Product entity
        Product product = new Product();
        product.setName(productReq.getName());
        product.setDescription(productReq.getDescription());
        product.setBrand(productReq.getBrand());
        product.setPrice(productReq.getPrice());
        product.setCategory(category);
        product.setStockQuantity(productReq.getStockQuantity());

        // Save Product to the repository
        productRepo.save(product);
    }


    // Update a product
    public void updateProduct(Product productReq) {
        Product existingProduct = productRepo.findById(productReq.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existingProduct.setName(productReq.getName());
        existingProduct.setDescription(productReq.getDescription());
        existingProduct.setBrand(productReq.getBrand());
        existingProduct.setPrice(productReq.getPrice());
        existingProduct.setStockQuantity(productReq.getStockQuantity());

        Category category = categoryRepo.findById(productReq.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        existingProduct.setCategory(category);

        productRepo.save(existingProduct);
    }

    // Search products based on filter
    public List<Product> searchProducts(String keyword, Double minPrice, Double maxPrice, Integer categoryId) {
        Specification<Product> spec = Specification.where(ProductSpecifications.hasKeyword(keyword));

        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.hasMinPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.hasMaxPrice(maxPrice));
        }
        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            spec = spec.and(ProductSpecifications.hasCategory(category));
        }

        return productRepo.findAll(spec);
    }

    // Delete a product
    public void deleteProduct(int prodId) {
        productRepo.deleteById(prodId);
    }

    // Check if a product exists by ID
    public boolean existsById(int prodId) {
        return productRepo.existsById(prodId);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // Get product by ID and return ProductReq
    public ProductReq getProductById(int productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return convertToProductReq(product); // Convert Product entity to ProductReq DTO
    }
}


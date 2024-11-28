package com.oneproject.nil.data;

import com.oneproject.nil.model.Product;
import com.oneproject.nil.model.Category;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), searchPattern)
            );
        };
    }

    public static Specification<Product> hasMinPrice(Double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasCategory(Category category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }
}

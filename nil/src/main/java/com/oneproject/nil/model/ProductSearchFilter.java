package com.oneproject.nil.model;

import lombok.Data;

@Data
public class ProductSearchFilter {
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
    private Integer categoryId;
}


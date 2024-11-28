package com.oneproject.nil.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductReq {

    private int id; // Change to Long if Product entity uses Long for id

    private String name;
    private String description;
    private String brand;
    private BigDecimal price;

    private int categoryId;  // Change this to int

    private int stockQuantity;
}

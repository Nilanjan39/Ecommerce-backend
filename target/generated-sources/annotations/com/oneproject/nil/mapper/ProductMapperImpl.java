package com.oneproject.nil.mapper;

import com.oneproject.nil.model.Category;
import com.oneproject.nil.model.Product;
import com.oneproject.nil.request.ProductReq;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T00:55:45+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product productReqToProduct(ProductReq productReq) {
        if ( productReq == null ) {
            return null;
        }

        Product product = new Product();

        product.setCategory( productReqToCategory( productReq ) );
        product.setId( productReq.getId() );
        product.setName( productReq.getName() );
        product.setDescription( productReq.getDescription() );
        product.setBrand( productReq.getBrand() );
        product.setPrice( productReq.getPrice() );
        product.setStockQuantity( productReq.getStockQuantity() );

        return product;
    }

    @Override
    public ProductReq productToProductReq(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductReq productReq = new ProductReq();

        productReq.setCategoryId( productCategoryId( product ) );
        productReq.setId( product.getId() );
        productReq.setName( product.getName() );
        productReq.setDescription( product.getDescription() );
        productReq.setBrand( product.getBrand() );
        productReq.setPrice( product.getPrice() );
        productReq.setStockQuantity( product.getStockQuantity() );

        return productReq;
    }

    protected Category productReqToCategory(ProductReq productReq) {
        if ( productReq == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( productReq.getCategoryId() );

        return category;
    }

    private int productCategoryId(Product product) {
        if ( product == null ) {
            return 0;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return 0;
        }
        int id = category.getId();
        return id;
    }
}

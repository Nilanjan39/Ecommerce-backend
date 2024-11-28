package com.oneproject.nil.mapper;

import com.oneproject.nil.model.Product;
import com.oneproject.nil.request.ProductReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Map ProductReq to Product
    @Mapping(source = "categoryId", target = "category.id") // mapping categoryId to category.id
    Product productReqToProduct(ProductReq productReq);

    // Map Product to ProductReq
    @Mapping(source = "category.id", target = "categoryId") // mapping category.id to categoryId
    ProductReq productToProductReq(Product product);
}

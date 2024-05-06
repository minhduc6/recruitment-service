package com.example.recruitmentservice.service;

import com.example.recruitmentservice.exceptions.NotFoundException;
import com.example.recruitmentservice.model.Product;
import com.example.recruitmentservice.vo.CreateProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(Integer pageNo, Integer pageSize);

    Integer countTotalProducts();

    Product getProductById(Integer productId) throws NotFoundException;

    Product createProduct(CreateProductRequest createProductRequest);

    Product deleteProduct(Integer productId) throws NotFoundException;
}

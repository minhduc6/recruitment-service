package com.example.recruitmentservice.service;

import com.example.recruitmentservice.ProductCommandLineRunner;
import com.example.recruitmentservice.exceptions.NotFoundException;
import com.example.recruitmentservice.model.Product;
import com.example.recruitmentservice.vo.CreateProductRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImple implements ProductService{

    private final List<Product> productList;

    public ProductServiceImple() {
        this.productList = ProductCommandLineRunner.productList;
    }
    @Override
    public List<Product> getAllProducts(Integer pageNo, Integer pageSize) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, productList.size());

        if (startIndex >= endIndex) {
            return Collections.emptyList();
        }

        return productList.subList(startIndex, endIndex);
    }

    @Override
    public Integer countTotalProducts() {
        return productList.size();
    }

    @Override
    public Product getProductById(Integer productId) throws NotFoundException {
        for (Product product : productList) {
            if (product.getId() == productId) {
                return product;
            }
        }
        throw new NotFoundException("Product not found with ID: " + productId);
    }

    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
        Product product = new Product();
        product.setId(productList.size()+1);
        product.setTitle(createProductRequest.getTitle());
        product.setDescription(createProductRequest.getDescription());
        product.setPrice(createProductRequest.getPrice());
        productList.add(product);
        return product;
    }

    @Override
    public Product deleteProduct(Integer productId) throws NotFoundException {
        for (Product product : productList) {
            if (product.getId() == productId) {
                productList.remove(product);
                return product;
            }
        }
        throw new NotFoundException("Product not found with ID: " + productId);
    }
}

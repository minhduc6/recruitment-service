package com.example.recruitmentservice.controller;

import com.example.recruitmentservice.exceptions.NotFoundException;
import com.example.recruitmentservice.model.PagingWrapper;
import com.example.recruitmentservice.model.Product;
import com.example.recruitmentservice.model.ResponseWrapper;
import com.example.recruitmentservice.service.ProductService;
import com.example.recruitmentservice.vo.CreateProductRequest;
import com.example.recruitmentservice.vo.UpdateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;



@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PagingWrapper<Product> getAllProducts(@RequestParam Integer page, @RequestParam Integer size) {
        List<Product> paginatedData = productService.getAllProducts(page, size);

        // Get the total number of elements and calculate the total number of pages
        long totalElements = productService.countTotalProducts();
        long totalPages = (long) Math.ceil((double) totalElements / size);

        return new PagingWrapper<>(page, size, totalElements, totalPages, paginatedData);
    }


    @GetMapping("/{id}")
    public ResponseWrapper<Product> getProductById(@PathVariable("id") Integer id) throws NotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseWrapper<>(200, HttpStatus.OK.value(), "Success", product);
    }


    @PostMapping
    public ResponseWrapper<Product> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest){
        Product product = productService.createProduct(createProductRequest);
        return new ResponseWrapper<>(200, HttpStatus.OK.value(), "Success", product);
    }


    @PatchMapping("/{id}")
    public  ResponseWrapper<Product> updateProduct(@PathVariable int id, @Valid @RequestBody UpdateProductRequest updateProduct) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            throw new NotFoundException("Product not found");
        }

        Field[] fields = UpdateProductRequest.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updateProduct);
                if (updatedValue != null) {
                    Field existingField = Product.class.getDeclaredField(field.getName());
                    existingField.setAccessible(true);
                    existingField.set(existingProduct, updatedValue);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                // Handle exception if needed
            }
        }
        return new ResponseWrapper<>(200, HttpStatus.OK.value(), "Success", existingProduct);
    }


    @DeleteMapping("/{id}")
    public  ResponseWrapper<Product> deleteProduct(@PathVariable int id) {
        Product deleteProduct = productService.deleteProduct(id);
        return new ResponseWrapper<>(200, HttpStatus.OK.value(), "Success", deleteProduct);
    }






}

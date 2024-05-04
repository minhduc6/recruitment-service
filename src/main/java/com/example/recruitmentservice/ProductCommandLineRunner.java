package com.example.recruitmentservice;

import com.example.recruitmentservice.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCommandLineRunner implements CommandLineRunner {

    public static final List<Product> productList = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        // Mock  data
        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            product.setId(i);
            product.setTitle("Product " + i);
            product.setDescription("Description of Product " + i);
            product.setPrice(10.99 * i);
            // Set other attributes as needed
            productList.add(product);
        }
    }
}

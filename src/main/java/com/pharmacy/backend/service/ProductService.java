package com.pharmacy.backend.service;

import com.pharmacy.backend.model.Product;
import com.pharmacy.backend.model.Product.Category;
import com.pharmacy.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByCategory(String categoryName) {
        Category category = Category.valueOf(categoryName.toUpperCase());
        return productRepository.findByCategory(category);
    }
}

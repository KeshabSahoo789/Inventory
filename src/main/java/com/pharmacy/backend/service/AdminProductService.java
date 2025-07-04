package com.pharmacy.backend.service;

import com.pharmacy.backend.model.AdminProduct;
import com.pharmacy.backend.repository.AdminProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminProductService {

    @Autowired
    private AdminProductRepository productRepository;

    public List<AdminProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public AdminProduct getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public AdminProduct getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public void reduceStockByName(String name, int quantitySold) {
        AdminProduct product = getProductByName(name);
        int newStock = product.getQuantityInStock() - quantitySold;
        product.setQuantityInStock(newStock);
        productRepository.save(product);
    }

    public AdminProduct updateQuantity(Long id, int quantity) {
        AdminProduct product = productRepository.findById(id).orElseThrow();
        product.setQuantityInStock(quantity);
        return productRepository.save(product);
    }
}

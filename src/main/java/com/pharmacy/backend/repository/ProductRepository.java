package com.pharmacy.backend.repository;

import com.pharmacy.backend.model.Product;
import com.pharmacy.backend.model.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}

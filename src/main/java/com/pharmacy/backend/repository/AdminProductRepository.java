package com.pharmacy.backend.repository;

import com.pharmacy.backend.model.AdminProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminProductRepository extends JpaRepository<AdminProduct, Long> {
    List<AdminProduct> findByCategory(String category);

    AdminProduct findByName(String name);
}
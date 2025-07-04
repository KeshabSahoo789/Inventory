package com.pharmacy.backend.repository;

import com.pharmacy.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByEmployeeEmail(String email);
    CartItem findByProductNameAndEmployeeEmail(String productName, String employeeEmail);
}
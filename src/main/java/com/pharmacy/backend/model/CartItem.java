package com.pharmacy.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private double price;
    private String image;
    private String employeeEmail;

    @Transient
    public double getTotal() {
        return this.quantity * this.price;
    }

}
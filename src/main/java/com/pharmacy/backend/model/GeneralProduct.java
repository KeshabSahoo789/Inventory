package com.pharmacy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralProduct {
    private String name;
    private String description;
    private String image;
    private double price;
    // getters and setters
}

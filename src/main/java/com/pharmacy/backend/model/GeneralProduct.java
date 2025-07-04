package com.pharmacy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor

public class GeneralProduct {
    private String name;
    private String description;
    private String image;
    private double price;

    public String getName() {
        return name;
    }

    public GeneralProduct(String name, String description, String image, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
// getters and setters
}

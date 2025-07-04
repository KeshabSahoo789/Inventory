package com.pharmacy.backend.service;

import com.pharmacy.backend.model.CartItem;
import com.pharmacy.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public void addToCart(String productName, double price, int quantity, String email) {
        CartItem existingItem = cartRepository.findByProductNameAndEmployeeEmail(productName, email);

        if (existingItem != null) {
            int updatedQty = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(updatedQty);
            existingItem.setPrice(price);
            cartRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductName(productName);
            newItem.setPrice(price);
            newItem.setQuantity(quantity);
            newItem.setEmployeeEmail(email);
            cartRepository.save(newItem);
        }
    }

    public List<CartItem> getCartItems(String email) {
        return cartRepository.findByEmployeeEmail(email);
    }

    public void updateQuantity(String productName, int newQty, String email) {
        CartItem item = cartRepository.findByProductNameAndEmployeeEmail(productName, email);
        if (item != null) {
            if (newQty <= 0) {
                cartRepository.delete(item);
            } else {
                item.setQuantity(newQty);
                cartRepository.save(item);
            }
        }
    }

    public void removeFromCart(String productName, String email) {
        CartItem item = cartRepository.findByProductNameAndEmployeeEmail(productName, email);
        if (item != null) {
            cartRepository.delete(item);
        }
    }

    public void clearCartByEmail(String email) {
        List<CartItem> items = cartRepository.findByEmployeeEmail(email);
        cartRepository.deleteAll(items);
    }
}

package com.pharmacy.backend.controller;

import com.pharmacy.backend.model.CartItem;
import com.pharmacy.backend.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * This method is used only if fallback form-submit is used
     * (not needed if AJAX is active).
     */
    @PostMapping("/add")
    public String addToCart(
                            @RequestParam String productName,
                            @RequestParam double price,
                            @RequestParam int quantity,
                            HttpSession session) {
        System.out.println("Adding to cart: " + productName + ", qty: " + quantity );

        String employeeEmail = (String) session.getAttribute("employeeEmail");
        if (employeeEmail != null) {
            cartService.addToCart( productName, price, quantity, employeeEmail);  // ✅ fixed
        }
        return "redirect:/employee/products/general";
    }


    /**
     * Loads cart page with items belonging to logged-in employee.
     */
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        String email = (String) session.getAttribute("employeeEmail");
        if (email != null) {
            List<CartItem> items = cartService.getCartItems(email);
            model.addAttribute("cartItems", items);
            model.addAttribute("employeeEmail", email); // Pass to Thymeleaf
        }
        return "cart"; // cart.html in templates/employee/
    }
}

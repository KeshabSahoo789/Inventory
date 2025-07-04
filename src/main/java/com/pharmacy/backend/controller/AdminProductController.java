package com.pharmacy.backend.controller;
import com.pharmacy.backend.model.AdminProduct;
import com.pharmacy.backend.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminProductController {
    @Autowired
    private AdminProductService service;
    @GetMapping("/products")
    public String showAllProducts(Model model) {
        List<AdminProduct> products = service.getAllProducts();
        model.addAttribute("products", products);
        return "admin-product"; // corresponds to admin-product.html
    }
    @PostMapping("/products/update")
    public String updateProductQuantity(@RequestParam Long id,
                                        @RequestParam int quantity) {
        service.updateQuantity(id, quantity);
        return "redirect:/admin/products";
    }
}
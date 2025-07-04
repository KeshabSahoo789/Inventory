package com.pharmacy.backend.controller;

import com.pharmacy.backend.model.AdminProduct;
import com.pharmacy.backend.model.CartItem;
import com.pharmacy.backend.service.AdminProductService;
import com.pharmacy.backend.service.CartService;
import com.pharmacy.backend.controller.PDFGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class CheckoutController {

    @Autowired
    private AdminProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("employeeEmail");
        if (email == null) {
            return "redirect:/employee/login";
        }

        List<CartItem> cart = cartService.getCartItems(email);
        if (cart == null || cart.isEmpty()) {
            return "redirect:/employee/cart";
        }

        double subtotal = 0;
        for (CartItem item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double gst = subtotal * 0.18;
        double total = subtotal + gst;

        model.addAttribute("cart", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("gst", gst);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/confirm-order")
    public ResponseEntity<byte[]> confirmOrder(@RequestParam String customerName,
                                               @RequestParam String customerPhone,
                                               HttpSession session) throws IOException {
        String email = (String) session.getAttribute("employeeEmail");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired".getBytes());
        }

        List<CartItem> cart = cartService.getCartItems(email);
        if (cart == null || cart.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty.".getBytes());
        }

        // Inventory check using product name
        for (CartItem item : cart) {
            AdminProduct product = productService.getProductByName(item.getProductName());
            if (product.getQuantityInStock() < item.getQuantity()) {
                return ResponseEntity
                        .badRequest()
                        .body(("Insufficient stock for product: " + product.getName()).getBytes());
            }
        }

        // Reduce inventory stock
        for (CartItem item : cart) {
            productService.reduceStockByName(item.getProductName(), item.getQuantity());
        }

        // Generate bill PDF
        ByteArrayOutputStream pdfStream = PDFGenerator.generateBillPDF(customerName, customerPhone, cart);

        // Clear cart
        cartService.clearCartByEmail(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "invoice.pdf");

        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }
}

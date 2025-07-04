package com.pharmacy.backend.controller;

import com.pharmacy.backend.model.CartItem;
import com.pharmacy.backend.model.GeneralProduct;
import com.pharmacy.backend.service.CartService;
import com.pharmacy.backend.service.GeneralProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee/products")
public class ProductController {

    @Autowired
    private GeneralProductService productService;

    @Autowired
    private CartService cartService;

    private Map<String, Integer> getCartQuantityMap(String email) {
        Map<String, Integer> cartMap = new HashMap<>();
        if (email != null) {
            List<CartItem> cartItems = cartService.getCartItems(email);
            for (CartItem item : cartItems) {
                cartMap.put(item.getProductName(), item.getQuantity());
            }
        }
        return cartMap;
    }

    @GetMapping("/general")
    public String showGeneralProducts(HttpSession session, Model model) {
        String email = (String) session.getAttribute("employeeEmail");


        List<GeneralProduct> products = productService.getGeneralProducts();
        List<CartItem> cart = cartService.getCartItems(email);

        Map<String, Integer> cartQuantities = new HashMap<>();
        for (CartItem item : cart) {
            cartQuantities.put(item.getProductName(), item.getQuantity());
        }

        model.addAttribute("generalProducts", products);
        model.addAttribute("employeeEmail", email);
        model.addAttribute("cartQuantities", cartQuantities);

        return "general-products";
    }

    @GetMapping("/gyn")
    public String showGynProducts(Model model, HttpSession session) {
        List<GeneralProduct> gynProducts = productService.getGynProducts();
        String email = (String) session.getAttribute("employeeEmail");

        model.addAttribute("gynProducts", gynProducts);
        model.addAttribute("employeeEmail", email);
        model.addAttribute("cartQuantities", getCartQuantityMap(email));
        return "gynaecology-products";
    }

    @GetMapping("/cardiology")
    public String showCardiologyProducts(Model model, HttpSession session) {
        List<GeneralProduct> cardioProducts = productService.getCardiologyProducts();
        String email = (String) session.getAttribute("employeeEmail");

        model.addAttribute("cardiologyProducts", cardioProducts);
        model.addAttribute("employeeEmail", email);
        model.addAttribute("cartQuantities", getCartQuantityMap(email));
        return "cardiology-products";
    }

    @GetMapping("/neurology")
    public String showNeurologyProducts(Model model, HttpSession session) {
        List<GeneralProduct> neurologyProducts = List.of(
                new GeneralProduct("Levetiracetam", "Used to treat epilepsy and seizures.", "levetiracetam.jpg", 55.0),
                new GeneralProduct("Carbamazepine", "For bipolar disorder and seizures.", "carbamazepine.jpg", 60.0),
                new GeneralProduct("Phenytoin", "Controls and prevents seizures.", "phenytoin.jpg", 48.0),
                new GeneralProduct("Gabapentin", "Treats nerve pain and epilepsy.", "gabapentin.jpg", 42.0),
                new GeneralProduct("Donepezil", "Used for Alzheimer’s treatment.", "donepezil.jpg", 70.0)
        );

        String email = (String) session.getAttribute("employeeEmail");

        model.addAttribute("neurologyProducts", neurologyProducts);
        model.addAttribute("employeeEmail", email);
        model.addAttribute("cartQuantities", getCartQuantityMap(email));
        return "neurology-products";
    }
}

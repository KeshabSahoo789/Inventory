package com.pharmacy.backend.controller;

import com.pharmacy.backend.model.Admin;
import com.pharmacy.backend.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ Show login page (web)
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-login";
    }

    // ✅ Login via Web Form (Thymeleaf)
    @PostMapping("/custom-login")
    public String loginWeb(@ModelAttribute("admin") Admin admin, Model model) {
        try {
            // Authenticate with MySQL
            Admin dbAdmin = adminService.loginAdmin(admin.getEmail(), admin.getPassword());

            // Set Spring Security context
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    dbAdmin.getEmail(), null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            return "redirect:/admin/dashboard";

        } catch (Exception e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            model.addAttribute("admin", new Admin());
            return "admin-login";
        }
    }

    // ✅ Register via Web Form (Thymeleaf)
    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute("admin") Admin admin, Model model) {
        try {
            adminService.registerAdmin(admin);
            model.addAttribute("success", "Registration successful. Please login.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("admin", new Admin());
        return "admin-login";
    }

    // ✅ Dashboard
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin-dashboard";
    }

    // ✅ API login via POSTMAN / Flutter (without Firebase)
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> loginApi(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            Admin admin = adminService.loginAdmin(email, password);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "fullName", admin.getFullName(),
                    "email", admin.getEmail()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("status", "error", "message", e.getMessage())
            );
        }
    }
}

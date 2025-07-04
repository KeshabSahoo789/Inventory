package com.pharmacy.backend.service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.pharmacy.backend.model.Admin;
import com.pharmacy.backend.repository.AdminRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FirebaseAuth firebaseAuth; // from Firebase Admin SDK

    @Synchronized
    public Admin registerAdmin(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (!admin.getPassword().equals(admin.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // ✅ Create Firebase user
        try {
            CreateRequest request = new CreateRequest()
                    .setEmail(admin.getEmail())
                    .setPassword(admin.getPassword());
            firebaseAuth.createUser(request);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Firebase registration failed: " + e.getMessage());
        }

        // ✅ Save in MySQL
        return adminRepository.save(admin);
    }


    @Synchronized
    public Admin loginAdmin(String email, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Admin not found");
        }
    }


}

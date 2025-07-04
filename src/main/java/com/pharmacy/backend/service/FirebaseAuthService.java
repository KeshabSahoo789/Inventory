// src/main/java/com/pharmacy/backend/service/FirebaseAuthService.java
package com.pharmacy.backend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.pharmacy.backend.dto.FirebaseLoginRequest;
import com.pharmacy.backend.dto.FirebaseLoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FirebaseAuthService {
    private final WebClient webClient = WebClient.create("https://identitytoolkit.googleapis.com/v1");

    private final String firebaseApiKey = "AIzaSyA3A7CFB4t2r8misaIi5C_Q8iexml9HzTQ"; // Replace with your key

    public FirebaseLoginResponse loginWithEmail(FirebaseLoginRequest request) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts:signInWithPassword")
                        .queryParam("key", firebaseApiKey)
                        .build())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FirebaseLoginResponse.class)
                .block();
    }

    public void createFirebaseUser(String email, String phone) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setEmailVerified(false)
                    .setPassword("123456") // default password or random
                    .setDisabled(false);

            FirebaseAuth.getInstance().createUser(request);
        } catch (Exception e) {
            throw new RuntimeException("Firebase user creation failed: " + e.getMessage());
        }
    }

}

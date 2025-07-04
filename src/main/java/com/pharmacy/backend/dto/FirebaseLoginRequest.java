package com.pharmacy.backend.dto;

import lombok.Data;

@Data
public class FirebaseLoginRequest {
    private String email;
    private String password;
    private boolean returnSecureToken = true;
}

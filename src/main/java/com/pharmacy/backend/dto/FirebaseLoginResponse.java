package com.pharmacy.backend.dto;

import lombok.Data;

@Data
public class FirebaseLoginResponse {
    private String idToken;
    private String email;
    private String refreshToken;
    private String expiresIn;
    private String localId;
    private boolean registered;
}

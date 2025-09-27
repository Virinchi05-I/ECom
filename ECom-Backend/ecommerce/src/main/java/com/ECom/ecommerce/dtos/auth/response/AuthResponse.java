package com.ECom.ecommerce.dtos.auth.response;

import java.time.LocalDate;

import com.ECom.ecommerce.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
    private LocalDate dateOfBirth;
    private boolean active;
    private boolean emailVerified;
    private boolean phoneVerified;
    private String accessToken;
    private String refreshToken;
    private String message; 
    
    
    public AuthResponse(String accessToken, String refreshToken, String message) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }
}

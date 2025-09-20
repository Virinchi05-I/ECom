package com.ECom.ecommerce.services;

import com.ECom.ecommerce.dtos.auth.request.LoginRequest;
import com.ECom.ecommerce.dtos.auth.request.RegisterRequest;
import com.ECom.ecommerce.dtos.auth.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request); 
    
    AuthResponse login(LoginRequest request); 
    
    boolean validateToken(String token);

    AuthResponse refreshToken(String refreshToken);

    boolean revokeToken(String refreshToken);

    void logout(String refreshToken);

}


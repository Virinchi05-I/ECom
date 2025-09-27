package com.ECom.ecommerce.services.auth;

import com.ECom.ecommerce.dtos.auth.request.LoginRequest;
import com.ECom.ecommerce.dtos.auth.request.LogoutRequest;
import com.ECom.ecommerce.dtos.auth.request.RegisterRequest;
import com.ECom.ecommerce.dtos.auth.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request); 
    
    AuthResponse login(LoginRequest request); 
    
    boolean validateAccessToken(String token);
    
    boolean validateRefreshToken(String refreshToken);

    AuthResponse refreshToken(String refreshToken);

    AuthResponse revokeToken(String refreshToken);

    AuthResponse logout(LogoutRequest logoutRequest);

    String createRefreshToken(Long userId);

}


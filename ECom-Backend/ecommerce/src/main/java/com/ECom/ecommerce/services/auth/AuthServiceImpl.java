package com.ECom.ecommerce.services.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.auth.request.LoginRequest;
import com.ECom.ecommerce.dtos.auth.request.LogoutRequest;
import com.ECom.ecommerce.dtos.auth.request.RegisterRequest;
import com.ECom.ecommerce.dtos.auth.response.AuthResponse;
import com.ECom.ecommerce.entities.RefreshToken;
import com.ECom.ecommerce.entities.Role;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.RefreshTokenRepo;
import com.ECom.ecommerce.repositories.UserRepo;
import com.ECom.ecommerce.services.email.EmailService;
import com.ECom.ecommerce.utils.JWTUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private EmailService emailService;


    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        User user = new User();
        user.setId(registerRequest.getId());
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setDateOfBirth(registerRequest.getDateOfBirth());

        user.setRole(Role.ROLE_CUSTOMER);
        user.setActive(true);
        user.setEmailVerified(false);
        user.setPhoneVerified(false);

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        user.setEmailVerificationToken(otp);
        User savedUser = userRepo.save(user);

        emailService.sendEmailVerification(savedUser.getEmail(), otp);

        return mapToResponse(savedUser);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("This Email is not registered"));

        if (!user.isActive()) throw new IllegalArgumentException("User account is inactive");

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword()))
            throw new IllegalArgumentException("Invalid password");

        AuthResponse response = mapToResponse(user);

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = createRefreshToken(user.getId());

        // Save refresh token in DB
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUser(user);
        refreshTokenRepo.save(tokenEntity);

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return false;
        }
        try {
            return jwtUtil.validateToken(accessToken);
        } catch (Exception e) {
            
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
       return jwtUtil.validateRefreshToken(refreshToken);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
        throw new RuntimeException("Invalid or expired refresh token");
    }

    Long userId = Long.parseLong(jwtUtil.getUserIdFromToken(refreshToken));
    String role = jwtUtil.getRoleFromToken(refreshToken);
    String email = jwtUtil.getClaims(refreshToken).getSubject();


        // Generate new tokens
    String newAccessToken = jwtUtil.generateAccessToken(userId, email, role);
    String newRefreshToken = jwtUtil.generateRefreshToken(userId); 

    AuthResponse response = new AuthResponse(newAccessToken, newRefreshToken, "Token refreshed successfully");

    return response;
    }

    @Override
    public AuthResponse revokeToken(String refreshToken) {
        Optional<RefreshToken> tokenOpt = refreshTokenRepo.findByToken(refreshToken);
        if (tokenOpt.isEmpty()) {
            throw new RuntimeException("Token not found");
        }

        RefreshToken token = tokenOpt.get();
        token.setRevoked(true);
        refreshTokenRepo.save(token);

        return new AuthResponse(null, null, "Refresh token revoked successfully");
    }

    @Override
    public AuthResponse logout(LogoutRequest logoutRequest) {
        String refreshToken = logoutRequest.refreshToken();
        
        return new AuthResponse(null, null, "Logged out successfully");
    }

    @Override
    public String createRefreshToken(Long userId) {
        return jwtUtil.generateRefreshToken(userId);
    }
    
    private AuthResponse mapToResponse(User user) {
        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        response.setEmailVerified(user.isEmailVerified());
        response.setPhoneVerified(user.isPhoneVerified());
        return response;
    }
}

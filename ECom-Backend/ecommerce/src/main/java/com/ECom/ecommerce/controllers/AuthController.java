package com.ECom.ecommerce.controllers;

import com.ECom.ecommerce.dtos.auth.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ECom.ecommerce.dtos.auth.response.AuthResponse;
import com.ECom.ecommerce.services.auth.AuthService;
import com.ECom.ecommerce.services.email.EmailService;
import com.ECom.ecommerce.services.phoneValidation.PhoneNumberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final EmailService emailService;
    
    private final PhoneNumberService phoneNumberService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(
                    new AuthResponse(null, null, e.getMessage())
            );
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@Valid @RequestBody LogoutRequest logoutRequest){
        return ResponseEntity.ok(authService.logout(logoutRequest));
    }

     @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.refreshToken()));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmailOtp(@RequestBody VerifyRequest request) {
        boolean verified = emailService.verifyOtp(request.getEmail(), request.getToken());
        return ResponseEntity.ok(verified ? "Email verified successfully" : "Invalid or expired OTP");
    }

    // 🔹 Forgot Password via Email
    @PostMapping("/forgot-password/email")
    public ResponseEntity<String> forgotPasswordEmail(@RequestParam String email) {
        boolean sent = emailService.forgotPassword(email);
        return ResponseEntity.ok(sent ? "OTP sent to your email" : "Failed to send OTP");
    }

    // 🔹 Reset Password via Email
    @PostMapping("/reset-password/email")
    public ResponseEntity<String> resetPasswordEmail(@RequestParam String email,
                                                     @RequestParam String otp,
                                                     @RequestParam String newPassword) {
        boolean success = emailService.resetPassword(email, otp, newPassword);
        return ResponseEntity.ok(success ? "Password reset successfully" : "Invalid OTP or email");
    }

    // 🔹 Send Phone OTP
    @PostMapping("/send-otp/phone")
    public ResponseEntity<String> sendPhoneOtp(@RequestParam Long userId) {
        String status = phoneNumberService.sendOtp(userId);
        return ResponseEntity.ok("OTP sent via SMS, status: " + status);
    }

    // 🔹 Verify Phone OTP
    @PostMapping("/verify-otp/phone")
    public ResponseEntity<String> verifyPhoneOtp(@RequestParam String phoneNumber,
                                                 @RequestParam String otp) {
        String status = phoneNumberService.verifyOtp(phoneNumber, otp);
        if ("approved".equals(status)) {
            // Mark user active + phoneVerified
            return ResponseEntity.ok("Phone number verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired OTP");
    }

    // 🔹 Forgot Password via Phone
    @PostMapping("/forgot-password/phone")
    public ResponseEntity<String> forgotPasswordPhone(@RequestParam String phoneNumber) {
        String result = phoneNumberService.forgotPassword(phoneNumber);
        return ResponseEntity.ok(result);
    }

    // 🔹 Reset Password via Phone
    @PostMapping("/reset-password/phone")
    public ResponseEntity<String> resetPasswordPhone(@RequestParam String phoneNumber,
                                                     @RequestParam String otp,
                                                     @RequestParam String newPassword) {
        String result = phoneNumberService.resetPassword(phoneNumber, otp, newPassword);
        return ResponseEntity.ok(result);
    }
}

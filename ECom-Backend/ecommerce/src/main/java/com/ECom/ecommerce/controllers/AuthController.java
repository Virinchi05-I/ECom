package com.ECom.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECom.ecommerce.dtos.auth.request.LoginRequest;
import com.ECom.ecommerce.dtos.auth.request.LogoutRequest;
import com.ECom.ecommerce.dtos.auth.request.RefreshTokenRequest;
import com.ECom.ecommerce.dtos.auth.request.RegisterRequest;
import com.ECom.ecommerce.dtos.auth.response.AuthResponse;
import com.ECom.ecommerce.services.auth.AuthService;
import com.ECom.ecommerce.services.email.EmailService;
import com.ECom.ecommerce.services.phoneValidation.PhoneNumberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ECom/auth")
@CrossOrigin()
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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest logoutRequest){
        return ResponseEntity.ok(authService.logout(logoutRequest));
    }

     @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.refreshToken()));
    } 

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmailOtp(@RequestParam String email, @RequestParam String token) {
        boolean verified = emailService.verifyOtp(email, token);
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

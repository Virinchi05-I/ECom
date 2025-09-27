package com.ECom.ecommerce.services.password;

public interface PasswordService {

    void setPassword(Long userId, String newPassword);
    
    boolean validatePassword(Long userId, String password);
    
    void changePassword(Long userId, String oldPassword, String newPassword);

    void forgotPassword(String email);

    void resetByEmailPassword(String token, String newPassword);

    void resetByPhonePassword(String phoneNumber, String otp, String newPassword);

    void sendOtp(Long userId);

    boolean validateOtp(Long userId, String otp);
}

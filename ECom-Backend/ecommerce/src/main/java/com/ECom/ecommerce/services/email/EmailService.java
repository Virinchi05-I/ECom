package com.ECom.ecommerce.services.email;

import java.util.List;

public interface EmailService {

    // 🔹 Generic email sender
    void sendEmail(String to, String subject, String body);

    // 🔹 Forgot / reset password
    boolean forgotPassword(String email);
    boolean resetPassword(String email, String otp, String newPassword);

    // 🔹 Email verification (for signup / account activation)
    void sendEmailVerification(String to, String token);

    // 🔹 OTP verification
    boolean verifyOtp(String email, String token);

    // 🔹 Order-related notifications
    void sendOrderConfirmation(String to, Long orderId);
    void sendOrderStatusUpdate(String to, Long orderId, String status);
    void sendOrderCancellation(String to, Long orderId);
    void sendOrderDelivery(String to, Long orderId);
    void sendOrderReturn(String to, Long orderId);
    void sendOrderRefund(String to, Long orderId);
    void sendOrderReplacement(String to, Long orderId);

    // 🔹 Bulk / promotional emails
    void sendBulkEmail(List<String> recipients, String subject, String body);
}

package com.ECom.ecommerce.services.email;

import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    // Temporary OTP/token store (use Redis/DB in production)
    private final Map<String, String> tokenStore = new HashMap<>();

    // 🔹 Core email sender
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(domainName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = enable HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }

    // 🔹 Forgot password
    @Override
    public boolean forgotPassword(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not registered"));

        String otp = generateOtpOrToken();
        tokenStore.put(email, otp);

        sendEmailPasswordReset(email, otp);
        return true; // OTP successfully sent
    }

    // 🔹 Reset password
    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        if (!tokenStore.containsKey(email) || !tokenStore.get(email).equals(otp)) {
            return false; // OTP invalid
        }
        tokenStore.remove(email);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not registered"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return true; // password reset successful
    }

    // 🔹 Send password reset email
    private void sendEmailPasswordReset(String to, String token) {
        String body = "<h3>Password Reset Request</h3>" +
                "<p>Your OTP for resetting password is: <b>" + token + "</b></p>";
        sendEmail(to, "Password Reset", body);
    }

    // 🔹 Email verification for signup
    @Override
    public void sendEmailVerification(String to, String token) {
        tokenStore.put(to, token);
        String body = "<h3>Email Verification</h3>" +
                "<p>Click <a href='http://localhost:8081/auth/verify-email?token=" + token +
                "'>here</a> to verify your account.</p>";
        sendEmail(to, "Verify Your Email", body);
    }

    // 🔹 OTP verification
    @Override
    public boolean verifyOtp(String email, String token) {
        String stored = tokenStore.get(email);
        if (stored == null || !stored.equals(token)) {
            return false; // OTP invalid or expired
        }
        tokenStore.remove(email);

        // Mark user as verified
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not registered"));
        user.setEmailVerified(true);
        user.setActive(true);
        userRepo.save(user);

        return true; // OTP verified successfully
    }

    // 🔹 Order notifications
    @Override
    public void sendOrderConfirmation(String to, Long orderId) {
        sendEmail(to, "Order Confirmation",
                "<h3>Your order #" + orderId + " has been confirmed.</h3>");
    }

    @Override
    public void sendOrderStatusUpdate(String to, Long orderId, String status) {
        sendEmail(to, "Order Update",
                "<h3>Your order #" + orderId + " is now " + status + ".</h3>");
    }

    @Override
    public void sendOrderCancellation(String to, Long orderId) {
        sendEmail(to, "Order Cancelled",
                "<h3>Your order #" + orderId + " has been cancelled.</h3>");
    }

    @Override
    public void sendOrderDelivery(String to, Long orderId) {
        sendEmail(to, "Order Delivered",
                "<h3>Your order #" + orderId + " has been delivered successfully.</h3>");
    }

    @Override
    public void sendOrderReturn(String to, Long orderId) {
        sendEmail(to, "Order Returned",
                "<h3>Your order #" + orderId + " has been returned.</h3>");
    }

    @Override
    public void sendOrderRefund(String to, Long orderId) {
        sendEmail(to, "Order Refunded",
                "<h3>Refund has been processed for order #" + orderId + ".</h3>");
    }

    @Override
    public void sendOrderReplacement(String to, Long orderId) {
        sendEmail(to, "Order Replacement",
                "<h3>Your order #" + orderId + " has been replaced.</h3>");
    }

    // 🔹 Bulk / promotional emails
    @Override
    public void sendBulkEmail(List<String> recipients, String subject, String body) {
        for (String recipient : recipients) {
            sendEmail(recipient, subject, body);
        }
    }

    // 🔹 Utility: Generate OTP/Token
    private String generateOtpOrToken() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit OTP
    }
}

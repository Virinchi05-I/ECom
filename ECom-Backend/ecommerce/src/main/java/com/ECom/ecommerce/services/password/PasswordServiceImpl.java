package com.ECom.ecommerce.services.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.UserRepo;
import com.ECom.ecommerce.services.email.EmailService;
import com.ECom.ecommerce.services.phoneValidation.PhoneNumberService;
import com.ECom.ecommerce.utils.JWTUtil;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PhoneNumberService phoneNumberService; 
    
    @Autowired
    private EmailService emailService; 
    
    @Autowired
    private JWTUtil jwtUtil;

    
    @Override
    public void setPassword(Long userId, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    
    @Override
    public boolean validatePassword(Long userId, String password) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return passwordEncoder.matches(password, user.getPassword());
    }

    
    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    
    @Override
    public void forgotPassword(String phoneOrEmail) {
        
        if (phoneOrEmail.contains("@")) {
            emailService.forgotPassword(phoneOrEmail);
        }else{
            phoneNumberService.forgotPassword(phoneOrEmail);
        }

    }

    // Delegates reset password to PhoneNumberService (OTP verification)
    @Override
public void resetByEmailPassword(String token, String newPassword) {
    String userIdStr;

    try {
        userIdStr = jwtUtil.getUserIdFromToken(token);
    } catch (Exception e) {
        throw new IllegalArgumentException("Invalid or expired token");
    }

    Long userId = Long.parseLong(userIdStr); // 👈 convert to Long

    User user = userRepo.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepo.save(user);
}

    // Delegates sending OTP via PhoneNumberService
    @Override
    public void sendOtp(Long userId) {
        phoneNumberService.sendOtp(userId);
    }

    // Delegates OTP validation via PhoneNumberService
    @Override
    public boolean validateOtp(Long userId, String otp) {
        String status = phoneNumberService.verifyOtp(userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getPhoneNumber(), otp);
        return "approved".equals(status);
    }

    @Override
    public void resetByPhonePassword(String phoneNumber, String otp, String newPassword) {
        phoneNumberService.resetPassword(phoneNumber, otp, newPassword);
    }

}

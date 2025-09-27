package com.ECom.ecommerce.services.phoneValidation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.ECom.ecommerce.config.TwilioConfig;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.repositories.UserRepo;
import com.twilio.rest.verify.v2.service.VerificationCheck;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {


    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

        @Override
        public String sendOtp(Long userId) {
           
            User user = userRepo.findById(userId)
                  .orElseThrow(() -> new RuntimeException("User not found"));

            String phoneNumber = user.getPhoneNumber();
            Verification verification = Verification.creator(
                    twilioConfig.getVerifyServiceSid(),
                    phoneNumber,
                    "sms"
            ).create();
            return verification.getStatus();
        }

    @Override
    public String verifyOtp(String phoneNumber, String otp) {
       VerificationCheck verificationCheck = VerificationCheck.creator(
                twilioConfig.getVerifyServiceSid()
        )
        .setTo(phoneNumber)
        .setCode(otp)
        .create();

        if ("approved".equals(verificationCheck.getStatus())) {
        User user = userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Phone number not registered"));
        user.setPhoneVerified(true);
        user.setActive(true);
        userRepo.save(user);
    }

        return verificationCheck.getStatus();
        
    }

    
    @Override
    public String forgotPassword(String phoneNumber) {
        
        User user = userRepo.findByPhoneNumber(phoneNumber)
        .orElseThrow(() -> new IllegalArgumentException("Phone number not registered"));
        
        // Send OTP via Twilio Verify
        Verification verification = Verification.creator(
            twilioConfig.getVerifyServiceSid(),
            phoneNumber,
            "sms"
            ).create();
            
            return "OTP sent via SMS to " + phoneNumber + " (status: " + verification.getStatus() + ")";
            
        }
    @Override
    public String resetPassword(String phoneNumber, String otp, String newPassword) {
            
        VerificationCheck verificationCheck = VerificationCheck.creator(
                    twilioConfig.getVerifyServiceSid())
                    .setTo(phoneNumber)
                    .setCode(otp)
                    .create();
    
        if (!"approved".equals(verificationCheck.getStatus())) {
                throw new IllegalArgumentException("Invalid OTP");
        }   
    
            // Update password
        User user = userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Phone number not registered"));
    
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    
        return "Password reset successfully";
    }

}

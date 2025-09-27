package com.ECom.ecommerce.services.phoneValidation;

public interface PhoneNumberService {

    public String sendOtp(Long userId);

    public String verifyOtp(String phoneNumber, String otp);

    public String resetPassword(String phoneNumber, String otp, String newPassword);

    public String forgotPassword(String phoneNumber);

}

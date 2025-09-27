package com.ECom.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    @Value("${spring.twilio.account-sid}")
    private String accountSid;

    @Value("${spring.twilio.auth-token}")
    private String authToken;

    @Value("${spring.twilio.verify-service-sid}")
    private String verifyServiceSid;

    @Value("${spring.mobileNumber.host}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken); // Initialize Twilio once on startup
    }

    public String getVerifyServiceSid() {
        return verifyServiceSid;
    }

    public String getFromNumber() {
        return fromNumber;
    }
}



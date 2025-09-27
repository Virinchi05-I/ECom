package com.ECom.ecommerce.utils;

public class PhoneValidator {

    private static final String INTERNATIONAL_REGEX = "^\\+[1-9]\\d{1,14}$";

    // Indian format specifically (+91XXXXXXXXXX, 10 digits starting with 6–9)
    private static final String INDIAN_REGEX = "^\\+91[6-9][0-9]{9}$";

    public static boolean isValidInternational(String phone) {
        return phone != null && phone.matches(INTERNATIONAL_REGEX);
    }

    public static boolean isValidIndian(String phone) {
        return phone != null && phone.matches(INDIAN_REGEX);
    }
}

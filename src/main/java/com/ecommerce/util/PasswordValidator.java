package com.ecommerce.util;

public class PasswordValidator {

    private PasswordValidator() {}

    public static boolean isValid(String password) {
        return password != null &&
                password.length() >= 6 &&
                password.length() <= 100 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }
}

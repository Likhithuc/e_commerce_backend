package com.ecommerce.constant;

public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "roles";
    public static final long OTP_EXPIRATION_MINUTES = 5;

    // Public endpoints that don't require authentication
    public static final String[] PUBLIC_URLS = {
        "/auth/**",
        "/uploads/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/actuator/**"
    };

    private SecurityConstants() {}
}

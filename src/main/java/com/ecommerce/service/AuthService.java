package com.ecommerce.service;

import com.ecommerce.dto.request.*;
import com.ecommerce.dto.response.JwtResponse;
import com.ecommerce.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
    JwtResponse refreshToken(RefreshTokenRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}

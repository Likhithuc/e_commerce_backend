package com.ecommerce.service;

import com.ecommerce.dto.request.UserProfileRequest;
import com.ecommerce.dto.response.UserResponse;

public interface UserService {
    UserResponse getProfile(Long userId);
    UserResponse updateProfile(Long userId, UserProfileRequest request);
}

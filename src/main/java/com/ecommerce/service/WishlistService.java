package com.ecommerce.service;

import com.ecommerce.dto.request.WishlistRequest;
import com.ecommerce.dto.response.WishlistResponse;
import java.util.List;

public interface WishlistService {
    WishlistResponse addToWishlist(Long userId, WishlistRequest request);
    void removeFromWishlist(Long userId, Long wishlistId);
    List<WishlistResponse> getWishlist(Long userId);
}

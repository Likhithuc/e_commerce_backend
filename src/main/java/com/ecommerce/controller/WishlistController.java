package com.ecommerce.controller;

import com.ecommerce.dto.request.WishlistRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.WishlistResponse;
import com.ecommerce.entity.User;
import com.ecommerce.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "Wishlist management APIs")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @Operation(summary = "Add product to wishlist")
    public ResponseEntity<ApiResponse<WishlistResponse>> addToWishlist(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WishlistRequest request) {
        WishlistResponse response = wishlistService.addToWishlist(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product added to wishlist", response));
    }

    @GetMapping
    @Operation(summary = "Get user's wishlist")
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getWishlist(@AuthenticationPrincipal User user) {
        List<WishlistResponse> responses = wishlistService.getWishlist(user.getId());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{wishlistId}")
    @Operation(summary = "Remove product from wishlist")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(
            @AuthenticationPrincipal User user,
            @PathVariable Long wishlistId) {
        wishlistService.removeFromWishlist(user.getId(), wishlistId);
        return ResponseEntity.ok(ApiResponse.success("Product removed from wishlist"));
    }
}

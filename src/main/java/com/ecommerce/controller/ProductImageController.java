package com.ecommerce.controller;

import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Images", description = "Product image management APIs")
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add images to product (Admin)")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> addImages(
            @PathVariable Long productId,
            @RequestParam("files") List<MultipartFile> files) {
        List<ProductImageResponse> responses = productImageService.addImages(productId, files);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Images added successfully", responses));
    }

    @GetMapping("/{productId}/images")
    @Operation(summary = "Get product images")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getProductImages(@PathVariable Long productId) {
        List<ProductImageResponse> responses = productImageService.getProductImages(productId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/images/{imageId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product image (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.ok(ApiResponse.success("Image deleted successfully"));
    }
}

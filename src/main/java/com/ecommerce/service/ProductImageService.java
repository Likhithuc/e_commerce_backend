package com.ecommerce.service;

import com.ecommerce.dto.response.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> addImages(Long productId, List<MultipartFile> files);
    void deleteImage(Long imageId);
    List<ProductImageResponse> getProductImages(Long productId);
}

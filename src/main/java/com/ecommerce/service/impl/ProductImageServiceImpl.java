package com.ecommerce.service.impl;

import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Value("${application.file.upload-dir:uploads/products}")
    private String uploadDir;

    @Override
    @Transactional
    public List<ProductImageResponse> addImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        List<ProductImageResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String relativePath = "/uploads/" + productId + "/" + fileName;

            try {
                Path basePath = Paths.get(System.getProperty("user.dir"), uploadDir);
                Path uploadPath = basePath.resolve(String.valueOf(productId));
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());
                log.info("File saved: {}", filePath);
            } catch (IOException e) {
                log.error("Failed to save file: {}", fileName, e);
                throw new RuntimeException("Failed to upload image: " + fileName, e);
            }

            ProductImage productImage = ProductImage.builder()
                    .imageUrl(relativePath)
                    .product(product)
                    .build();

            productImage = productImageRepository.save(productImage);

            responses.add(ProductImageResponse.builder()
                    .id(productImage.getId())
                    .imageUrl(productImage.getImageUrl())
                    .build());
        }

        return responses;
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductImage", "id", imageId));
        productImageRepository.delete(image);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductImageResponse> getProductImages(Long productId) {
        return productImageRepository.findByProductId(productId)
                .stream()
                .map(img -> ProductImageResponse.builder()
                        .id(img.getId())
                        .imageUrl(img.getImageUrl())
                        .build())
                .toList();
    }
}

package com.ecommerce.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Integer stockQuantity;
    private String brand;
    private Boolean status;
    private Long categoryId;
    private String categoryName;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

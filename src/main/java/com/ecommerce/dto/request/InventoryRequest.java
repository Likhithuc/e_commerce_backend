package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {

    @NotNull(message = "Available quantity is required")
    @PositiveOrZero(message = "Available quantity must be zero or positive")
    private Integer availableQuantity;

    @PositiveOrZero(message = "Reserved quantity must be zero or positive")
    private Integer reservedQuantity;
}

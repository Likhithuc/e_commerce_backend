package com.ecommerce.service;

import com.ecommerce.dto.request.InventoryRequest;
import com.ecommerce.dto.response.InventoryResponse;
import java.util.List;

public interface InventoryService {
    InventoryResponse getInventoryByProductId(Long productId);
    InventoryResponse updateInventory(Long productId, InventoryRequest request);
    List<InventoryResponse> getLowStockItems(Integer threshold);
}

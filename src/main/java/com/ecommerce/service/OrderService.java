package com.ecommerce.service;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse placeOrder(Long userId, OrderRequest request);
    OrderResponse getOrderById(Long userId, Long orderId);
    PageResponse<OrderResponse> getUserOrders(Long userId, Pageable pageable);
    OrderResponse cancelOrder(Long userId, Long orderId);
}

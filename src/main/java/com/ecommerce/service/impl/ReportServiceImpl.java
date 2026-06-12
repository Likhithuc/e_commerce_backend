package com.ecommerce.service.impl;

import com.ecommerce.dto.response.ReportResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.repository.*;
import com.ecommerce.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getSalesReport(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        List<Order> orders = orderRepository.findByDateRange(start, end);
        Double totalRevenue = orderRepository.getTotalRevenueByDateRange(start, end);
        double revenue = totalRevenue != null ? totalRevenue : 0.0;

        Map<String, Object> data = new HashMap<>();
        data.put("totalOrders", (long) orders.size());
        data.put("totalRevenue", revenue);
        data.put("averageOrderValue", orders.isEmpty() ? 0.0 : revenue / orders.size());
        data.put("ordersByStatus", getOrdersByStatusCount(orders));
        data.put("startDate", startDate);
        data.put("endDate", endDate);

        return ReportResponse.builder()
                .reportType("SALES_REPORT")
                .data(data)
                .generatedAt(LocalDateTime.now().toString())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getOrdersReport(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        List<Order> orders = orderRepository.findByDateRange(start, end);

        Map<String, Object> data = new HashMap<>();
        data.put("totalOrders", (long) orders.size());
        data.put("pendingOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count());
        data.put("confirmedOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.CONFIRMED).count());
        data.put("shippedOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.SHIPPED).count());
        data.put("deliveredOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED).count());
        data.put("cancelledOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.CANCELLED).count());
        data.put("startDate", startDate);
        data.put("endDate", endDate);

        return ReportResponse.builder()
                .reportType("ORDER_REPORT")
                .data(data)
                .generatedAt(LocalDateTime.now().toString())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getCustomersReport(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        long totalCustomers = userRepository.countByCreatedAtBetween(start, end);
        long totalOrders = orderRepository.countByCreatedAtBetween(start, end);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCustomers", totalCustomers);
        data.put("totalOrders", totalOrders);
        data.put("averageOrdersPerCustomer", totalCustomers == 0 ? 0.0 : (double) totalOrders / totalCustomers);
        data.put("startDate", startDate);
        data.put("endDate", endDate);

        return ReportResponse.builder()
                .reportType("CUSTOMER_REPORT")
                .data(data)
                .generatedAt(LocalDateTime.now().toString())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getInventoryReport(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        long totalProducts = productRepository.countByCreatedAtBetween(start, end);
        long totalCategories = categoryRepository.count();
        List<?> lowStock = inventoryRepository.findLowStockItems(10);

        Map<String, Object> data = new HashMap<>();
        data.put("totalProducts", totalProducts);
        data.put("totalCategories", totalCategories);
        data.put("lowStockItems", (long) lowStock.size());
        data.put("startDate", startDate);
        data.put("endDate", endDate);

        return ReportResponse.builder()
                .reportType("INVENTORY_REPORT")
                .data(data)
                .generatedAt(LocalDateTime.now().toString())
                .build();
    }

    private Map<String, Long> getOrdersByStatusCount(List<Order> orders) {
        Map<String, Long> statusCount = new HashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            statusCount.put(status.name(), orders.stream()
                    .filter(o -> o.getStatus() == status)
                    .count());
        }
        return statusCount;
    }
}

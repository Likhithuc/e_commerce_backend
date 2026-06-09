package com.ecommerce.controller;

import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.ReportResponse;
import com.ecommerce.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Reports", description = "Admin report APIs")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales")
    @Operation(summary = "Get sales report (Admin)")
    public ResponseEntity<ApiResponse<ReportResponse>> getSalesReport(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusDays(30).toString()}") String startDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String endDate) {
        ReportResponse response = reportService.getSalesReport(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/orders")
    @Operation(summary = "Get orders report (Admin)")
    public ResponseEntity<ApiResponse<ReportResponse>> getOrdersReport(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().minusDays(30).toString()}") String startDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String endDate) {
        ReportResponse response = reportService.getOrdersReport(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/customers")
    @Operation(summary = "Get customers report (Admin)")
    public ResponseEntity<ApiResponse<ReportResponse>> getCustomersReport() {
        ReportResponse response = reportService.getCustomersReport();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/inventory")
    @Operation(summary = "Get inventory report (Admin)")
    public ResponseEntity<ApiResponse<ReportResponse>> getInventoryReport() {
        ReportResponse response = reportService.getInventoryReport();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

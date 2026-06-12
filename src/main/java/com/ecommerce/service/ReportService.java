package com.ecommerce.service;

import com.ecommerce.dto.response.ReportResponse;

public interface ReportService {
    ReportResponse getSalesReport(String startDate, String endDate);
    ReportResponse getOrdersReport(String startDate, String endDate);
    ReportResponse getCustomersReport(String startDate, String endDate);
    ReportResponse getInventoryReport(String startDate, String endDate);
}

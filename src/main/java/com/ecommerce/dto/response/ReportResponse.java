package com.ecommerce.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {
    private String reportType;
    private Map<String, Object> data;
    private String generatedAt;
}

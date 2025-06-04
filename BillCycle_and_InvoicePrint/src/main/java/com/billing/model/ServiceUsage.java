package com.billing.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServiceUsage {
    private String serviceType;
    private BigDecimal totalAmount;
    private Long totalUnits;
} 
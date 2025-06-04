package com.billing.model;

import lombok.Data;
import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class BillDetails {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ServiceUsage> serviceUsages;
    private BigDecimal totalAmount;
} 
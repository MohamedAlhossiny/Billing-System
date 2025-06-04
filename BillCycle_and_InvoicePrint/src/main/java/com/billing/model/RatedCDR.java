package com.billing.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RatedCDR {
    private Long ratedCdrId;
    private Long cdrId;
    private Long ratingRuleId;
    private Long profileId;
    private Long serviceId;
    private BigDecimal ratedAmount;
    private LocalDateTime ratedTime;
} 
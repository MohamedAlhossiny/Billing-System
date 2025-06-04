package com.telecom.billing.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RatedCDR {
    private Long ratedCdrId;
    private Long cdrId;
    private Long ratingRuleId;
    private Long profileId;
    private Long serviceId;
    private BigDecimal ratedAmount;
    private LocalDateTime ratedTime;
} 
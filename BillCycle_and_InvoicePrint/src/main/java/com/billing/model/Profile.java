package com.billing.model;

import lombok.Data;

@Data
public class Profile {
    private Long profileId;
    private Long customerId;
    private Long ratePlanId;
    private Long servicePackageId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String ratePlanName;
    private String packageName;
} 
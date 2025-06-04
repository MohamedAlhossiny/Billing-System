package com.example.billingapi.dto;

import com.example.billingapi.model.RatePlan;

import java.util.List;

public class RatePlanDetails {
    private RatePlan ratePlan;
    private List<ServicePackageDetails> servicePackageDetails;

    public RatePlanDetails() {
    }

    public RatePlanDetails(RatePlan ratePlan, List<ServicePackageDetails> servicePackageDetails) {
        this.ratePlan = ratePlan;
        this.servicePackageDetails = servicePackageDetails;
    }

    public RatePlan getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlan ratePlan) {
        this.ratePlan = ratePlan;
    }

    public List<ServicePackageDetails> getServicePackageDetails() {
        return servicePackageDetails;
    }

    public void setServicePackageDetails(List<ServicePackageDetails> servicePackageDetails) {
        this.servicePackageDetails = servicePackageDetails;
    }
} 
package com.example.billingapi.model;

import java.math.BigDecimal;

public class RatePlan {
    private int rateplanId;
    private String planName;
    private String description;
    private BigDecimal monthlyFee;

    // Constructors
    public RatePlan() {
    }

    public RatePlan(int rateplanId, String planName, String description, BigDecimal monthlyFee) {
        this.rateplanId = rateplanId;
        this.planName = planName;
        this.description = description;
        this.monthlyFee = monthlyFee;
    }

    // Getters and Setters
    public int getRateplanId() {
        return rateplanId;
    }

    public void setRateplanId(int rateplanId) {
        this.rateplanId = rateplanId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public String toString() {
        return "RatePlan{" +
               "rateplanId=" + rateplanId +
               ", planName='" + planName + '\'' +
               ", description='" + description + '\'' +
               ", monthlyFee=" + monthlyFee +
               '}';
    }
} 
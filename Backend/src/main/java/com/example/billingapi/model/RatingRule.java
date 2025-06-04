package com.example.billingapi.model;

import java.math.BigDecimal;

public class RatingRule {
    private int ratingRuleId;
    private int serviceId;
    private int destinationZoneId;
    private BigDecimal pricePerUnit;

    // Constructors
    public RatingRule() {
    }

    public RatingRule(int ratingRuleId, int serviceId, int destinationZoneId, BigDecimal pricePerUnit) {
        this.ratingRuleId = ratingRuleId;
        this.serviceId = serviceId;
        this.destinationZoneId = destinationZoneId;
        this.pricePerUnit = pricePerUnit;
    }

    // Getters and Setters
    public int getRatingRuleId() {
        return ratingRuleId;
    }

    public void setRatingRuleId(int ratingRuleId) {
        this.ratingRuleId = ratingRuleId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getDestinationZoneId() {
        return destinationZoneId;
    }

    public void setDestinationZoneId(int destinationZoneId) {
        this.destinationZoneId = destinationZoneId;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return "RatingRule{" +
               "ratingRuleId=" + ratingRuleId +
               ", serviceId=" + serviceId +
               ", destinationZoneId=" + destinationZoneId +
               ", pricePerUnit=" + pricePerUnit +
               '}';
    }
} 
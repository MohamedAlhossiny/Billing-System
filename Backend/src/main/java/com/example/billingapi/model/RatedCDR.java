package com.example.billingapi.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RatedCDR {
    private int ratedCdrId;
    private int cdrId;
    private int ratingRuleId;
    private int profileId;
    private int serviceId;
    private BigDecimal ratedAmount;
    private Timestamp ratedTime;

    // Constructors
    public RatedCDR() {
    }

    public RatedCDR(int ratedCdrId, int cdrId, int ratingRuleId, int profileId, int serviceId, BigDecimal ratedAmount, Timestamp ratedTime) {
        this.ratedCdrId = ratedCdrId;
        this.cdrId = cdrId;
        this.ratingRuleId = ratingRuleId;
        this.profileId = profileId;
        this.serviceId = serviceId;
        this.ratedAmount = ratedAmount;
        this.ratedTime = ratedTime;
    }

    // Getters and Setters
    public int getRatedCdrId() {
        return ratedCdrId;
    }

    public void setRatedCdrId(int ratedCdrId) {
        this.ratedCdrId = ratedCdrId;
    }

    public int getCdrId() {
        return cdrId;
    }

    public void setCdrId(int cdrId) {
        this.cdrId = cdrId;
    }

    public int getRatingRuleId() {
        return ratingRuleId;
    }

    public void setRatingRuleId(int ratingRuleId) {
        this.ratingRuleId = ratingRuleId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getRatedAmount() {
        return ratedAmount;
    }

    public void setRatedAmount(BigDecimal ratedAmount) {
        this.ratedAmount = ratedAmount;
    }

    public Timestamp getRatedTime() {
        return ratedTime;
    }

    public void setRatedTime(Timestamp ratedTime) {
        this.ratedTime = ratedTime;
    }

    @Override
    public String toString() {
        return "RatedCDR{" +
               "ratedCdrId=" + ratedCdrId +
               ", cdrId=" + cdrId +
               ", ratingRuleId=" + ratingRuleId +
               ", profileId=" + profileId +
               ", serviceId=" + serviceId +
               ", ratedAmount=" + ratedAmount +
               ", ratedTime=" + ratedTime +
               '}';
    }
} 
package com.example.billingapi.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Bill {
    private int billId;
    private int profileId;
    private int servicePackageId;
    private Date billingPeriodStart;
    private Date billingPeriodEnd;
    private BigDecimal totalAmount;
    private Timestamp generatedDate;

    // Constructors
    public Bill() {
    }

    public Bill(int billId, int profileId, int servicePackageId, Date billingPeriodStart, Date billingPeriodEnd, BigDecimal totalAmount, Timestamp generatedDate) {
        this.billId = billId;
        this.profileId = profileId;
        this.servicePackageId = servicePackageId;
        this.billingPeriodStart = billingPeriodStart;
        this.billingPeriodEnd = billingPeriodEnd;
        this.totalAmount = totalAmount;
        this.generatedDate = generatedDate;
    }

    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getServicePackageId() {
        return servicePackageId;
    }

    public void setServicePackageId(int servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    public Date getBillingPeriodStart() {
        return billingPeriodStart;
    }

    public void setBillingPeriodStart(Date billingPeriodStart) {
        this.billingPeriodStart = billingPeriodStart;
    }

    public Date getBillingPeriodEnd() {
        return billingPeriodEnd;
    }

    public void setBillingPeriodEnd(Date billingPeriodEnd) {
        this.billingPeriodEnd = billingPeriodEnd;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Timestamp generatedDate) {
        this.generatedDate = generatedDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
               "billId=" + billId +
               ", profileId=" + profileId +
               ", servicePackageId=" + servicePackageId +
               ", billingPeriodStart=" + billingPeriodStart +
               ", billingPeriodEnd=" + billingPeriodEnd +
               ", totalAmount=" + totalAmount +
               ", generatedDate=" + generatedDate +
               '}';
    }
} 
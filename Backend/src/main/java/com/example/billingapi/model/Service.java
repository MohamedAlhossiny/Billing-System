package com.example.billingapi.model;

import java.math.BigDecimal;

public class Service {
    private int serviceId;
    private int packageId;
    private String serviceType;
    private BigDecimal pricePerUnit;
    private long freeUnits;

    // Constructors
    public Service() {
    }

    public Service(int serviceId, int packageId, String serviceType, BigDecimal pricePerUnit, long freeUnits) {
        this.serviceId = serviceId;
        this.packageId = packageId;
        this.serviceType = serviceType;
        this.pricePerUnit = pricePerUnit;
        this.freeUnits = freeUnits;
    }

    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public long getFreeUnits() {
        return freeUnits;
    }

    public void setFreeUnits(long freeUnits) {
        this.freeUnits = freeUnits;
    }

    @Override
    public String toString() {
        return "Service{" +
               "serviceId=" + serviceId +
               ", packageId=" + packageId +
               ", serviceType='" + serviceType + '\'' +
               ", pricePerUnit=" + pricePerUnit +
               ", freeUnits=" + freeUnits +
               '}';
    }
} 
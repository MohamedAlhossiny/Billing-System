package com.example.billingapi.model;

public class ServicePackage {
    private int packageId;
    private int rateplanId;
    private String packageName;
    private String description;

    // Constructors
    public ServicePackage() {
    }

    public ServicePackage(int packageId, int rateplanId, String packageName, String description) {
        this.packageId = packageId;
        this.rateplanId = rateplanId;
        this.packageName = packageName;
        this.description = description;
    }

    // Getters and Setters
    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getRateplanId() {
        return rateplanId;
    }

    public void setRateplanId(int rateplanId) {
        this.rateplanId = rateplanId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServicePackage{" +
               "packageId=" + packageId +
               ", rateplanId=" + rateplanId +
               ", packageName='" + packageName + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
} 
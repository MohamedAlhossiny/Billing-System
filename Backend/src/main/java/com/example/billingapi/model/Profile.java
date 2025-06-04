package com.example.billingapi.model;

import java.sql.Date;

public class Profile {
    private int profileId;
    private int customerId;
    private int rateplanId;
    private Date activationDate;
    private String status;

    // Constructors
    public Profile() {
    }

    public Profile(int profileId, int customerId, int rateplanId, Date activationDate, String status) {
        this.profileId = profileId;
        this.customerId = customerId;
        this.rateplanId = rateplanId;
        this.activationDate = activationDate;
        this.status = status;
    }

    // Getters and Setters
    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRateplanId() {
        return rateplanId;
    }

    public void setRateplanId(int rateplanId) {
        this.rateplanId = rateplanId;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Profile{" +
               "profileId=" + profileId +
               ", customerId=" + customerId +
               ", rateplanId=" + rateplanId +
               ", activationDate=" + activationDate +
               ", status='" + status + '\'' +
               '}';
    }
} 
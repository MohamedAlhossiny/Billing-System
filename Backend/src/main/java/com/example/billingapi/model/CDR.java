package com.example.billingapi.model;

import java.sql.Timestamp;

public class CDR {
    private int cdrId;
    private int profileId;
    private String dialA;
    private String dialB;
    private String serviceType;
    private long durationVolume;
    private Timestamp startTime;

    // Constructors
    public CDR() {
    }

    public CDR(int cdrId, int profileId, String dialA, String dialB, String serviceType, long durationVolume, Timestamp startTime) {
        this.cdrId = cdrId;
        this.profileId = profileId;
        this.dialA = dialA;
        this.dialB = dialB;
        this.serviceType = serviceType;
        this.durationVolume = durationVolume;
        this.startTime = startTime;
    }

    // Getters and Setters
    public int getCdrId() {
        return cdrId;
    }

    public void setCdrId(int cdrId) {
        this.cdrId = cdrId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getDialA() {
        return dialA;
    }

    public void setDialA(String dialA) {
        this.dialA = dialA;
    }

    public String getDialB() {
        return dialB;
    }

    public void setDialB(String dialB) {
        this.dialB = dialB;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public long getDurationVolume() {
        return durationVolume;
    }

    public void setDurationVolume(long durationVolume) {
        this.durationVolume = durationVolume;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "CDR{" +
               "cdrId=" + cdrId +
               ", profileId=" + profileId +
               ", dialA='" + dialA + '\'' +
               ", dialB='" + dialB + '\'' +
               ", serviceType='" + serviceType + '\'' +
               ", durationVolume=" + durationVolume +
               ", startTime=" + startTime +
               '}';
    }
} 
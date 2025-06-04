package com.example.billingapi.model;

public class DestinationZone {
    private int destinationZoneId;
    private String zoneName;
    private String countryCode;

    // Constructors
    public DestinationZone() {
    }

    public DestinationZone(int destinationZoneId, String zoneName, String countryCode) {
        this.destinationZoneId = destinationZoneId;
        this.zoneName = zoneName;
        this.countryCode = countryCode;
    }

    // Getters and Setters
    public int getDestinationZoneId() {
        return destinationZoneId;
    }

    public void setDestinationZoneId(int destinationZoneId) {
        this.destinationZoneId = destinationZoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "DestinationZone{" +
               "destinationZoneId=" + destinationZoneId +
               ", zoneName='" + zoneName + '\'' +
               ", countryCode='" + countryCode + '\'' +
               '}';
    }
} 
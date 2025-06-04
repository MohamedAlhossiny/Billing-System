package com.example.billingapi.dto;

import com.example.billingapi.model.Service;
import com.example.billingapi.model.ServicePackage;

import java.util.List;

public class ServicePackageDetails {
    private ServicePackage servicePackage;
    private List<Service> services;

    public ServicePackageDetails() {
    }

    public ServicePackageDetails(ServicePackage servicePackage, List<Service> services) {
        this.servicePackage = servicePackage;
        this.services = services;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
} 
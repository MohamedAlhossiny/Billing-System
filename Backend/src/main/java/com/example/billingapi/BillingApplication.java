package com.example.billingapi;

import com.example.billingapi.resource.CustomerResource;
import com.example.billingapi.resource.RatePlanResource;
import com.example.billingapi.resource.ServicePackageResource;
import com.example.billingapi.resource.ServiceResource;
import com.example.billingapi.resource.InvoiceResource;
import com.example.billingapi.resource.CurrentAssignmentResource;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class BillingApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Register REST resources
        classes.add(CustomerResource.class);
        classes.add(RatePlanResource.class);
        classes.add(ServicePackageResource.class);
        classes.add(ServiceResource.class);
        classes.add(InvoiceResource.class);
        classes.add(CurrentAssignmentResource.class);
        return classes;
    }
} 
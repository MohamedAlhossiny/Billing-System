package com.billing;

import com.billing.service.BillGenerationService;
import com.billing.service.InvoiceGenerationService;
import com.billing.model.ServiceUsage;
import com.billing.model.Profile;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize services
            BillGenerationService billService = new BillGenerationService();
            InvoiceGenerationService invoiceService = new InvoiceGenerationService();
            
            // Create invoices directory if it doesn't exist
            File invoicesDir = new File("invoices");
            if (!invoicesDir.exists()) {
                invoicesDir.mkdir();
            }
            
            // Get all active profiles
            List<Profile> profiles = billService.getAllActiveProfiles();
            if (profiles.isEmpty()) {
                System.out.println("No active profiles found");
                return;
            }
            
            // Set billing period
            LocalDate startDate = LocalDate.now().minusMonths(1);
            LocalDate endDate = LocalDate.now();
            
            // Process each profile
            for (Profile profile : profiles) {
                try {
                    System.out.println("Processing profile for customer: " + profile.getCustomerName());
                    
                    // Generate bill
                    Map<String, ServiceUsage> serviceUsages = 
                        billService.generateBill(profile.getProfileId(), startDate, endDate);
                    
                    if (serviceUsages.isEmpty()) {
                        System.out.println("No service usage found for customer: " + profile.getCustomerName());
                        continue;
                    }
                    
                    // Save bill and get the generated bill ID
                    Long billId = billService.saveBill(
                        profile.getProfileId(), 
                        profile.getServicePackageId(), 
                        startDate, 
                        endDate, 
                        serviceUsages
                    );
                    
                    // Generate invoice PDF
                    String outputPath = String.format("invoices/invoice_%s_%s_%s.pdf",
                        profile.getCustomerName().replaceAll("\\s+", "_"),
                        profile.getProfileId(),
                        endDate);
                    
                    String invoicePath = invoiceService.generateInvoice(billId, outputPath);
                    System.out.println("Invoice generated successfully at: " + invoicePath);
                    
                } catch (Exception e) {
                    System.err.println("Error processing profile for customer: " + profile.getCustomerName());
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            System.out.println("Invoice generation completed for all profiles");
            
        } catch (Exception e) {
            System.err.println("Error processing bills/invoices: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 
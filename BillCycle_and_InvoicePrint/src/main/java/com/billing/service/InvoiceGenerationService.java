package com.billing.service;

import com.billing.config.DatabaseConfig;
import com.billing.model.BillDetails;
import com.billing.model.ServiceUsage;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.math.BigDecimal;

public class InvoiceGenerationService {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    
    public String generateInvoice(Long billId, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            
            // Get bill details
            BillDetails billDetails = getBillDetails(billId);
            if (billDetails == null) {
                throw new RuntimeException("No bill details found for bill ID: " + billId);
            }
            
            // Add header
            addHeader(document, billDetails);
            
            // Add customer details
            addCustomerDetails(document, billDetails);
            
            // Add service usage table
            // addServiceUsageTable(document, billDetails);
            
            // Add total amount
            addTotalAmount(document, billDetails);
            
            document.close();
            
            // Save invoice record
            saveInvoiceRecord(billId, outputPath);
            
            return outputPath;
        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice", e);
        }
    }
    
    private void addHeader(Document document, BillDetails billDetails) throws DocumentException {
        Paragraph title = new Paragraph("Telecom Bill Invoice", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        LocalDate startDate = billDetails.getStartDate();
        LocalDate endDate = billDetails.getEndDate();
        
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Billing period dates are missing");
        }
        
        Paragraph billingPeriod = new Paragraph(
            String.format("Billing Period: %s to %s", 
                startDate.format(DateTimeFormatter.ISO_DATE),
                endDate.format(DateTimeFormatter.ISO_DATE)),
            NORMAL_FONT);
        billingPeriod.setAlignment(Element.ALIGN_CENTER);
        billingPeriod.setSpacingAfter(20);
        document.add(billingPeriod);
    }
    
    private void addCustomerDetails(Document document, BillDetails billDetails) throws DocumentException {
        String customerName = billDetails.getCustomerName();
        String customerEmail = billDetails.getCustomerEmail();
        String customerPhone = billDetails.getCustomerPhone();
        String customerAddress = billDetails.getCustomerAddress();
        
        if (customerName == null || customerEmail == null || customerPhone == null || customerAddress == null) {
            throw new RuntimeException("Customer details are incomplete");
        }
        
        Paragraph customerInfo = new Paragraph("Customer Information", HEADER_FONT);
        customerInfo.setSpacingBefore(20);
        customerInfo.setSpacingAfter(10);
        document.add(customerInfo);
        
        Paragraph details = new Paragraph(
            String.format("Name: %s\nEmail: %s\nPhone: %s\nAddress: %s",
                customerName, customerEmail, customerPhone, customerAddress),
            NORMAL_FONT);
        details.setSpacingAfter(20);
        document.add(details);
    }
    
    private void addServiceUsageTable(Document document, BillDetails billDetails) throws DocumentException {
        java.util.List<ServiceUsage> serviceUsages = billDetails.getServiceUsages();
        if (serviceUsages == null || serviceUsages.isEmpty()) {
            throw new RuntimeException("No service usage details found");
        }
        
        Paragraph usageTitle = new Paragraph("Service Usage Details", HEADER_FONT);
        usageTitle.setSpacingBefore(20);
        usageTitle.setSpacingAfter(10);
        document.add(usageTitle);
        
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        table.addCell(new PdfPCell(new Phrase("Service Type", HEADER_FONT)));
        table.addCell(new PdfPCell(new Phrase("Units Used", HEADER_FONT)));
        table.addCell(new PdfPCell(new Phrase("Amount", HEADER_FONT)));

        // Add service usage details
        for (ServiceUsage usage : serviceUsages) {
            if (usage.getServiceType() == null || usage.getTotalUnits() == null || usage.getTotalAmount() == null) {
                throw new RuntimeException("Service usage details are incomplete");
            }
            
            table.addCell(new PdfPCell(new Phrase(usage.getServiceType(), NORMAL_FONT)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(usage.getTotalUnits()), NORMAL_FONT)));
            table.addCell(new PdfPCell(new Phrase(String.format("$%.2f", usage.getTotalAmount()), NORMAL_FONT)));
        }

        document.add(table);
    }
    
    private void addTotalAmount(Document document, BillDetails billDetails) throws DocumentException {
        BigDecimal totalAmount = billDetails.getTotalAmount();
        if (totalAmount == null) {
            throw new RuntimeException("Total amount is missing");
        }
        
        Paragraph total = new Paragraph(
            String.format("Total Amount: $%.2f", totalAmount),
            HEADER_FONT);
        total.setSpacingBefore(20);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);
    }
    
    private BillDetails getBillDetails(Long billId) throws SQLException {
        String sql = "SELECT b.*, c.full_name, c.email, c.phone_number, c.address, " +
                    "s.service_type, SUM(rcdr.rated_amount) as total_amount, " +
                    "SUM(cdr.duration_volume) as total_units " +
                    "FROM Bills b " +
                    "JOIN Profiles p ON b.profile_id = p.profile_id " +
                    "JOIN Customers c ON p.customer_id = c.customer_id " +
                    "JOIN RatedCDRs rcdr ON rcdr.profile_id = p.profile_id " +
                    "JOIN CDRs cdr ON rcdr.cdr_id = cdr.cdr_id " +
                    "JOIN Services s ON rcdr.service_id = s.service_id " +
                    "WHERE b.bill_id = ? " +
                    "AND cdr.start_time BETWEEN b.billing_period_start AND b.billing_period_end " +
                    "GROUP BY s.service_type";
            
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, billId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                
                BillDetails details = new BillDetails();
                Map<String, ServiceUsage> serviceUsages = new HashMap<>();
                
                do {
                    if (details.getCustomerName() == null) {
                        details.setCustomerName(rs.getString("full_name"));
                        details.setCustomerEmail(rs.getString("email"));
                        details.setCustomerPhone(rs.getString("phone_number"));
                        details.setCustomerAddress(rs.getString("address"));
                        details.setStartDate(rs.getDate("billing_period_start").toLocalDate());
                        details.setEndDate(rs.getDate("billing_period_end").toLocalDate());
                        details.setTotalAmount(rs.getBigDecimal("total_amount"));
                    }
                    
                    String serviceType = rs.getString("service_type");
                    ServiceUsage usage = new ServiceUsage();
                    usage.setServiceType(serviceType);
                    usage.setTotalAmount(rs.getBigDecimal("total_amount"));
                    usage.setTotalUnits(rs.getLong("total_units"));
                    serviceUsages.put(serviceType, usage);
                } while (rs.next());
                
                details.setServiceUsages(new ArrayList<>(serviceUsages.values()));
                return details;
            }
        }
    }
    
    private void saveInvoiceRecord(Long billId, String pdfPath) throws SQLException {
        String sql = "INSERT INTO Invoices (bill_id, pdf_path, due_date, payment_status) " +
                    "VALUES (?, ?, DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY), 'unpaid')";
            
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, billId);
            pstmt.setString(2, pdfPath);
            pstmt.executeUpdate();
        }
    }
} 
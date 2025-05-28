package db;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class InvoiceDataTransfer {

    public static void transferDataToCustomerInvoice() {
        String selectQuery = """
            SELECT 
                dial_a as customer_msisdn,
                service_type,
                SUM(volume) as total_volume,
                SUM(total) as total_charges,
                CURRENT_DATE as invoice_date
            FROM rated_cdrs
            GROUP BY dial_a, service_type""";

        String insertQuery = """
            INSERT INTO customer_invoice 
            (customer_msisdn, service_type, total_volume, total_charges, invoice_date)
            VALUES (?, ?, ?, ?, ?)""";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // Begin transaction
            conn.setAutoCommit(false);
            
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                ResultSet rs = selectStmt.executeQuery();
                int count = 0;

                while (rs.next()) {
                    insertStmt.setString(1, rs.getString("customer_msisdn"));
                    insertStmt.setString(2, rs.getString("service_type"));
                    insertStmt.setInt(3, rs.getInt("total_volume"));
                    insertStmt.setBigDecimal(4, rs.getBigDecimal("total_charges"));
                    insertStmt.setDate(5, rs.getDate("invoice_date"));

                    count += insertStmt.executeUpdate();
                }
                
                // Commit the transaction
                conn.commit();
                System.out.println("Customer invoice data transfer completed! " + count + " records inserted.");
            } catch (SQLException e) {
                // Rollback in case of error
                if (conn != null) {
                    conn.rollback();
                }
                System.err.println("Error during customer_invoice transfer: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    

    public static void transferDataToInvoice() {
        String selectQuery = """
            SELECT 
                dial_a as customer_msisdn,
                SUM(total) as total_charges,
                CURRENT_DATE as invoice_date
            FROM rated_cdrs
            GROUP BY dial_a""";

        String insertQuery = """
            INSERT INTO invoice 
            (customer_msisdn, total_charges, invoice_date, pdf_path)
            VALUES (?, ?, ?, ?)""";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // Begin transaction
            conn.setAutoCommit(false);
            
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

                ResultSet rs = selectStmt.executeQuery();
                int count = 0;
                
                while (rs.next()) {
                    String pdfPath = "Invoices/invoice_" + rs.getString("customer_msisdn") + "_" + rs.getDate("invoice_date").toString() + ".pdf";
                    generateInvoicePDF(rs.getString("customer_msisdn"), rs.getBigDecimal("total_charges"), rs.getDate("invoice_date"), pdfPath);
                    
                    insertStmt.setString(1, rs.getString("customer_msisdn"));
                    insertStmt.setBigDecimal(2, rs.getBigDecimal("total_charges"));
                    insertStmt.setDate(3, rs.getDate("invoice_date"));
                    insertStmt.setString(4, pdfPath);
                    
                    count += insertStmt.executeUpdate();
                }
                
                // Commit the transaction
                conn.commit();
                System.out.println("Invoice data transfer completed! " + count + " records inserted.");
            } catch (SQLException e) {
                // Rollback in case of error
                if (conn != null) {
                    conn.rollback();
                }
                System.err.println("Error during invoice transfer: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void generateInvoicePDF(String customer_msisdn, BigDecimal total_charges, Date invoice_date, String pdfPath) {
        // TODO: Implement invoice PDF generation
        // Create PDF file
        // select services usage for the customer
        String selectServicesQuery = """
            SELECT 
                service_type,
                SUM(volume) as total_volume,
                SUM(total) as total_charges
            FROM rated_cdrs
            WHERE dial_a = ?
            GROUP BY service_type""";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            try (PreparedStatement selectStmt = conn.prepareStatement(selectServicesQuery)) {
                selectStmt.setString(1, customer_msisdn);
                ResultSet rs = selectStmt.executeQuery();
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
                    document.open();
                    
                    // Create a table with 3 columns
                    PdfPTable table = new PdfPTable(3);
                    table.setWidthPercentage(80); // Set table width to 80% of page
                    table.setHorizontalAlignment(Element.ALIGN_CENTER); // Center the table
                    
                    // Add table headers
                    table.addCell("Service Type");
                    table.addCell("Total Volume");
                    table.addCell("Total Charges");
                    
                    // Populate table rows
                    while (rs.next()) {
                        table.addCell(rs.getString("service_type"));
                        table.addCell(String.valueOf(rs.getInt("total_volume")));
                        table.addCell(rs.getBigDecimal("total_charges").toString());
                    }
                    
                    // Add customer details and invoice summary
                    Paragraph customerInfo = new Paragraph("Customer MSISDN: " + customer_msisdn);
                    customerInfo.setAlignment(Element.ALIGN_CENTER);
                    document.add(customerInfo);
                    
                    document.add(table);
                    
                    Paragraph totalCharges = new Paragraph("Total charges: " + total_charges);
                    totalCharges.setAlignment(Element.ALIGN_CENTER);
                    document.add(totalCharges);
                    
                    Paragraph invoiceDate = new Paragraph("Invoice date: " + invoice_date);
                    invoiceDate.setAlignment(Element.ALIGN_CENTER);
                    document.add(invoiceDate);
                    
                    document.close();
                } catch (Exception e) {
                    System.err.println("Error generating invoice PDF: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
        
        
    }
}
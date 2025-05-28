package org.billing;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
import db.DatabaseConnection;
import db.InvoiceDataTransfer;

/**
 * Application for transferring billing data to invoice tables
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println("Starting billing data transfer...");
        
        try {
            // Test connection
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Database connection successful!");
            conn.close();

            //create pdf directory
            File pdfDir = new File("Invoices");
            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }
            

            // Transfer data to customer_invoice table
            InvoiceDataTransfer.transferDataToCustomerInvoice();
            
            // Transfer data to invoice table
            InvoiceDataTransfer.transferDataToInvoice();

            
            
            System.out.println("All data transfers completed successfully!");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("Application completed.");
    }
}

package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.Invoice;
import com.example.billingapi.util.DatabaseConnection;

public class InvoiceDAO {

    public List<Invoice> getInvoicesByMobileNumber(String mobileNumber) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT i.* FROM Invoices i JOIN Bills b ON i.bill_id = b.bill_id JOIN Profiles p ON b.profile_id = p.profile_id JOIN Customers c ON p.customer_id = c.customer_id WHERE c.phone_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mobileNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("invoice_id"));
                    invoice.setBillId(rs.getInt("bill_id"));
                    invoice.setPdfPath(rs.getString("pdf_path"));
                    invoice.setInvoiceDate(rs.getTimestamp("invoice_date"));
                    invoice.setDueDate(rs.getDate("due_date"));
                    invoice.setPaymentStatus(rs.getString("payment_status"));
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    // You can add more methods as needed
} 
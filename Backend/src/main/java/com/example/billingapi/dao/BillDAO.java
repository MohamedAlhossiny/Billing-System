package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.Bill;
import com.example.billingapi.util.DatabaseConnection;

public class BillDAO {

    public List<Bill> getBillsByProfileId(int profileId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bills WHERE profile_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profileId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setProfileId(rs.getInt("profile_id"));
                    bill.setServicePackageId(rs.getInt("service_package_id"));
                    bill.setBillingPeriodStart(rs.getDate("billing_period_start"));
                    bill.setBillingPeriodEnd(rs.getDate("billing_period_end"));
                    bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                    bill.setGeneratedDate(rs.getTimestamp("generated_date"));
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    // You can add more methods as needed
} 
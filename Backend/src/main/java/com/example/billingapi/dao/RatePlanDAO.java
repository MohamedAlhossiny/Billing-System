package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.RatePlan;
import com.example.billingapi.util.DatabaseConnection;

public class RatePlanDAO {

    public void addRatePlan(RatePlan ratePlan) {
        String sql = "INSERT INTO RatePlans (plan_name, description, monthly_fee) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, ratePlan.getPlanName());
            pstmt.setString(2, ratePlan.getDescription());
            pstmt.setBigDecimal(3, ratePlan.getMonthlyFee());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ratePlan.setRateplanId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RatePlan> getAllRatePlans() {
        List<RatePlan> ratePlans = new ArrayList<>();
        String sql = "SELECT * FROM RatePlans";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RatePlan ratePlan = new RatePlan();
                ratePlan.setRateplanId(rs.getInt("rateplan_id"));
                ratePlan.setPlanName(rs.getString("plan_name"));
                ratePlan.setDescription(rs.getString("description"));
                ratePlan.setMonthlyFee(rs.getBigDecimal("monthly_fee"));
                ratePlans.add(ratePlan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratePlans;
    }
} 
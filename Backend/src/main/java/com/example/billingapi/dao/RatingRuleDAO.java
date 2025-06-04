package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.RatingRule;
import com.example.billingapi.util.DatabaseConnection;

public class RatingRuleDAO {

    public RatingRule getRatingRuleById(int ratingRuleId) {
        RatingRule ratingRule = null;
        String sql = "SELECT * FROM RatingRules WHERE rating_rule_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ratingRuleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ratingRule = new RatingRule();
                    ratingRule.setRatingRuleId(rs.getInt("rating_rule_id"));
                    ratingRule.setServiceId(rs.getInt("service_id"));
                    ratingRule.setDestinationZoneId(rs.getInt("destination_zone_id"));
                    ratingRule.setPricePerUnit(rs.getBigDecimal("price_per_unit"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratingRule;
    }

    public List<RatingRule> getRatingRulesByServiceId(int serviceId) {
        List<RatingRule> ratingRules = new ArrayList<>();
        String sql = "SELECT * FROM RatingRules WHERE service_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, serviceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RatingRule ratingRule = new RatingRule();
                    ratingRule.setRatingRuleId(rs.getInt("rating_rule_id"));
                    ratingRule.setServiceId(rs.getInt("service_id"));
                    ratingRule.setDestinationZoneId(rs.getInt("destination_zone_id"));
                    ratingRule.setPricePerUnit(rs.getBigDecimal("price_per_unit"));
                    ratingRules.add(ratingRule);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratingRules;
    }

    // You can add more methods as needed
} 
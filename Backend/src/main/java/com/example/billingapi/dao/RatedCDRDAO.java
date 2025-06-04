package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.RatedCDR;
import com.example.billingapi.util.DatabaseConnection;

public class RatedCDRDAO {

    public List<RatedCDR> getRatedCDRsByProfileId(int profileId) {
        List<RatedCDR> ratedCDRs = new ArrayList<>();
        String sql = "SELECT * FROM RatedCDRs WHERE profile_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profileId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RatedCDR ratedCDR = new RatedCDR();
                    ratedCDR.setRatedCdrId(rs.getInt("rated_cdr_id"));
                    ratedCDR.setCdrId(rs.getInt("cdr_id"));
                    ratedCDR.setRatingRuleId(rs.getInt("rating_rule_id"));
                    ratedCDR.setProfileId(rs.getInt("profile_id"));
                    ratedCDR.setServiceId(rs.getInt("service_id"));
                    ratedCDR.setRatedAmount(rs.getBigDecimal("rated_amount"));
                    ratedCDR.setRatedTime(rs.getTimestamp("rated_time"));
                    ratedCDRs.add(ratedCDR);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratedCDRs;
    }

    // You can add more methods as needed
} 
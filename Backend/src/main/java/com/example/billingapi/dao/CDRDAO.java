package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.CDR;
import com.example.billingapi.util.DatabaseConnection;

public class CDRDAO {

    public List<CDR> getCDRsByProfileId(int profileId) {
        List<CDR> cdrs = new ArrayList<>();
        String sql = "SELECT * FROM CDRs WHERE profile_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profileId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CDR cdr = new CDR();
                    cdr.setCdrId(rs.getInt("cdr_id"));
                    cdr.setProfileId(rs.getInt("profile_id"));
                    cdr.setDialA(rs.getString("dial_a"));
                    cdr.setDialB(rs.getString("dial_b"));
                    cdr.setServiceType(rs.getString("service_type"));
                    cdr.setDurationVolume(rs.getLong("duration_volume"));
                    cdr.setStartTime(rs.getTimestamp("start_time"));
                    cdrs.add(cdr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdrs;
    }

    // You can add more methods as needed
} 
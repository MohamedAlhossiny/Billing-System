package com.telecom.billing.dao.impl;

import com.telecom.billing.dao.CDRDAO;
import com.telecom.billing.model.CDR;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CDRDAOImpl implements CDRDAO {
    
    @Override
    public void saveCDR(CDR cdr, Connection conn) throws SQLException {
        String sql = "INSERT INTO CDRs (profile_id, dial_a, dial_b, service_type, duration_volume, start_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
                    
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, cdr.getProfileId());
            pstmt.setString(2, cdr.getDialA());
            pstmt.setString(3, cdr.getDialB());
            pstmt.setString(4, cdr.getServiceType().name());
            pstmt.setDouble(5, cdr.getDurationVolume());
            pstmt.setTimestamp(6, Timestamp.valueOf(cdr.getStartTime()));
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cdr.setCdrId(rs.getLong(1));
                }
            }
        }
    }
    
    @Override
    public List<CDR> getUnratedCDRs(Connection conn) throws SQLException {
        List<CDR> cdrs = new ArrayList<>();
        String sql = "SELECT * FROM CDRs WHERE cdr_id NOT IN (SELECT cdr_id FROM RatedCDRs)";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                CDR cdr = new CDR();
                cdr.setCdrId(rs.getLong("cdr_id"));
                cdr.setProfileId(rs.getLong("profile_id"));
                cdr.setDialA(rs.getString("dial_a"));
                cdr.setDialB(rs.getString("dial_b"));
                cdr.setServiceType(CDR.ServiceType.valueOf(rs.getString("service_type")));
                cdr.setDurationVolume(rs.getLong("duration_volume"));
                cdr.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                cdrs.add(cdr);
            }
        }
        
        return cdrs;
    }
    
    @Override
    public boolean cdrExists(CDR cdr, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM CDRs WHERE profile_id = ? AND dial_a = ? AND dial_b = ? AND service_type = ? AND duration_volume = ? AND start_time = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cdr.getProfileId());
            pstmt.setString(2, cdr.getDialA());
            pstmt.setString(3, cdr.getDialB());
            pstmt.setString(4, cdr.getServiceType().name());
            pstmt.setDouble(5, cdr.getDurationVolume());
            pstmt.setTimestamp(6, Timestamp.valueOf(cdr.getStartTime()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if a row is found, false otherwise
            }
        }
    }
} 
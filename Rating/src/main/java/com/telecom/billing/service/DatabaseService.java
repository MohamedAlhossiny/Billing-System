package com.telecom.billing.service;

import com.telecom.billing.config.DatabaseConfig;
import com.telecom.billing.dao.CDRDAO;
import com.telecom.billing.dao.RatedCDRDAO;
import com.telecom.billing.dao.impl.CDRDAOImpl;
import com.telecom.billing.dao.impl.RatedCDRDAOImpl;
import com.telecom.billing.model.CDR;
import com.telecom.billing.model.RatedCDR;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DatabaseService {
    
    private final CDRDAO cdrDAO;
    private final RatedCDRDAO ratedCDRDAO;

    public DatabaseService() {
        this.cdrDAO = new CDRDAOImpl();
        this.ratedCDRDAO = new RatedCDRDAOImpl();
    }
    
    public void saveCDR(CDR cdr) throws SQLException {
        String sql = "INSERT INTO CDRs (profile_id, dial_a, dial_b, service_type, duration_volume, start_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
                    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, cdr.getProfileId());
            pstmt.setString(2, cdr.getDialA());
            pstmt.setString(3, cdr.getDialB());
            pstmt.setString(4, cdr.getServiceType().name());
            pstmt.setLong(5, cdr.getDurationVolume());
            pstmt.setTimestamp(6, Timestamp.valueOf(cdr.getStartTime()));
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cdr.setCdrId(rs.getLong(1));
                }
            }
        }
    }
    
    public List<CDR> getUnratedCDRs() throws SQLException {
        String sql = "SELECT c.* FROM CDRs c " +
                    "LEFT JOIN RatedCDRs rc ON c.cdr_id = rc.cdr_id " +
                    "WHERE rc.cdr_id IS NULL";
                    
        List<CDR> cdrs = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
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
    
    public void saveRatedCDR(RatedCDR ratedCDR) throws SQLException {
        String sql = "INSERT INTO RatedCDRs (cdr_id, rating_rule_id, profile_id, service_id, rated_amount, rated_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
                    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, ratedCDR.getCdrId());
            pstmt.setLong(2, ratedCDR.getRatingRuleId());
            pstmt.setLong(3, ratedCDR.getProfileId());
            pstmt.setLong(4, ratedCDR.getServiceId());
            pstmt.setBigDecimal(5, ratedCDR.getRatedAmount());
            pstmt.setTimestamp(6, Timestamp.valueOf(ratedCDR.getRatedTime()));
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ratedCDR.setRatedCdrId(rs.getLong(1));
                }
            }
        }
    }

    public boolean cdrExists(CDR cdr, Connection conn) throws SQLException {
        return cdrDAO.cdrExists(cdr, conn);
    }
} 
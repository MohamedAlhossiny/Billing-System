package com.telecom.billing.dao.impl;

import com.telecom.billing.dao.RatedCDRDAO;
import com.telecom.billing.model.CDR;
import com.telecom.billing.model.RatedCDR;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;

@Slf4j
public class RatedCDRDAOImpl implements RatedCDRDAO {
    
    @Override
    public void saveRatedCDR(RatedCDR ratedCDR, Connection conn) throws SQLException {
        String sql = "INSERT INTO RatedCDRs (cdr_id, rating_rule_id, profile_id, service_id, rated_amount, rated_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
                    
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
    
    @Override
    public RatedCDR getRatingRule(CDR cdr, Connection conn) throws SQLException {
        String sql = "SELECT rr.rating_rule_id, rr.price_per_unit, s.service_id " +
                    "FROM RatingRules rr " +
                    "JOIN Services s ON rr.service_id = s.service_id " +
                    "JOIN ServicePackages sp ON s.package_id = sp.package_id " +
                    "JOIN Profiles p ON sp.rateplan_id = p.rateplan_id " +
                    "WHERE p.profile_id = ? " +
                    "AND s.service_type = ? " +
                    "AND rr.destination_zone_id = (SELECT destination_zone_id FROM DestinationZones WHERE country_code = ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cdr.getProfileId());
            pstmt.setString(2, cdr.getServiceType().name());
            pstmt.setString(3, getCountryCode(cdr.getDialB()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    RatedCDR ratedCDR = new RatedCDR();
                    ratedCDR.setCdrId(cdr.getCdrId());
                    ratedCDR.setProfileId(cdr.getProfileId());
                    ratedCDR.setRatingRuleId(rs.getLong("rating_rule_id"));
                    ratedCDR.setServiceId(rs.getLong("service_id"));
                    
                    // Calculate rated amount
                    double pricePerUnit = rs.getDouble("price_per_unit");
                    double ratedAmount = pricePerUnit * cdr.getDurationVolume().doubleValue(); // Convert Long to double
                    ratedCDR.setRatedAmount(new java.math.BigDecimal(ratedAmount));
                    ratedCDR.setRatedTime(LocalDateTime.now());
                    
                    return ratedCDR;
                } else {
                    log.warn("No rating rule found for CDR: profileId={}, serviceType={}, dialB={}", 
                            cdr.getProfileId(), cdr.getServiceType(), cdr.getDialB());
                }
            }
        }
        
        return null;
    }
    
    private String getCountryCode(String phoneNumber) {
        // Remove the + prefix if present
        String number = phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
        // Get the country code (first two digits after removing +)
        return number.substring(0, 2);
    }
} 
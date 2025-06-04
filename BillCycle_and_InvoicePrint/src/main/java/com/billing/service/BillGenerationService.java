package com.billing.service;

import com.billing.config.DatabaseConfig;
import com.billing.model.RatedCDR;
import com.billing.model.ServiceUsage;
import com.billing.model.Profile;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;

public class BillGenerationService {
    
    public List<Profile> getAllActiveProfiles() {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT p.profile_id, p.customer_id, p.rateplan_id, " +
                    "c.full_name, c.email, c.phone_number, c.address, " +
                    "rp.plan_name, sp.package_id, sp.package_name " +
                    "FROM Profiles p " +
                    "JOIN Customers c ON p.customer_id = c.customer_id " +
                    "JOIN RatePlans rp ON p.rateplan_id = rp.rateplan_id " +
                    "JOIN ServicePackages sp ON rp.rateplan_id = sp.rateplan_id " +
                    "WHERE p.status = 'active'";
            
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Profile profile = new Profile();
                profile.setProfileId(rs.getLong("profile_id"));
                profile.setCustomerId(rs.getLong("customer_id"));
                profile.setRatePlanId(rs.getLong("rateplan_id"));
                profile.setServicePackageId(rs.getLong("package_id"));
                profile.setCustomerName(rs.getString("full_name"));
                profile.setCustomerEmail(rs.getString("email"));
                profile.setCustomerPhone(rs.getString("phone_number"));
                profile.setCustomerAddress(rs.getString("address"));
                profile.setRatePlanName(rs.getString("plan_name"));
                profile.setPackageName(rs.getString("package_name"));
                profiles.add(profile);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting active profiles", e);
        }
        
        return profiles;
    }
    
    public Map<String, ServiceUsage> generateBill(Long profileId, LocalDate startDate, LocalDate endDate) {
        Map<String, ServiceUsage> serviceUsageMap = new HashMap<>();
        
        String sql = "SELECT s.service_type, SUM(rcdr.rated_amount) as total_amount, SUM(cdr.duration_volume) as total_units " +
                    "FROM RatedCDRs rcdr " +
                    "JOIN CDRs cdr ON rcdr.cdr_id = cdr.cdr_id " +
                    "JOIN Services s ON rcdr.service_id = s.service_id " +
                    "WHERE rcdr.profile_id = ? " +
                    "AND cdr.start_time BETWEEN ? AND ? " +
                    "GROUP BY s.service_type";
            
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, profileId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(endDate));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String serviceType = rs.getString("service_type");
                    BigDecimal totalAmount = rs.getBigDecimal("total_amount");
                    Long totalUnits = rs.getLong("total_units");
                    
                    ServiceUsage usage = new ServiceUsage();
                    usage.setServiceType(serviceType);
                    usage.setTotalAmount(totalAmount);
                    usage.setTotalUnits(totalUnits);
                    
                    serviceUsageMap.put(serviceType, usage);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating bill", e);
        }
        
        return serviceUsageMap;
    }
    
    public Long saveBill(Long profileId, Long servicePackageId, LocalDate startDate, LocalDate endDate, 
                        Map<String, ServiceUsage> serviceUsageMap) {
        String sql = "INSERT INTO Bills (profile_id, service_package_id, billing_period_start, " +
                    "billing_period_end, total_amount) " +
                    "VALUES (?, ?, ?, ?, ?)";
            
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            BigDecimal totalAmount = serviceUsageMap.values().stream()
                .map(ServiceUsage::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            pstmt.setLong(1, profileId);
            pstmt.setLong(2, servicePackageId);
            pstmt.setDate(3, java.sql.Date.valueOf(startDate));
            pstmt.setDate(4, java.sql.Date.valueOf(endDate));
            pstmt.setBigDecimal(5, totalAmount);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving bill", e);
        }
    }
} 
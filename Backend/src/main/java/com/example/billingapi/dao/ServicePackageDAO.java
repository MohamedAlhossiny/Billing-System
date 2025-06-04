package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.ServicePackage;
import com.example.billingapi.util.DatabaseConnection;

public class ServicePackageDAO {

    public void addServicePackage(ServicePackage servicePackage) {
        String sql = "INSERT INTO ServicePackages (rateplan_id, package_name, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, servicePackage.getRateplanId());
            pstmt.setString(2, servicePackage.getPackageName());
            pstmt.setString(3, servicePackage.getDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        servicePackage.setPackageId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateServicePackage(ServicePackage servicePackage) {
        String sql = "UPDATE ServicePackages SET rateplan_id = ?, package_name = ?, description = ? WHERE package_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, servicePackage.getRateplanId());
            pstmt.setString(2, servicePackage.getPackageName());
            pstmt.setString(3, servicePackage.getDescription());
            pstmt.setInt(4, servicePackage.getPackageId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ServicePackage getServicePackageById(int packageId) {
        ServicePackage servicePackage = null;
        String sql = "SELECT * FROM ServicePackages WHERE package_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, packageId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    servicePackage = new ServicePackage();
                    servicePackage.setPackageId(rs.getInt("package_id"));
                    servicePackage.setRateplanId(rs.getInt("rateplan_id"));
                    servicePackage.setPackageName(rs.getString("package_name"));
                    servicePackage.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicePackage;
    }

    public List<ServicePackage> getServicePackagesByRatePlanId(int rateplanId) {
        List<ServicePackage> servicePackages = new ArrayList<>();
        String sql = "SELECT * FROM ServicePackages WHERE rateplan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rateplanId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ServicePackage servicePackage = new ServicePackage();
                    servicePackage.setPackageId(rs.getInt("package_id"));
                    servicePackage.setRateplanId(rs.getInt("rateplan_id"));
                    servicePackage.setPackageName(rs.getString("package_name"));
                    servicePackage.setDescription(rs.getString("description"));
                    servicePackages.add(servicePackage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicePackages;
    }
} 
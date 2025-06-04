package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.Service;
import com.example.billingapi.util.DatabaseConnection;

public class ServiceDAO {

    public void addService(Service service) {
        String sql = "INSERT INTO Services (package_id, service_type, price_per_unit, free_units) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, service.getPackageId());
            pstmt.setString(2, service.getServiceType());
            pstmt.setBigDecimal(3, service.getPricePerUnit());
            pstmt.setLong(4, service.getFreeUnits());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        service.setServiceId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateService(Service service) {
        String sql = "UPDATE Services SET package_id = ?, service_type = ?, price_per_unit = ?, free_units = ? WHERE service_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, service.getPackageId());
            pstmt.setString(2, service.getServiceType());
            pstmt.setBigDecimal(3, service.getPricePerUnit());
            pstmt.setLong(4, service.getFreeUnits());
            pstmt.setInt(5, service.getServiceId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Service getServiceById(int serviceId) {
        Service service = null;
        String sql = "SELECT * FROM Services WHERE service_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, serviceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    service = new Service();
                    service.setServiceId(rs.getInt("service_id"));
                    service.setPackageId(rs.getInt("package_id"));
                    service.setServiceType(rs.getString("service_type"));
                    service.setPricePerUnit(rs.getBigDecimal("price_per_unit"));
                    service.setFreeUnits(rs.getLong("free_units"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    public List<Service> getServicesByPackageId(int packageId) {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Services WHERE package_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, packageId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();
                    service.setServiceId(rs.getInt("service_id"));
                    service.setPackageId(rs.getInt("package_id"));
                    service.setServiceType(rs.getString("service_type"));
                    service.setPricePerUnit(rs.getBigDecimal("price_per_unit"));
                    service.setFreeUnits(rs.getLong("free_units"));
                    services.add(service);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
} 
package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.DestinationZone;
import com.example.billingapi.util.DatabaseConnection;

public class DestinationZoneDAO {

    public DestinationZone getDestinationZoneById(int destinationZoneId) {
        DestinationZone destinationZone = null;
        String sql = "SELECT * FROM DestinationZones WHERE destination_zone_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, destinationZoneId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    destinationZone = new DestinationZone();
                    destinationZone.setDestinationZoneId(rs.getInt("destination_zone_id"));
                    destinationZone.setZoneName(rs.getString("zone_name"));
                    destinationZone.setCountryCode(rs.getString("country_code"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinationZone;
    }

    public List<DestinationZone> getAllDestinationZones() {
        List<DestinationZone> destinationZones = new ArrayList<>();
        String sql = "SELECT * FROM DestinationZones";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DestinationZone destinationZone = new DestinationZone();
                destinationZone.setDestinationZoneId(rs.getInt("destination_zone_id"));
                destinationZone.setZoneName(rs.getString("zone_name"));
                destinationZone.setCountryCode(rs.getString("country_code"));
                destinationZones.add(destinationZone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinationZones;
    }

    // You can add more methods as needed
} 
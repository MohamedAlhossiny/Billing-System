package com.example.billingapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.billingapi.model.Profile;
import com.example.billingapi.util.DatabaseConnection;

public class ProfileDAO {

    public Profile getProfileById(int profileId) {
        Profile profile = null;
        String sql = "SELECT * FROM Profiles WHERE profile_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profileId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    profile = new Profile();
                    profile.setProfileId(rs.getInt("profile_id"));
                    profile.setCustomerId(rs.getInt("customer_id"));
                    profile.setRateplanId(rs.getInt("rateplan_id"));
                    profile.setActivationDate(rs.getDate("activation_date"));
                    profile.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public List<Profile> getProfilesByCustomerId(int customerId) {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT * FROM Profiles WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Profile profile = new Profile();
                    profile.setProfileId(rs.getInt("profile_id"));
                    profile.setCustomerId(rs.getInt("customer_id"));
                    profile.setRateplanId(rs.getInt("rateplan_id"));
                    profile.setActivationDate(rs.getDate("activation_date"));
                    profile.setStatus(rs.getString("status"));
                    profiles.add(profile);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT * FROM Profiles";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Profile profile = new Profile();
                profile.setProfileId(rs.getInt("profile_id"));
                profile.setCustomerId(rs.getInt("customer_id"));
                profile.setRateplanId(rs.getInt("rateplan_id"));
                profile.setActivationDate(rs.getDate("activation_date"));
                profile.setStatus(rs.getString("status"));
                profiles.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }

    public void addProfile(Profile profile) {
        String sql = "INSERT INTO Profiles (customer_id, rateplan_id, activation_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, profile.getCustomerId());
            pstmt.setInt(2, profile.getRateplanId());
            pstmt.setDate(3, profile.getActivationDate());
            pstmt.setString(4, profile.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        profile.setProfileId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(Profile profile) {
        String sql = "UPDATE Profiles SET customer_id = ?, rateplan_id = ?, activation_date = ?, status = ? WHERE profile_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profile.getCustomerId());
            pstmt.setInt(2, profile.getRateplanId());
            pstmt.setDate(3, profile.getActivationDate());
            pstmt.setString(4, profile.getStatus());
            pstmt.setInt(5, profile.getProfileId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProfile(int profileId) {
        String sql = "DELETE FROM Profiles WHERE profile_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profileId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // You can add more methods as needed for your API endpoints
} 
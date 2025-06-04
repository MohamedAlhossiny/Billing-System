package com.example.billingapi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/billing?serverTimezone=UTC"; // TODO: Configure database URL
    private static final String USER = "root"; // TODO: Configure database username
    private static final String PASSWORD = "root"; // TODO: Configure database password

    public static Connection getConnection() throws SQLException {
        // Ensure the JDBC driver is loaded
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // You might want to add methods for connection pooling in a production environment
} 
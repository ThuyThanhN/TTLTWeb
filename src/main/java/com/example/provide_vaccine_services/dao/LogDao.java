package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.Service.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDao {

    private static final String INSERT_LOG_SQL =
            "INSERT INTO logs (log_level, log_message, user_name, user_ip, timestamp) VALUES (?, ?, ?, ?, ?)";

    public void insertLog(String logLevel, String logMessage, String userName, String userIp) throws SQLException {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_LOG_SQL)) {

            stmt.setString(1, logLevel);
            stmt.setString(2, logMessage);
            stmt.setString(3, userName);
            stmt.setString(4, userIp);
            stmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
        }
    }
}
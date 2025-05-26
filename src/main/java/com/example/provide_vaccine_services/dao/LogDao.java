package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.Service.DBUtils;
import com.example.provide_vaccine_services.dao.model.logs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<logs> getAll() {
        List<logs> logsList = new ArrayList<>();
        String sql = "SELECT * FROM logs ORDER BY timestamp DESC";

        try {
            PreparedStatement pst = DBUtils.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String logLevel = rs.getString("log_level");
                String logMessage = rs.getString("log_message");
                String userName = rs.getString("user_name");
                String userIp = rs.getString("user_ip");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                logs log = new logs(id, logLevel, logMessage, userName, userIp, timestamp);
                logsList.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logsList;
    }

}
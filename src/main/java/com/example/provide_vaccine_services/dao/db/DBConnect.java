package com.example.provide_vaccine_services.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnect {
    static String url = "jdbc:mysql://" + DbProperties.host() + ":" + DbProperties.port() + "/" + DbProperties.dbname() + "?" + DbProperties.option();
    static Connection conn = null;

    public static PreparedStatement get(String sql) {
        try {
            if (conn == null || conn.isClosed()) {
                makeConnect();
            }

            return conn.prepareStatement(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement getAuto(String sql) {
        try {
            if (conn == null || conn.isClosed()) {
                makeConnect();
            }

            return conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void makeConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, DbProperties.username(), DbProperties.password());
    }

    public static void main(String[] args) {
        System.out.println(DbProperties.dbname());
    }
}

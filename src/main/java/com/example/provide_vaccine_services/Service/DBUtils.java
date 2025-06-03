package com.example.provide_vaccine_services.Service;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    private static final String PROPERTIES_FILE = "/db.properties";

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = DBUtils.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file " + PROPERTIES_FILE);
            }
            prop.load(input);

            String host = prop.getProperty("db.host");
            String port = prop.getProperty("db.port");
            String dbname = prop.getProperty("db.dbname");
            String options = prop.getProperty("db.option", "");

            url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?" + options;
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

            // Đăng ký driver (nếu dùng Java cũ, mới thì tự load)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi load cấu hình DB", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    public static PreparedStatement get(String sql) throws SQLException {
        Connection conn = getConnection();
        return conn.prepareStatement(sql);
    }
}
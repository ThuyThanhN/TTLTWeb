package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionDao {
    // Kiểm tra xem người dùng có userId có được cấp quyền (READ, WRITE, EXECUTE) không?
    public boolean hasPermission(int userId, String module, int requiredPermission) {
        try {
            String sql = "SELECT p.permission FROM permissions p " +
                    "JOIN userpermissions up ON p.id = up.permissionId " +
                    "WHERE up.userId = ? AND p.module = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, userId);
            pst.setString(2, module);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int userPermission = rs.getInt("permission");
                return (userPermission & requiredPermission) == requiredPermission;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Permission DENIED");
        return false;
    }
}

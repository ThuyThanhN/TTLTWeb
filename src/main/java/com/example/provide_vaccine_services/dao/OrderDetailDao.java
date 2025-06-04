package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.OrderDetails;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao {
    public int insertDetailFull(int orderId, int vaccineId, int packageId, int quantity, float price) {
        int result = 0;
        try {
            String sql = "INSERT INTO orderdetails(idOrder, idVaccine, idPackage, quantityOrder, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);

            // Gán giá trị cho các tham số
            pst.setInt(1, orderId);

            // Nếu vaccineId là 0, truyền NULL, nếu không truyền giá trị
            if (vaccineId != 0) {
                pst.setInt(2, vaccineId);
            } else {
                pst.setNull(2, java.sql.Types.INTEGER);
            }

            // Nếu packageId là 0, truyền NULL, nếu không truyền giá trị
            if (packageId != 0) {
                pst.setInt(3, packageId);
            } else {
                pst.setNull(3, java.sql.Types.INTEGER);
            }

            pst.setInt(4, quantity);

            // Lấy giá dựa trên loại vaccine hoặc gói vaccine
            pst.setFloat(5, price);

            result = pst.executeUpdate();

            if (result > 0) {
                System.out.println("Dữ liệu đã được thêm thành công.");
            } else {
                System.out.println("Thêm dữ liệu thất bại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy giá của vaccine lẻ
    public float getVaccinePrice(int vaccineId) {
        String sql = "SELECT price FROM vaccines WHERE id = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, vaccineId);
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getFloat("price") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy giá của gói vaccine
    public float getPackagePrice(int packageId) {
        String sql = "SELECT totalPrice FROM vaccinepackages WHERE id = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, packageId);
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getFloat("totalPrice") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPackageNameById(int packageId) {
        String name = null;
        String sql = "SELECT name FROM vaccinepackages WHERE id = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, packageId);

            pst.setInt(1, packageId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    public String getVaccineNameById(int vaccineId) {
        String name = null;
        String sql = "SELECT name FROM vaccines WHERE id = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, vaccineId);

            pst.setInt(1, vaccineId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    public int totalOrder() {
        int result = 0;

        try {
            String sql = "SELECT COUNT(DISTINCT od.idOrder) AS total " +
                    "FROM orderdetails od " +
                    "JOIN orders o ON od.idOrder = o.id " +
                    "WHERE o.status != 'Đã hủy';"; // thêm điều kiện
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean deleteOrderDetail(int idVaccine) {
        try {
            String sql = "delete from orderdetails where idVaccine=?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idVaccine);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public float getOrderPriceByOrderId(int id) {
        String sql = "SELECT price FROM orderdetails WHERE idOrder=?";
        float price = 0;
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                price += rs.getFloat("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    // Đếm số lượng đơn hàng tuần này so với tuần trước bao nhiêu đơn
    public int getOrdersCountLastWeek() {
        int count = 0;
        try {
            String sql = "SELECT " +
                    "(SELECT COUNT(o.id) " +
                    "FROM orders o " +
                    "WHERE o.status != 'Đã hủy' " +
                    "AND o.createdAt >= CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY " +
                    "AND o.createdAt < CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY + INTERVAL 7 DAY) " +
                    "- " +
                    "(SELECT COUNT(o.id) " +
                    "FROM orders o " +
                    "WHERE o.status != 'Đã hủy' " +
                    "AND o.createdAt >= CURDATE() - INTERVAL WEEKDAY(CURDATE()) + 7 DAY " +
                    "AND o.createdAt < CURDATE() - INTERVAL WEEKDAY(CURDATE()) + 7 DAY + INTERVAL 7 DAY) " +
                    "AS count;";

            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Tính tổng doanh thu
    public float totalRevenue() {
        float totalRevenue = 0;
        try {
            String sql = "SELECT " +
                    "SUM(od.price * od.quantityOrder) AS totalRevenue " +
                    "FROM orderdetails od " +
                    "JOIN orders o ON od.idOrder = o.id " +
                    "WHERE o.status != 'Đã hủy' " +
                    "AND MONTH(o.createdAt) = MONTH(CURDATE()) " +
                    "AND YEAR(o.createdAt) = YEAR(CURDATE());";

            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                totalRevenue = rs.getFloat("totalRevenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }


}

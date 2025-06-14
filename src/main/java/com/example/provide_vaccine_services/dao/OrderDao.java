package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao {

    public int insertOrder(Orders o) {
        int id = -1;

        try {
            String sql = "INSERT INTO orders(idPatient, idCenter, createdAt, appointmentDate, appointmentTime, status, paymentStatus) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);

            pst.setInt(1, o.getIdPatient());
            pst.setInt(2, o.getIdCenter());
            pst.setObject(3, o.getCreatedAt());
            pst.setObject(4, o.getAppointmentDate());
            pst.setString(5, o.getAppointmentTime());
            pst.setString(6, o.getStatus());
            pst.setString(7, o.getPaymentStatus());

            // Thực thi câu lệnh
            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                System.out.println("Them du lieu thanh cong");
            } else {
                System.out.println("Them du lieu that bai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<Vaccines> quantityVaccine() {
        List<Vaccines> result = new ArrayList<>();

        try {
            String sql = "SELECT v.id AS vaccine_id, " +
                    "v.name AS vaccine_name, " +
                    "v.description AS vaccine_description, " +
                    "COUNT(od.id) AS order_count " +
                    "FROM orderdetails od " +
                    "JOIN orders o ON od.idOrder = o.id " +
                    "JOIN vaccines v ON od.idVaccine = v.id " +
                    "LEFT JOIN vaccinepmappings vm ON od.idPackage = vm.idPackage " +
                    "LEFT JOIN vaccinepackages vp ON vp.id = vm.idPackage " +
                    "WHERE MONTH(o.appointmentDate) = MONTH(CURDATE()) " +
                    "GROUP BY v.id " +
                    "ORDER BY order_count DESC";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Vaccines vaccine = new Vaccines();
                vaccine.setId(rs.getInt("vaccine_id"));
                vaccine.setName(rs.getString("vaccine_name"));
                vaccine.setDescription(rs.getString("vaccine_description"));
                vaccine.setOrderCount(rs.getInt("order_count"));
                result.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Map<String, Object>> getOrder() {
        List<Map<String, Object>> re = new ArrayList<>();

        try {
            String sqlOrder = "SELECT o.id AS order_id, " +
                    "p.fullname AS patient_name, " +
                    "c.name AS center_name, " +
                    "o.appointmentDate AS appointment_date, " +
                    "o.appointmentTime AS appointment_time, " +
                    "SUM(COALESCE(v.price, vp.totalPrice) * od.quantityOrder) AS total_price, " +
                    "o.status AS order_status, " +
                    "GROUP_CONCAT(COALESCE(v.name, vp.name) SEPARATOR ', ') AS vaccine_or_package_names " +
                    "FROM orders o " +
                    "JOIN patients p ON o.idPatient = p.id " +
                    "JOIN orderdetails od ON o.id = od.idOrder " +
                    "JOIN centers c ON o.idCenter = c.id " +
                    "LEFT JOIN vaccines v ON od.idVaccine = v.id " +
                    "LEFT JOIN vaccinepackages vp ON od.idPackage = vp.id " +
                    "GROUP BY o.id, p.fullname, o.appointmentDate, o.appointmentTime, o.status";

            PreparedStatement pstOrder = DBConnect.get(sqlOrder);
            ResultSet rsOrder = pstOrder.executeQuery();

            while (rsOrder.next()) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("order_id", rsOrder.getInt("order_id"));
                orderData.put("patient_name", rsOrder.getString("patient_name"));
                orderData.put("center_name", rsOrder.getString("center_name"));
                orderData.put("appointment_date", rsOrder.getDate("appointment_date"));
                orderData.put("appointment_time", rsOrder.getString("appointment_time"));
                orderData.put("total_price", rsOrder.getFloat("total_price"));
                orderData.put("order_status", rsOrder.getString("order_status"));
                orderData.put("vaccine_or_package_names", rsOrder.getString("vaccine_or_package_names"));
                re.add(orderData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public List<Map<String, Object>> getOrderDetailById(int id) {
        List<Map<String, Object>> re = new ArrayList<>();

        try {
            String sqlOrder = "SELECT " +
                    "o.id AS order_id, " +
                    "cp.fullname AS contact_name, " +
                    "cp.relationship AS relationship, " +
                    "cp.phone AS phone, " +
                    "SUM(COALESCE(v.price, vp.totalPrice) * od.quantityOrder) AS total_price, " +
                    "GROUP_CONCAT(DISTINCT COALESCE(v.name, vp.name) SEPARATOR ', ') AS vaccine_or_package_names " +
                    "FROM orders o " +
                    "JOIN patients p ON o.idPatient = p.id " +
                    "JOIN contactpersons cp ON cp.idPatient = p.id " +
                    "JOIN orderdetails od ON o.id = od.idOrder " +
                    "LEFT JOIN vaccines v ON od.idVaccine = v.id " +
                    "LEFT JOIN vaccinepackages vp ON od.idPackage = vp.id " +
                    "WHERE o.id = ? " +
                    "GROUP BY cp.fullname, cp.relationship, cp.phone";

            PreparedStatement pstOrder = DBConnect.get(sqlOrder);
            pstOrder.setInt(1, id);
            ResultSet rsOrder = pstOrder.executeQuery();

            while (rsOrder.next()) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("order_id", rsOrder.getInt("order_id"));
                orderData.put("contact_name", rsOrder.getString("contact_name"));
                orderData.put("relationship", rsOrder.getString("relationship"));
                orderData.put("phone", rsOrder.getString("phone"));
                orderData.put("total_price", rsOrder.getFloat("total_price"));
                orderData.put("vaccine_or_package_names", rsOrder.getString("vaccine_or_package_names"));
                re.add(orderData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public List<Map<String, Object>> export() {
        List<Map<String, Object>> re = new ArrayList<>();

        try {
            String sqlOrder = "SELECT " +
                    "o.id AS order_id, " +
                    "p.fullname AS patient_name, " +
                    "c.name AS center_name, " +
                    "o.appointmentDate AS appointment_date, " +
                    "o.appointmentTime AS appointment_time, " +
                    "cp.fullname AS contact_name, " +
                    "cp.relationship AS relationship, " +
                    "cp.phone AS phone, " +
                    "SUM(COALESCE(v.price, vp.totalPrice) * od.quantityOrder) AS total_price, " +
                    "GROUP_CONCAT(DISTINCT COALESCE(v.name, vp.name) SEPARATOR ', ') AS vaccine_or_package_names " +
                    "FROM orders o " +
                    "JOIN patients p ON o.idPatient = p.id " +
                    "JOIN centers c ON o.idCenter = c.id " +
                    "JOIN contactpersons cp ON cp.idPatient = p.id " +
                    "JOIN orderdetails od ON o.id = od.idOrder " +
                    "LEFT JOIN vaccines v ON od.idVaccine = v.id " +
                    "LEFT JOIN vaccinepackages vp ON od.idPackage = vp.id " +
                    "GROUP BY o.id, p.fullname, c.name, o.appointmentDate, " +
                    "o.appointmentTime, cp.fullname, cp.relationship, cp.phone";

            PreparedStatement pstOrder = DBConnect.get(sqlOrder);
            ResultSet rsOrder = pstOrder.executeQuery();

            while (rsOrder.next()) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("order_id", rsOrder.getInt("order_id"));
                orderData.put("patient_name", rsOrder.getString("patient_name"));
                orderData.put("center_name", rsOrder.getString("center_name"));
                orderData.put("appointment_date", rsOrder.getDate("appointment_date"));
                orderData.put("appointment_time", rsOrder.getString("appointment_time"));
                orderData.put("contact_name", rsOrder.getString("contact_name"));
                orderData.put("relationship", rsOrder.getString("relationship"));
                orderData.put("phone", rsOrder.getString("phone"));
                orderData.put("total_price", rsOrder.getFloat("total_price"));
                orderData.put("vaccine_or_package_names", rsOrder.getString("vaccine_or_package_names"));
                re.add(orderData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }


    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, status);
            pst.setInt(2, orderId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, Object>> getOrderByUserId(int userId) {
        List<Map<String, Object>> re = new ArrayList<>();

        try {
            String sqlOrder = "SELECT o.id AS order_id, " +
                    "p.fullname AS patient_name, " +
                    "o.appointmentDate AS appointment_date, " +
                    "o.appointmentTime AS appointment_time, " +
                    "o.paymentStatus AS paymentStatus, " +
                    "SUM(COALESCE(v.price, vp.totalPrice) * od.quantityOrder) AS total_price, " +
                    "o.status AS order_status, " +
                    "GROUP_CONCAT(COALESCE(v.name, vp.name) SEPARATOR ', ') AS vaccine_or_package_names " +
                    "FROM orders o " +
                    "JOIN patients p ON o.idPatient = p.id " +
                    "JOIN contactpersons c ON c.idPatient = p.id " +
                    "JOIN users u ON u.id = c.idUser " +
                    "JOIN orderdetails od ON o.id = od.idOrder " +
                    "LEFT JOIN vaccines v ON od.idVaccine = v.id " +
                    "LEFT JOIN vaccinepackages vp ON od.idPackage = vp.id " +
                    "WHERE u.id = ? " +
                    "GROUP BY o.id, p.fullname, o.appointmentDate, o.appointmentTime, o.status";

            PreparedStatement pstOrder = DBConnect.get(sqlOrder);
            pstOrder.setInt(1, userId);

            ResultSet rsOrder = pstOrder.executeQuery();

            while (rsOrder.next()) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("order_id", rsOrder.getInt("order_id"));
                orderData.put("patient_name", rsOrder.getString("patient_name"));
                orderData.put("appointment_date", rsOrder.getDate("appointment_date"));
                orderData.put("appointment_time", rsOrder.getString("appointment_time"));
                orderData.put("total_price", rsOrder.getFloat("total_price"));
                orderData.put("order_status", rsOrder.getString("order_status"));
                orderData.put("vaccine_or_package_names", rsOrder.getString("vaccine_or_package_names"));
                orderData.put("paymentStatus", rsOrder.getString("paymentStatus"));
                re.add(orderData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public Map<String, Object> getAppointmentDetails(int id) {
        Map<String, Object> result = new HashMap<>();
        List<Vaccines> vaccinesList = new ArrayList<>();
        String vaccinePackageName = null;  // Biến để lưu tên gói vắc xin

        // Câu truy vấn SQL để lấy thông tin chi tiết về đơn hàng và các vắc xin liên quan
        String sql = """
    SELECT 
        o.id AS order_id, 
        o.appointmentDate, 
        o.appointmentTime, 
        o.status AS order_status, 
        o.paymentStatus,
        p.id AS patient_id, 
        p.fullname AS patient_name, 
        p.dateOfBirth, 
        p.gender, 
        p.identification, 
        p.address AS patient_address, 
        p.province AS patient_province, 
        p.district AS patient_district, 
        p.ward AS patient_ward, 
        c.id AS center_id,
        c.name AS center_name, 
        c.address AS center_address, 
        c.phone AS center_phone, 
        v.id AS vaccine_id, 
        v.name AS vaccine_name, 
        v.description AS vaccine_description, 
        v.price AS vaccine_price, 
        v.imageUrl AS vaccine_image_url,
        vp.name AS vaccine_package_name, 
        u.fullname AS contact_fullname,
        u.phone AS contact_phone,
        cp.relationship AS contact_relationship 
    FROM orders o 
    JOIN patients p ON o.idPatient = p.id 
    JOIN centers c ON o.idCenter = c.id 
    JOIN orderdetails od ON o.id = od.idOrder
    LEFT JOIN vaccines v ON od.idVaccine = v.id
    LEFT JOIN vaccinepackages vp ON od.idPackage = vp.id 
    LEFT JOIN contactpersons cp ON p.id = cp.idPatient
    LEFT JOIN users u ON cp.idUser = u.id
    WHERE o.id = ?
    """;

        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setInt(1, id);  // Đặt tham số ID vào câu truy vấn

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng Order và thiết lập các thông tin
                    Orders order = new Orders();
                    order.setId(rs.getInt("order_id"));
                    order.setAppointmentDate(rs.getDate("appointmentDate"));
                    order.setAppointmentTime(rs.getString("appointmentTime"));
                    order.setStatus(rs.getString("order_status"));
                    order.setPaymentStatus(rs.getString("paymentStatus"));
                    result.put("order", order);

                    // Tạo đối tượng Patient và thiết lập thông tin
                    Patients patient = new Patients();
                    patient.setId(rs.getInt("patient_id"));
                    patient.setFullname(rs.getString("patient_name"));
                    patient.setDateOfBirth(rs.getDate("dateOfBirth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setIdentification(rs.getString("identification"));
                    patient.setAddress(rs.getString("patient_address"));
                    patient.setProvince(rs.getString("patient_province"));
                    patient.setDistrict(rs.getString("patient_district"));
                    patient.setWard(rs.getString("patient_ward"));
                    result.put("patient", patient);

                    // Tạo đối tượng Center và thiết lập thông tin
                    Centers center = new Centers();
                    center.setId(rs.getInt("center_id"));
                    center.setName(rs.getString("center_name"));
                    center.setAddress(rs.getString("center_address"));
                    center.setPhone(rs.getString("center_phone"));
                    result.put("center", center);

                    // Tạo đối tượng Vaccine và thiết lập thông tin
                    Vaccines vaccine = new Vaccines();
                    vaccine.setId(rs.getInt("vaccine_id"));
                    vaccine.setName(rs.getString("vaccine_name"));
                    vaccine.setDescription(rs.getString("vaccine_description"));
                    vaccine.setPrice(rs.getFloat("vaccine_price"));

                    // Nếu vaccine có thông tin đầy đủ, thêm vào danh sách
                    if (vaccine.getName() != null && !vaccine.getName().isEmpty()) {
                        vaccinesList.add(vaccine);
                    }

                    // Kiểm tra gói vắc xin
                    if (rs.getString("vaccine_package_name") != null) {
                        vaccinePackageName = rs.getString("vaccine_package_name");
                    }
                    result.put("vaccinePackageName", vaccinePackageName); // Lưu tên gói vắc xin

                    // Thêm thông tin người liên hệ
                    String contactFullname = rs.getString("contact_fullname");
                    String contactPhone = rs.getString("contact_phone");
                    String contactRelationship = rs.getString("contact_relationship");

                    result.put("contactFullname", contactFullname);
                    result.put("contactPhone", contactPhone);
                    result.put("contactRelationship", contactRelationship);
                }

                // Truyền danh sách vắc xin vào kết quả
                result.put("vaccines", vaccinesList);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Orders getOrderById(int idOrder) {
        Orders result = null;

        String sql = "select * from orders where id = ?";
        try  {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1,idOrder);
            ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    result = new Orders();
                    result.setId(rs.getInt("id"));
                    result.setIdPatient(rs.getInt("idPatient"));
                    result.setIdCenter(rs.getInt("idCenter"));
                    // Lấy giá trị createdAt và chuyển thành LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("createdAt");
                    if (timestamp != null) {
                        LocalDateTime createdAt = timestamp.toLocalDateTime();
                        result.setCreatedAt(createdAt); // Đảm bảo phương thức setCreatedAt đã được định nghĩa trong Orders
                    }
                    // Lấy các trường còn lại nếu cần thiết
                    result.setAppointmentDate(rs.getDate("appointmentDate"));
                    result.setAppointmentTime(rs.getString("appointmentTime"));
                    result.setStatus(rs.getString("status"));
                    result.setPaymentStatus(rs.getString("paymentStatus"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public boolean updateOrderStatus(Orders order) {
        String sql = "UPDATE orders SET paymentStatus = ? WHERE id = ?";
        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setString(1,order.getPaymentStatus());
            pst.setInt(2,order.getId());
            int rowsAffected = pst.executeUpdate();
            if(rowsAffected > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, Integer> getVaccineAndQuantity(int orderId) {
        String sql = "select v.id, count(*) as time from orders o join orderdetails odt on o.id = odt.idOrder inner join vaccines v on v.id = odt.idVaccine where o.id = ? GROUP BY v.id";
        Map<Integer, Integer> result = new HashMap<>();

        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                result.put(rs.getInt("id"), rs.getInt("time"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
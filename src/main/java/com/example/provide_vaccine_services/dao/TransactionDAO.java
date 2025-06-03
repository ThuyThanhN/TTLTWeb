package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Users;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDAO {

    public List<Transaction> getAllTransaction() {

        List<Transaction> transactions = new ArrayList<Transaction>();

        try {
            String sql = "select * from transaction";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int vaccine_id = rs.getInt("vaccine_id");
                String type = rs.getString("type");
                int quantity = rs.getInt("quantity");
                int user_id = rs.getInt("user_id");
                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("expiry_date");
                LocalDateTime expiry_date = timestamp != null ? timestamp.toLocalDateTime() : null;

                Timestamp created_at = rs.getTimestamp("date");
                LocalDateTime created_at2 = created_at != null ? created_at.toLocalDateTime() : null;

                UserDao userDao = new UserDao();
                Users user = userDao.getUserById(user_id);

                Transaction transaction = new Transaction(id, vaccine_id, type, quantity, expiry_date, user);
                transaction.setDate(created_at2);

                transactions.add(transaction);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;

    }

    public int insert(Transaction t) {
        int newId = -1;

        try {
            String sql = "insert into transaction( vaccine_id, type, quantity, expiry_date, user_id, date) " +
                    "values(?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);
            pst.setInt(1, t.getVaccineId());
            pst.setString(2, t.getType());
            pst.setInt(3, t.getQuantity());
            pst.setTimestamp(4, Timestamp.valueOf(t.getExpiry_date())); // chuyển đổi tại đây
            pst.setInt(5, t.getUser().getId());
            pst.setTimestamp(6, Timestamp.valueOf(t.getDate()));

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                // lay id moi nhat
                String getIdSql = "SELECT MAX(id) FROM transaction";
                PreparedStatement getIdStmt = DBConnect.get(getIdSql);
                ResultSet rs = getIdStmt.executeQuery();
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public int update(Transaction transaction) {
        int result = 0;  // Mặc định trả về 0 (thất bại)

        try {
            String sql = "UPDATE transaction SET vaccine_id = ?, type = ?, quantity = ?, expiry_date = ?, user_id = ? WHERE id = ?";
            PreparedStatement pst = DBConnect.getAuto(sql);

            // Gán giá trị cho các tham số
            pst.setInt(1, transaction.getVaccineId());
            pst.setString(2, transaction.getType());
            pst.setInt(3, transaction.getQuantity());
            pst.setTimestamp(4, Timestamp.valueOf(transaction.getExpiry_date()));
            pst.setInt(5, transaction.getUser().getId());
            pst.setInt(6, transaction.getTransactionId());

            // Thực thi câu lệnh update
            result = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Transaction getTransactionById(int id) {
        Transaction transaction = null;

        try {
            String sql = "SELECT * FROM transaction WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int vaccineId = rs.getInt("vaccine_id");
                String type = rs.getString("type");
                int quantity = rs.getInt("quantity");
                int userId = rs.getInt("user_id");
                Timestamp timestamp = rs.getTimestamp("expiry_date");
                LocalDateTime expiryDate = timestamp != null ? timestamp.toLocalDateTime() : null;

                // Lấy thông tin người dùng
                UserDao userDao = new UserDao();
                Users user = userDao.getUserById(userId);

                transaction = new Transaction(id, vaccineId, type, quantity, expiryDate, user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public List<Map<String, Object>> export() {
        List<Map<String, Object>> transactionList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            String sql = "SELECT t.id, t.vaccine_id, t.type, t.quantity, t.date, t.expiry_date, t.user_id " +
                    "FROM transaction t " +
                    "ORDER BY t.id";

            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();


            VaccineDao vaccineDAO = new VaccineDao();
            while (rs.next()) {
                int id = rs.getInt("id");
                Vaccines vaccines = vaccineDAO.getVaccineById(id);


                Map<String, Object> transactionData = new HashMap<>();
                transactionData.put("id", rs.getInt("id"));
                transactionData.put("vaccineName", vaccines.getName());
                transactionData.put("type", rs.getString("type"));
                transactionData.put("quantity", rs.getString("quantity"));
                transactionData.put("user_id", rs.getInt("user_id"));


                Date date = rs.getDate("date");
                Date expiryDate = rs.getDate("expiry_date");

                transactionData.put("date", date != null ? date.toLocalDate().format(formatter) : null);
                transactionData.put("expiry_date", expiryDate != null ? expiryDate.toLocalDate().format(formatter) : null);

                transactionList.add(transactionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;

    }

    public void deleteById(int transactionId) {
        try {
            String sql = "DELETE FROM transaction WHERE id = ?";
            PreparedStatement pst = DBConnect.getAuto(sql);
            pst.setInt(1, transactionId);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Xóa giao dịch thành công, ID: " + transactionId);
            } else {
                System.out.println("Không tìm thấy giao dịch để xóa, ID: " + transactionId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

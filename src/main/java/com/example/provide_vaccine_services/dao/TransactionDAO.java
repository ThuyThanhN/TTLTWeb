package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Users;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

                UserDao userDao = new UserDao();
                Users user = userDao.getUserById(user_id);

                Transaction transaction = new Transaction(id, vaccine_id, type, quantity, expiry_date, user);

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
            String sql = "insert into transaction( vaccine_id, type, quantity, expiry_date, user_id) " +
                    "values(?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);
            pst.setInt(1, t.getVaccineId());
            pst.setString(2, t.getType());
            pst.setInt(3, t.getQuantity());
            pst.setTimestamp(4, Timestamp.valueOf(t.getExpiry_date())); // chuyển đổi tại đây
            pst.setInt(5, t.getUser().getId());

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
}

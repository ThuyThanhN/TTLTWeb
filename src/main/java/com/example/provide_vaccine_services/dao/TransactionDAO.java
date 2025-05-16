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
                int ware_house_id = rs.getInt("ware_house_id");
                String type = rs.getString("type");
                int quantity = rs.getInt("quantity");
                String imageUrl = rs.getString("imageUrl");
                int user_id = rs.getInt("user_id");
                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                UserDao userDao = new UserDao();
                Users user = userDao.getUserById(user_id);

                Transaction transaction = new Transaction(id ,ware_house_id, vaccine_id, type, quantity, createAt, user);

                transactions.add(transaction);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;

    }

}

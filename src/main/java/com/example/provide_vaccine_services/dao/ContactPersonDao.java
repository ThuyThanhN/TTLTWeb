package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.ContactPersons;
import com.example.provide_vaccine_services.dao.model.Users;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactPersonDao {
    public int insertContact(ContactPersons cp) {
        int re = 0;

        try {
            String sql = "INSERT INTO contactpersons(idUser, idPatient, fullname, relationship, phone) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);

            // Thiết lập các tham số cho câu lệnh SQL
            pst.setInt(1, cp.getIdUser());
            pst.setInt(2, cp.getIdPatient());
            pst.setString(3, cp.getFullname());
            pst.setString(4, cp.getRelationship());
            pst.setString(5, cp.getPhone());

            re = pst.executeUpdate();

            if (re > 0) {
                System.out.println("Them du lieu thanh cong");
            } else {
                System.out.println("Them du lieu that bai!");
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }
}

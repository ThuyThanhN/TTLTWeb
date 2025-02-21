package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Patients;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDao {
//    Them nguoi tiem
    public int insertPatient(Patients p) {
        int id = -1;

        try {
            String sql = "insert into patients (fullname, dateOfBirth, gender, identification, address, province, district, ward) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);

            pst.setString(1, p.getFullname());
            pst.setDate(2, p.getDateOfBirth());
            pst.setString(3, p.getGender());
            pst.setString(4, p.getIdentification());
            pst.setString(5, p.getAddress());
            pst.setString(6, p.getProvince());
            pst.setString(7, p.getDistrict());
            pst.setString(8, p.getWard());

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
}

package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.VacccineDetails;
import com.example.provide_vaccine_services.dao.model.VaccineTypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccineTypeDao {

    public int insert(VaccineTypes v) {
        int re = 0;

        try {
            String sql = "insert into vaccinetypes(idVaccine, idAgeGroup, idDisaseGroup) values(?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, v.getIdVaccine());
            pst.setInt(2, v.getIdAgeGroup());
            pst.setInt(3, v.getIdDisaseGroup());

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

    public boolean updateType(VaccineTypes vt) {
        String sql = "UPDATE vaccinetypes SET idAgeGroup = ?, idDisaseGroup = ? WHERE idVaccine = ?";

        try  {
            PreparedStatement pst = DBConnect.get(sql);
            // Gán giá trị cho các tham số trong câu lệnh SQL
            pst.setInt(1, vt.getIdAgeGroup());
            pst.setInt(2, vt.getIdDisaseGroup());
            pst.setInt(3, vt.getIdVaccine());

            // Thực thi lệnh cập nhật
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public VaccineTypes getType(int id) {
//        try {
//            String sql = "SELECT * FROM vaccinetypes WHERE idVaccine = ?";
//            PreparedStatement pst = DBConnect.get(sql);
//            pst.setInt(1, id);
//
//            // Thực thi câu lệnh truy vấn
//            ResultSet rs = pst.executeQuery();
//
//            if (rs.next()) {
//                // Nếu tìm thấy dữ liệu, khởi tạo đối tượng VaccineTypes
//                int idVaccine = rs.getInt("idVaccine");
//                int idAgeGroup = rs.getInt("idAgeGroup");
//                int idDisaseGroup = rs.getInt("idDisaseGroup");
//
//                VaccineTypes vt = new VaccineTypes(idVaccine, idAgeGroup, idDisaseGroup);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

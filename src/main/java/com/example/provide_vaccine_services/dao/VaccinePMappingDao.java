package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.VaccinePMappings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccinePMappingDao {
    public int insert(VaccinePMappings vpm) {
        int re = 0;

        try {
            String sql = "insert into vaccinepmappings(idVaccine, idPackage, dosage) values(?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, vpm.getIdVaccine());
            pst.setInt(2, vpm.getIdPackage());
            pst.setInt(3, vpm.getDosage());

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

    public int delete(int idP) {
        int re = 0;

        try {
            String sql = "delete from vaccinepmappings where idPackage = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idP);

            re = pst.executeUpdate();

            if (re > 0) {
                System.out.println("Xoa du lieu thanh cong");
            } else {
                System.out.println("Xoa du lieu that bai!");
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }
}

package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.PackageAges;
import com.example.provide_vaccine_services.dao.model.Suppliers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageAgeDao {
    public int insert(PackageAges pa) {
        int re = 0;

        try {
            String sql = "insert into packageages(idPackage, idAge) values(?, ?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, pa.getIdAge());
            pst.setInt(2, pa.getIdPackage());

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

    //    Lay danh sach nha cung cap
    public List<PackageAges> getAll() {
        ArrayList<PackageAges> re = new ArrayList<>();
        try {
            String sql = "SELECT * FROM packageages";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idAge = rs.getInt("idAge");
                int idPackage = rs.getInt("idPackage");

                PackageAges age = new PackageAges(id, idAge, idPackage);
                re.add(age);
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}

package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Suppliers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao {
    //    Them nha cung cap
    public int insert(Suppliers s) {
        int re = 0;

        try {
            String sql = "insert into suppliers(name, countryOfOrigin) values(?, ?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, s.getName());
            pst.setString(2, s.getCountryOfOrigin());

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

    // Cap nhat nha cung cap
    public int update(Suppliers s) {
        int re = 0;

        try {

            String sql = "update suppliers SET name = ?, countryOfOrigin = ? WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, s.getName());
            pst.setString(2, s.getCountryOfOrigin());
            pst.setInt(3, s.getId());

            re = pst.executeUpdate();

            if (re > 0) {
                System.out.println("Cap nhat du lieu thanh cong");
            } else {
                System.out.println("Cap nhat du lieu that bai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }


    // Xoa nha cung cap
    public int deleteSupplier(int idS) {
        int re = 0;

        try {
            // Xoa vac xin
            String deleteVaccineSql = "DELETE FROM vaccines WHERE idSupplier = ?";
            PreparedStatement pstVaccine = DBConnect.get(deleteVaccineSql);
            pstVaccine.setInt(1, idS);
            pstVaccine.executeUpdate();

            // Xoa nha cung cap
            String deleteSupplierSql = "DELETE FROM suppliers WHERE id = ?";
            PreparedStatement pstSupplier = DBConnect.get(deleteSupplierSql);
            pstSupplier.setInt(1, idS);
            re = pstSupplier.executeUpdate();

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

    //    Lay danh sach nha cung cap
    public List<Suppliers> getAll() {
        ArrayList<Suppliers> re = new ArrayList<>();
        try {
            String sql = "SELECT * FROM suppliers";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String countryOfOrigin = rs.getString("countryOfOrigin");

                Suppliers supplier = new Suppliers(id, name, countryOfOrigin);
                re.add(supplier);
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Lay danh sach theo id
    public Suppliers getById(int idVaccine) {
        Suppliers re = null;
        try {
            String sql = "SELECT s.id " +
                    "FROM vaccines v " +
                    "JOIN suppliers s ON s.id = v.idSupplier " +
                    "WHERE v.id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idVaccine);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                re = new Suppliers();
                re.setId(rs.getInt("id"));
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return re;
        }
    }
}

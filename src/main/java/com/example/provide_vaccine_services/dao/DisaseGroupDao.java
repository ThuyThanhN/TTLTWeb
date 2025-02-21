
package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.DisaseGroups;
import com.example.provide_vaccine_services.dao.model.Suppliers;
import com.example.provide_vaccine_services.dao.model.VaccineTypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisaseGroupDao {
    public DisaseGroups getDisaseById(int idVaccine) {
        DisaseGroups re = null;
        try {
            String sql = "SELECT dg.id " +
                    "FROM vaccines v " +
                    "JOIN vaccinetypes vt ON vt.idVaccine = v.id " +
                    "JOIN disasegroups dg ON dg.id = vt.idDisaseGroup " +
                    "WHERE v.id = ?";

            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idVaccine);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                re = new DisaseGroups();
                re.setId(rs.getInt("id"));
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return re;
        }
    }

    public List<DisaseGroups> getDisaseGroups() {
        ArrayList<DisaseGroups> re = new ArrayList<>();
        try {
            String sql = "select * from disasegroups";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                DisaseGroups disase = new DisaseGroups(id, name);
                re.add(disase);
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int insert(DisaseGroups d) {
        int re = 0;

        try {
            String sql = "insert into disasegroups(name) values(?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, d.getName());

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

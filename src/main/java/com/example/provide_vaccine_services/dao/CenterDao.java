package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Centers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CenterDao {
    //    Them nha cung cap
    public int insert(Centers c) {
        int re = 0;

        try {
            String sql = "insert into centers(name, address, province, district, ward, phone) values(?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, c.getName());
            pst.setString(2, c.getAddress());
            pst.setString(3, c.getProvince());
            pst.setString(4, c.getDistrict());
            pst.setString(5, c.getWard());
            pst.setString(6, c.getPhone());

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
    public int update(Centers c) {
        int re = 0;

        try {

            String sql = "update centers SET name = ?, address = ?, province = ?, district = ?, ward = ?, phone = ? WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, c.getName());
            pst.setString(2, c.getAddress());
            pst.setString(3, c.getProvince());
            pst.setString(4, c.getDistrict());
            pst.setString(5, c.getWard());
            pst.setString(6, c.getPhone());
            pst.setInt(7, c.getId());

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
    public int deleteCenter(int idCenter) {
        int re = 0;

        try {
            // Xoa cac ban ghi lien quan trong bang orderdetails
            String deleteOrderDetailsSql = "DELETE FROM orderdetails WHERE idOrder IN (SELECT id FROM orders WHERE idCenter = ?)";
            PreparedStatement pstDeleteOrderDetails = DBConnect.get(deleteOrderDetailsSql);
            pstDeleteOrderDetails.setInt(1, idCenter);
            pstDeleteOrderDetails.executeUpdate();

            // Xoa cac ban ghi trong bang orders lien quan den center
            String deleteOrdersSql = "DELETE FROM orders WHERE idCenter = ?";
            PreparedStatement pstDeleteOrders = DBConnect.get(deleteOrdersSql);
            pstDeleteOrders.setInt(1, idCenter);
            pstDeleteOrders.executeUpdate();

            // Xoa center
            String sql = "DELETE FROM centers WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idCenter);

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

    //    Lay danh sach nha cung cap
    public List<Centers> getAll() {
        ArrayList<Centers> re = new ArrayList<>();
        try {
            String sql = "select * from centers";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String province = rs.getString("province");
                String district = rs.getString("district");
                String ward = rs.getString("ward");
                String phone = rs.getString("phone");

                Centers center = new Centers(id, name, address, province, district, ward, phone);
                re.add(center);
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Lay danh sach theo id
//    public Centers getById(int id) {
//        Centers re = null;
//        try {
//            String sql = "select * from centers where id=?";
//            PreparedStatement pst = DBConnect.get(sql);
//            pst.setInt(1, id);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String address = rs.getString("address");
//                String phone = rs.getString("phone");
//
//                Centers center = new Centers(name, address, phone);
//            }
//            return re;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return re;
//        }
//    }
}


package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.VacccineDetails;
import com.example.provide_vaccine_services.dao.model.VaccineContents;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccineContentDao {
    public List<VaccineContents> getAllVaccineContents() {
        List<VaccineContents> vaccineContentsList = new ArrayList<>(); // Danh sách để lưu kết quả
        String sql = "SELECT * FROM vaccinecontents"; // Câu lệnh SQL

        try  {
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) { // Duyệt qua từng dòng kết quả
                int id = rs.getInt("id");
                int idDetail = rs.getInt("idDetail");
                String origin = rs.getString("origin");
                String administrationRoute = rs.getString("administrationRoute");
                String contraindications = rs.getString("contraindications");
                String precaution = rs.getString("precaution");
                String drugInteractions = rs.getString("drugInteractions");
                String sideEffects = rs.getString("sideEffects");

                // Khởi tạo đối tượng VaccineContents
                VaccineContents vaccineContents = new VaccineContents(id, idDetail, origin, administrationRoute, contraindications, precaution, drugInteractions, sideEffects);

                // Thêm vào danh sách
                vaccineContentsList.add(vaccineContents);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có vấn đề xảy ra
        }

        return vaccineContentsList; // Trả về danh sách kết quả
    }

    public VaccineContents getVaccineContentsById(int idVaccine) {
        VaccineContents re = null;
        try {
            String sql = "SELECT vc.* " +
                    "FROM vaccines v " +
                    "JOIN vaccinedetails vd ON vd.idVaccine = v.id " +
                    "JOIN vaccinecontents vc ON vc.idDetail = vd.id " +
                    "WHERE v.id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idVaccine);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idDetail = rs.getInt("idDetail");
                String origin = rs.getString("origin");
                String administrationRoute = rs.getString("administrationRoute");
                String contraindications = rs.getString("contraindications");
                String precaution = rs.getString("precaution");
                String drugInteractions = rs.getString("drugInteractions");
                String sideEffects = rs.getString("sideEffects");

                re = new VaccineContents(idDetail, origin, administrationRoute, contraindications, precaution, drugInteractions, sideEffects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public int insert(VaccineContents vc) {
        int re = 0;

        try {
            // Câu lệnh SQL với các cột cụ thể
            String sql = "INSERT INTO vaccinecontents(idDetail, origin, administrationRoute, contraindications, precaution, drugInteractions, sideEffects) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);

            // Gán giá trị từ đối tượng VaccineContents
            pst.setInt(1, vc.getIdDetail());
            pst.setString(2, vc.getOrigin());
            pst.setString(3, vc.getAdministrationRoute());
            pst.setString(4, vc.getContraindications());
            pst.setString(5, vc.getPrecaution());
            pst.setString(6, vc.getDrugInteractions());
            pst.setString(7, vc.getSideEffects());

            // Thực thi câu lệnh
            re = pst.executeUpdate();

            // Kiểm tra kết quả
            if (re > 0) {
                System.out.println("Them du lieu thanh cong");
            } else {
                System.out.println("Them du lieu that bai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public boolean updateVaccineContent(VaccineContents vc) {
        String sql = "UPDATE vaccinecontents SET origin = ?, administrationRoute = ?, "
                + "contraindications = ?, precaution = ?, drugInteractions = ?, sideEffects = ? "
                + "WHERE idDetail = ?";

        try  {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, vc.getOrigin());
            pst.setString(2, vc.getAdministrationRoute());
            pst.setString(3, vc.getContraindications());
            pst.setString(4, vc.getPrecaution());
            pst.setString(5, vc.getDrugInteractions());
            pst.setString(6, vc.getSideEffects());
            pst.setInt(7, vc.getIdDetail());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public VaccineContents getContent(int id) {
//        try {
//            // Câu lệnh SQL để lấy dữ liệu từ bảng vaccinecontents
//            String sql = "SELECT * FROM vaccinecontents where idDetail = ?";
//            PreparedStatement pst = DBConnect.get(sql);
//            ResultSet rs = pst.executeQuery();
//            pst.setInt(1, id);
//
//            // Duyệt qua kết quả và ánh xạ dữ liệu vào đối tượng VaccineContents
//            while (rs.next()) {
//                int idDetail = rs.getInt("idDetail");
//                String origin = rs.getString("origin");
//                String administrationRoute = rs.getString("administrationRoute");
//                String contraindications = rs.getString("contraindications");
//                String precaution = rs.getString("precaution");
//                String drugInteractions = rs.getString("drugInteractions");
//                String sideEffects = rs.getString("sideEffects");
//
//                // Tạo đối tượng VaccineContents và thêm vào danh sách
//                VaccineContents vaccineContent = new VaccineContents(idDetail, origin, administrationRoute, contraindications,
//                        precaution, drugInteractions, sideEffects);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.VacccineDetails;
import com.example.provide_vaccine_services.dao.model.VaccineContents;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacccineDetailDao {
    public VacccineDetails getVaccineDetailsById(int id) {
        VacccineDetails vaccineDetails = null;
        try {
            String sql = "SELECT * FROM vaccinedetails WHERE idVaccine = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int idVaccine = rs.getInt("idVaccine");
                String targetGroup = rs.getString("targetGroup");
                String immunization = rs.getString("immunization");
                String adverseReactions = rs.getString("adverseReactions");

                vaccineDetails = new VacccineDetails(idVaccine, targetGroup, immunization, adverseReactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccineDetails;
    }

    public int insert(VacccineDetails vd) {
        int id = -1;

        try {
            // Câu lệnh SQL để chèn dữ liệu vào bảng vaccine_details
            String sql = "INSERT INTO vaccinedetails(idVaccine, targetGroup, adverseReactions, immunization) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);

            // Gán giá trị từ đối tượng VacccineDetails
            pst.setInt(1, vd.getIdVaccine());
            pst.setString(2, vd.getTargetGroup());
            pst.setString(3, vd.getAdverseReactions());
            pst.setString(4, vd.getImmunization());

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

    public List<VacccineDetails> getAllVaccineDetails() {
        List<VacccineDetails> vaccineDetailsList = new ArrayList<>(); // Danh sách để lưu kết quả
        String sql = "SELECT * FROM vaccinedetails"; // Câu lệnh SQL

        try {
            PreparedStatement pst = DBConnect.getAuto(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) { // Duyệt qua từng dòng kết quả
                int id = rs.getInt("id");
                int idVaccine = rs.getInt("idVaccine");
                String targetGroup = rs.getString("targetGroup");
                String immunization = rs.getString("immunization");
                String adverseReactions = rs.getString("adverseReactions");

                // Khởi tạo đối tượng VaccineDetails
                VacccineDetails vaccineDetails = new VacccineDetails(id, idVaccine, targetGroup, immunization, adverseReactions);

                // Thêm vào danh sách
                vaccineDetailsList.add(vaccineDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có vấn đề xảy ra
        }

        return vaccineDetailsList; // Trả về danh sách kết quả
    }

    public int getIdDetail(int idVaccine) {
        int re = 0;
        String sql = "SELECT id FROM vaccinedetails WHERE idVaccine = ?";

        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idVaccine);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                re = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }


    public boolean updateVaccineDetail(VacccineDetails vd) {
        String sql = "UPDATE vaccinedetails SET targetGroup = ?, immunization = ?, adverseReactions = ? WHERE idVaccine = ?";

        try  {
            PreparedStatement pst = DBConnect.get(sql);
            // Gán giá trị cho các tham số trong câu lệnh SQL
            pst.setString(1, vd.getTargetGroup());
            pst.setString(2, vd.getImmunization());
            pst.setString(3, vd.getAdverseReactions());
            pst.setInt(4, vd.getIdVaccine() );

            // Thực thi lệnh cập nhật
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
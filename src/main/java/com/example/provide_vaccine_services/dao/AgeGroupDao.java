
package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.AgeGroups;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AgeGroupDao {
    public AgeGroups getAgeById(int idVaccine) {
        AgeGroups re = null;
        try {
            String sql = "SELECT ag.id " +
                    "FROM vaccines v " +
                    "JOIN vaccinetypes vt ON vt.idVaccine = v.id " +
                    "JOIN agegroups ag ON ag.id = vt.idAgeGroup " +
                    "WHERE v.id = ?";

            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idVaccine);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                re = new AgeGroups();
                re.setId(rs.getInt("id"));
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return re;
        }
    }

    public List<AgeGroups> getAgeGroups() {
        ArrayList<AgeGroups> re = new ArrayList<>();
        try {
            String sql = "select * from agegroups";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                AgeGroups age = new AgeGroups(id, name);
                re.add(age);
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int insert(AgeGroups s) {
        int re = 0;

        try {
            String sql = "insert into agegroups(name) values(?)";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, s.getName());

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

    public boolean updateAgeGroup(AgeGroups ag) {
        String sql = "UPDATE agegroups SET name = ? WHERE id = ?";

        try  {
            PreparedStatement pst = DBConnect.get(sql);
            // Gán giá trị cho các tham số trong câu lệnh SQL
            pst.setString(1, ag.getName());
            pst.setInt(2, ag.getId());

            // Thực thi lệnh cập nhật
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xử lý cho gói vắc xin hiện danh sách
    public List<AgeGroups> getVaccinePackagesByAge() {
        List<AgeGroups> ageGroups = new ArrayList<>();
        String query = "SELECT ag.id AS age_id, ag.name AS age_group, " +
                "vp.id AS package_id, vp.name AS package_name, " +
                "vp.description AS package_description, " +
                "SUM(v.price*vpm.dosage) AS total_price " +
                "FROM packageages pa " +
                "JOIN agegroups ag ON pa.idAge = ag.id " +
                "JOIN vaccinepackages vp ON pa.idPackage = vp.id " +
                "JOIN vaccinepmappings vpm ON vp.id = vpm.idPackage " +
                "JOIN vaccines v ON vpm.idVaccine = v.id " +
                "GROUP BY ag.id, vp.id " +
                "ORDER BY ag.id, vp.id;";

        try {
            PreparedStatement stmt = DBConnect.get(query);
            ResultSet rs = stmt.executeQuery();

            int currentAgeId = -1;
            AgeGroups currentGroup = null;
            List<VaccinePackages> currentPackages = null;

            while (rs.next()) {
                int ageId = rs.getInt("age_id");
                String ageName = rs.getString("age_group");
                int packageId = rs.getInt("package_id");
                String packageName = rs.getString("package_name");
                String packageDescription = rs.getString("package_description");
                float totalPrice = rs.getFloat("total_price"); // Tổng giá của gói

                // Nếu là nhóm tuổi mới, khởi tạo đối tượng mới
                if (ageId != currentAgeId) {
                    if (currentGroup != null) {
                        currentGroup.setPackages(currentPackages); // Lưu danh sách gói vắc xin vào nhóm tuổi
                        ageGroups.add(currentGroup);
                    }
                    currentGroup = new AgeGroups(ageId, ageName);
                    currentPackages = new ArrayList<>();
                    currentAgeId = ageId;
                }

                // Thêm gói vắc xin vào danh sách của nhóm tuổi hiện tại
                currentPackages.add(new VaccinePackages(packageId, packageName, packageDescription, totalPrice));
            }

            // Thêm nhóm cuối cùng vào danh sách
            if (currentGroup != null) {
                currentGroup.setPackages(currentPackages); // Lưu danh sách gói vắc xin vào nhóm tuổi
                ageGroups.add(currentGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ageGroups;
    }
}

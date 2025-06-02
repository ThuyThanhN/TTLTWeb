package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.VaccinePackages;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaccinePackageDao {
    public int insert(VaccinePackages vp) {
        int id = -1;

        try {
            String sql = "insert into vaccinepackages(name, description, totalPrice) values(?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);
            pst.setString(1, vp.getName());
            pst.setString(2, vp.getDescription());
            pst.setFloat(3, vp.getTotalPrice());

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

    public List<VaccinePackages> getAll() {
        ArrayList<VaccinePackages> re = new ArrayList<>();
        try {
            String sql = "select * from vaccinepackages";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float totalPrice = rs.getFloat("totalPrice");

                VaccinePackages vp = new VaccinePackages(id, name, description, totalPrice);
                re.add(vp);
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //  Lay ten goi, gia goi,... trong table-data-vax-package
    public List<Map<String, Object>> getPackage() {
        List<Map<String, Object>> packageList = new ArrayList<>();
        Map<Integer, Map<String, Object>> packageMap = new HashMap<>();

        String sql = "SELECT vp.id AS package_id, " +
                "vp.name AS package_name, " +
                "vp.description AS package_description, " +
                "vpm.dosage as vaccine_dosage, " +
                "vp.totalPrice AS package_total_price, " +
                "v.id AS vaccine_id, " +
                "v.name AS vaccine_name, " +
                "v.price AS vaccine_price, " +
                "ag.id AS age_id " +
                "FROM vaccinepackages vp " +
                "JOIN vaccinepmappings vpm ON vp.id = vpm.idPackage " +
                "JOIN vaccines v ON vpm.idVaccine = v.id " +
                "JOIN packageages pg ON pg.idPackage = vp.id " +
                "JOIN agegroups ag ON ag.id = pg.idAge";

        try (PreparedStatement pst = DBConnect.get(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int packageId = rs.getInt("package_id");

                // Kiểm tra xem gói vắc xin đã tồn tại trong map chưa
                Map<String, Object> packageInfo = packageMap.get(packageId);
                if (packageInfo == null) {
                    packageInfo = new HashMap<>();
                    packageInfo.put("package_id", packageId);
                    packageInfo.put("package_name", rs.getString("package_name"));
                    packageInfo.put("package_description", rs.getString("package_description"));
                    packageInfo.put("vaccines", new ArrayList<Map<String, Object>>());
                    packageInfo.put("total_price", rs.getFloat("package_total_price"));
                    packageInfo.put("age_id", rs.getInt("age_id"));
                    packageInfo.put("vaccine_ids", new ArrayList<Integer>());  // Danh sách vaccine_ids
                    packageInfo.put("vaccine_dosage_map", new HashMap<Integer, Object>());  // Danh sách vaccine_ids tương ứng với dosage
                    packageMap.put(packageId, packageInfo);
                }

                // Thêm thông tin vắc xin vào danh sách vắc xin
                List<Map<String, Object>> vaccines = (List<Map<String, Object>>) packageInfo.get("vaccines");
                Map<String, Object> vaccineInfo = new HashMap<>();
                int vaccineId = rs.getInt("vaccine_id");
                vaccineInfo.put("vaccine_id", vaccineId);
                vaccineInfo.put("vaccine_name", rs.getString("vaccine_name"));
                vaccineInfo.put("vaccine_price", rs.getFloat("vaccine_price"));
                vaccines.add(vaccineInfo);

                // Thêm vaccine_id vào danh sách vaccine_ids
                List<Integer> vaccineIds = (List<Integer>) packageInfo.get("vaccine_ids");

                // Thêm dosage
                Map<Integer, Object> vxDosageMap = (Map<Integer, Object>) packageInfo.get("vaccine_dosage_map");
                vxDosageMap.put(vaccineId, rs.getInt("vaccine_dosage"));

                vaccineIds.add(vaccineId);
            }

            packageList.addAll(packageMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packageList;
    }

    public int delete(int packageId) {
        int result = 0;

        String sqlVp = "delete from vaccinepackages WHERE id = ?";
        String sqlPa = "delete from packageages WHERE idPackage = ?";
        String sqlVpm = "delete from vaccinepmappings WHERE idPackage = ?";

        try {
            PreparedStatement pstVp = DBConnect.get(sqlVp);
            pstVp.setInt(1, packageId);

            PreparedStatement pstPa = DBConnect.get(sqlPa);
            pstPa.setInt(1, packageId);

            PreparedStatement pstVpm = DBConnect.get(sqlVpm);
            pstVpm.setInt(1, packageId);

            result += pstVpm.executeUpdate();
            result += pstPa.executeUpdate();
            result += pstVp.executeUpdate();

            if(result > 0) {
                System.out.println("Xoa du lieu thanh cong!");
            } else {
                System.out.println("Xoa du lieu that bai");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int update(int packageId, String packageName, List<Integer> vaccineIds,
                      List<Integer> dosages, int ageId, float totalPrice, String description) {
        int result = 0;

        try {
            // 1. Cập nhật tên gói
            String sqlUpdatePackage = "UPDATE vaccinepackages SET name = ?, totalPrice = ?, description = ?  WHERE id = ?";
            try (PreparedStatement stmt = DBConnect.get(sqlUpdatePackage)) {
                stmt.setString(1, packageName);
                stmt.setFloat(2, totalPrice);
                stmt.setString(3, description);
                stmt.setInt(4, packageId);
                result += stmt.executeUpdate();
            }

            // 2. Cập nhật độ tuôi
            String sqlUpdateAge = "UPDATE packageages SET idAge = ? WHERE idPackage = ?";
            try (PreparedStatement stmt = DBConnect.get(sqlUpdateAge)) {
                stmt.setInt(1, ageId);
                stmt.setInt(2, packageId);
                result += stmt.executeUpdate();
            }

            // 3. Xóa tất cả các vắc xin hiện tại của gói
            String sqlDeleteVaccines = "DELETE FROM vaccinepmappings WHERE idPackage = ?";
            try (PreparedStatement stmt = DBConnect.get(sqlDeleteVaccines)) {
                stmt.setInt(1, packageId);
                stmt.executeUpdate();
            }

            // 4. Thêm lại các vắc xin đã chọn
            String sqlInsertVaccines = "INSERT INTO vaccinepmappings (idVaccine, idPackage, dosage) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = DBConnect.get(sqlInsertVaccines)) {
                for (int i = 0; i < vaccineIds.size(); i++) {
                    stmt.setInt(1, vaccineIds.get(i));
                    stmt.setInt(2, packageId);
                    stmt.setInt(3, dosages.get(i));
                    stmt.addBatch(); // Tối ưu hóa với batch
                }
                int[] batchResults = stmt.executeBatch();
                result += batchResults.length;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getPackageName(int packageId) {
        String result = "";

        try {
            String sql = "select name AS packageName from vaccinepackages where id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, packageId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                result = rs.getString("packageName");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Integer> getVaccineIdsByPackageId(int packageId) {
        List<Integer> vaccineIds = new ArrayList<>();
        // Thực hiện query lấy danh sách id vaccine trong gói packageId từ bảng liên kết
        // ví dụ: SELECT idVaccine FROM package_vaccine WHERE idPackage = ?
        // thêm vào vaccineIds
        String sql = "select vaccine_id from vaccinepackages where idPackage = ?";

        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, packageId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                vaccineIds.add(rs.getInt("vaccine_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vaccineIds;
    }

    public boolean isValidpackage(int id) {

        String sql = "select * from vaccinepackages vp join vaccinepmappings vm on vp.id = vm.idPackage join vaccines v on v.id = vm.idVaccine" +
                " where idPackage = ? AND v.stockQuantity <= 0";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;


    }

    public VaccinePackages getpackageById(int id) {
        String sql = "select * from vaccinepackages where id = ?";
        VaccinePackages result = null;
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int vpid = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float totalPrice = rs.getFloat("totalPrice");
                result = new VaccinePackages(vpid, name, description, totalPrice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    // Lấy gói vắc xin theo độ tuổi
//    public List<VaccinePackages> getPackagesByAgeGroup(int ageGroupId) {
//        List<VaccinePackages> packages = new ArrayList<>();
//        String sql = "SELECT vp.id, vp.name, vp.description " +
//                "FROM VaccinePackages vp " +
//                "JOIN VaccinePMappings vpm ON vp.id = vpm.idPackage " +
//                "JOIN VaccineTypes vt ON vpm.idVaccine = vt.idVaccine " +
//                "WHERE vt.idAgeGroup = ?";
//
//        try  {
//            PreparedStatement pst = DBConnect.get(sql);
//            pst.setInt(1, ageGroupId); // set the ageGroupId to the query
//            ResultSet rs = pst.executeQuery();
//
//            while (rs.next()) {
//                VaccinePackages packageItem = new VaccinePackages();
//                packageItem.setId(rs.getInt("id"));
//                packageItem.setName(rs.getString("name"));
//                packageItem.setDescription(rs.getString("description"));
//                packages.add(packageItem);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return packages;
//    }
}

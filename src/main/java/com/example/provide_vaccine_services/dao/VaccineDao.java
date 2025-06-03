package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.VaccineContents;
import com.example.provide_vaccine_services.dao.model.VaccineDetails;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class VaccineDao {
    public List<Vaccines> getAll() {
        List<Vaccines> vaccines = new ArrayList<>();

        try {
            String sql = "select * from vaccines";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");

                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);

                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }

    // Xoa vac xin voi tham so id của supplier
    public int deleteByIdSupplier(int idS) {
        int re = 0;

        try {
            // Xoa orderdeatail lien quan toi nha cung cap
            String deleteOrderDetailsSql = "DELETE FROM orderdetails " +
                    "WHERE idVaccine IN (" +
                    "SELECT id FROM vaccines " +
                    "WHERE idSupplier = ?)";
            PreparedStatement pstOrderDetails = DBConnect.get(deleteOrderDetailsSql);
            pstOrderDetails.setInt(1, idS);
            pstOrderDetails.executeUpdate();

            // Xoa vac xin
            String deleteVaccineSql = "DELETE FROM vaccines WHERE idSupplier = ?";
            PreparedStatement pstVaccine = DBConnect.get(deleteVaccineSql);
            pstVaccine.setInt(1, idS);
            re = pstVaccine.executeUpdate();

            return re;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    // Xoa vac xin voi tham so id của vaccine
    public int deleteByIdVaccine(int idVaccine) {
        int re = 0;

        try {
            // Xoa cac ban ghi trong bang vaccinepmappings lien quan den vaccine
            String deleteVaccineMappingSql = "DELETE FROM vaccinepmappings WHERE idVaccine = ?";
            PreparedStatement pstVaccineMapping = DBConnect.get(deleteVaccineMappingSql);
            pstVaccineMapping.setInt(1, idVaccine);
            pstVaccineMapping.executeUpdate();

            // Xoa cac ban ghi trong bang vaccinetypes lien quan den vaccine
            String deleteVaccineTypeSql = "DELETE FROM vaccinetypes WHERE idVaccine = ?";
            PreparedStatement pstVaccineType = DBConnect.get(deleteVaccineTypeSql);
            pstVaccineType.setInt(1, idVaccine);
            pstVaccineType.executeUpdate();

            // Xoa cac ban ghi trong bang vaccinecontents lien quan den vaccine detail
            String deleteVaccineContentsSql = "DELETE FROM vaccinecontents " +
                    "WHERE idDetail IN (" +
                    "SELECT id FROM vaccinedetails " +
                    "WHERE idVaccine = ?)";
            PreparedStatement pstVaccineContents = DBConnect.get(deleteVaccineContentsSql);
            pstVaccineContents.setInt(1, idVaccine);
            pstVaccineContents.executeUpdate();

            // Xoa cac ban ghi trong bang vaccinedetails lien quan den vaccine
            String deleteVaccineDetailsSql = "DELETE FROM vaccinedetails WHERE idVaccine = ?";
            PreparedStatement pstVaccineDetails = DBConnect.get(deleteVaccineDetailsSql);
            pstVaccineDetails.setInt(1, idVaccine);
            pstVaccineDetails.executeUpdate();

            // Xoa cac ban ghi trong bang orderdetails lien quan den vaccine
            String deleteOrderDetailsSql = "DELETE FROM orderdetails WHERE idVaccine = ?";
            PreparedStatement pstOrderDetails = DBConnect.get(deleteOrderDetailsSql);
            pstOrderDetails.setInt(1, idVaccine);
            pstOrderDetails.executeUpdate();

            // Xoa vaccine
            String deleteVaccineSql = "DELETE FROM vaccines WHERE id = ?";
            PreparedStatement pstVaccine = DBConnect.get(deleteVaccineSql);
            pstVaccine.setInt(1, idVaccine);
            re = pstVaccine.executeUpdate();

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


    public int insert(Vaccines v) {
        int newId = -1;

        try {
            String sql = "insert into vaccines(idSupplier, name, description, stockQuantity, price, imageUrl, status, createdAt, prevention) " +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.getAuto(sql);
            pst.setInt(1, v.getIdSupplier());
            pst.setString(2, v.getName());
            pst.setString(3, v.getDescription());
            pst.setInt(4, v.getStockQuantity());
            pst.setFloat(5, v.getPrice());
            pst.setString(6, v.getImageUrl());
            pst.setString(7, v.getStatus());
            pst.setTimestamp(8, Timestamp.valueOf(v.getCreatedAt()));
            pst.setString(9, v.getPrevention());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                // lay id moi nhat
                String getIdSql = "SELECT MAX(id) FROM vaccines";
                PreparedStatement getIdStmt = DBConnect.get(getIdSql);
                ResultSet rs = getIdStmt.executeQuery();
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

    public Vaccines getVaccineById(int id) {
        Vaccines vaccine = null;

        try {
            String sql = "SELECT * FROM vaccines WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createdAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");

                vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createdAt, prevention);
            }
        } catch (SQLException e) {
            e.printStackTrace(); //
        }

        return vaccine;
    }

    public String getVaccineName(int vaccineId) {
        String result = "";

        try {
            String sql = "select name AS vaccineName, price AS price from vaccines where id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, vaccineId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String vaccineName = rs.getString("vaccineName");
                float price = rs.getFloat("price");

                // Nối chuỗi với tab
                result = vaccineName + " - " + price + " đ";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // update so luong
    public void updateQuantity(int vaccineId, int delta) {
        String sql = "UPDATE vaccines SET stockQuantity = stockQuantity + ? WHERE id = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, delta);
            pst.setInt(2, vaccineId);
            pst.executeUpdate();

            VaccineDao vaccineDao = new VaccineDao();
            Vaccines v = vaccineDao.getVaccineById(vaccineId);
            if(v.getStockQuantity() == 0) {
                vaccineDao.setStatus(vaccineId, "Hết hàng");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(int vaccineId, String status) {

        String sql = "UPDATE vaccines SET status = ? WHERE id = ?";

        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, status);
            pst.setInt(2, vaccineId);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    // Phương thức cập nhật thông tin vắc xin
    public boolean updateVaccine(Vaccines vaccine) {
        String sql = "UPDATE vaccines SET idSupplier = ?, name = ?, description = ?, stockQuantity = ?, "
                + "price = ?, imageUrl = ?, status = ?, prevention = ?, createdAt = ? WHERE id = ?";

        try {
            PreparedStatement pst = DBConnect.get(sql);
            // Gán giá trị cho các tham số trong câu lệnh SQL
            pst.setInt(1, vaccine.getIdSupplier());
            pst.setString(2, vaccine.getName());
            pst.setString(3, vaccine.getDescription());
            pst.setInt(4, vaccine.getStockQuantity());
            pst.setFloat(5, vaccine.getPrice());
            pst.setString(6, vaccine.getImageUrl());
            pst.setString(7, vaccine.getStatus());
            pst.setString(8, vaccine.getPrevention());
            pst.setTimestamp(9, Timestamp.valueOf(vaccine.getCreatedAt()));
            pst.setInt(10, vaccine.getId());

            // Thực thi lệnh cập nhật
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Vaccines> getVaccinesByPage(int page) {
        List<Vaccines> vaccines = new ArrayList<>();
        String sql = "SELECT * FROM vaccines LIMIT 12 OFFSET ?;";

        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setInt(1, (page - 1) * 12);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");

                // Thêm vaccine vào danh sách
                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);

                vaccines.add(vaccine);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vaccines;

    }


    public List<Vaccines> getVaccinesByPage(int page, boolean age, boolean disease) {
        List<Vaccines> vaccines = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT v.* FROM vaccines v");

        if (age) {
            sql.append(" JOIN vaccinetypes vt ON v.id = vt.idVaccine ")
                    .append(" JOIN agegroups ag ON vt.idAgeGroup = ag.id ");
        }

        if (disease) {
            sql.append(age ? " JOIN disasegroups dt ON vt.idDisaseGroup = dt.id " : " JOIN vaccinetypes vt ON v.id = vt.idVaccine JOIN disasegroups dt ON vt.idDisaseGroup = dt.id ");
        }

        if (age && disease) {
            sql.append(" ORDER BY ag.id, dt.id ");
        } else if (age) {
            sql.append(" ORDER BY ag.id ");
        } else if (disease) {
            sql.append(" ORDER BY dt.id ");
        }

        sql.append(" LIMIT 12 OFFSET ?");

        try (PreparedStatement pst = DBConnect.get(sql.toString())) {
            pst.setInt(1, (page - 1) * 12);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;
                String prevention = rs.getString("prevention");

                Vaccines vaccine = new Vaccines(id, idSupplier, name, description, stockQuantity, price, imageUrl, status, createAt, prevention);
                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vaccines;
    }

    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM Vaccines";

        try (PreparedStatement pst = DBConnect.get(sql)) {

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTotalCount(boolean age, boolean disease) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM vaccines v");

        if (age) {
            sql.append(" JOIN vaccinetypes vt ON v.id = vt.idVaccine ")
                    .append(" JOIN agegroups ag ON vt.idAgeGroup = ag.id ");
        }

        if (disease) {
            sql.append(age ? " JOIN disasegroups dt ON vt.idDisease = dt.id " : " JOIN vaccinetypes vt ON v.id = vt.idVaccine JOIN disasegroups dt ON vt.idDisaseGroup = dt.id ");
        }

        try (PreparedStatement pst = DBConnect.get(sql.toString())) {
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public int getTotalCount(String searchQuery) {
        String sql = "SELECT COUNT(*) FROM Vaccines WHERE name LIKE ?";

        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTotalCount(String searchQuery, boolean age, boolean disease) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM vaccines v");

        if (age) {
            sql.append(" JOIN vaccinetypes vt ON v.id = vt.idVaccine ")
                    .append(" JOIN agegroups ag ON vt.idAgeGroup = ag.id ");
        }

        if (disease) {
            sql.append(age ? " JOIN diseasetypes dt ON vt.idDisease = dt.id " : " JOIN vaccinetypes vt ON v.id = vt.idVaccine JOIN diseasetypes dt ON vt.idDisease = dt.id ");
        }

        sql.append(" WHERE v.name LIKE ? ");

        try (PreparedStatement pst = DBConnect.get(sql.toString())) {
            pst.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public List<Vaccines> searchByName(String searchQuery) {
        List<Vaccines> vaccines = new ArrayList<>();
        String sql = "SELECT * FROM vaccines WHERE name LIKE ?";

        try (PreparedStatement stmt = DBConnect.get(sql)) {
            stmt.setString(1, "%" + searchQuery + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");

                // Thêm vaccine vào danh sách
                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);

                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }

    public List<Vaccines> getSearchedVaccinesByPage(String searchQuery, int page, boolean age, boolean disease) {
        List<Vaccines> vaccines = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT v.* FROM vaccines v");

        System.out.println(age);

        if (age) {
            sql.append(" JOIN vaccinetypes vt ON v.id = vt.idVaccine ")
                    .append(" JOIN agegroups ag ON vt.idAgeGroup = ag.id ");
        }

        if (disease) {
            sql.append(age ? " JOIN diseasetypes dt ON vt.idDisease = dt.id " : " JOIN vaccinetypes vt ON v.id = vt.idVaccine JOIN diseasetypes dt ON vt.idDisease = dt.id ");
        }

        sql.append(" WHERE v.name LIKE ? ");

        if (age && disease) {
            sql.append(" ORDER BY ag.id, dt.id ");
        } else if (age) {
            sql.append(" ORDER BY ag.id ");
        } else if (disease) {
            sql.append(" ORDER BY dt.id ");
        }

        sql.append(" LIMIT 12 OFFSET ?");

        try (PreparedStatement stmt = DBConnect.get(sql.toString())) {
            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setInt(2, (page - 1) * 12);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;
                String prevention = rs.getString("prevention");

                Vaccines vaccine = new Vaccines(id, idSupplier, name, description, stockQuantity, price, imageUrl, status, createAt, prevention);
                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vaccines;
    }

    public List<String> getAutoCompleteSuggestions(String query) {
        List<String> suggestions = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM vaccines WHERE name LIKE ? LIMIT 5";

        System.out.println(query);

        try (PreparedStatement stmt = DBConnect.get(sql)) {
            stmt.setString(1, "%" + query + "%");

            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                suggestions.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suggestions;
    }

    public List<Vaccines> getTopVaccines() {
        List<Vaccines> vaccines = new ArrayList<>();
        try {
            String sql = "SELECT v.*, " +
                    "IFNULL(COUNT(od.idOrder), 0) AS orderCount " +
                    "FROM vaccines v " +
                    "LEFT JOIN orderdetails od ON v.id = od.idVaccine " +
                    "GROUP BY v.id " +
                    "ORDER BY orderCount DESC " +
                    "LIMIT 8"; // Lấy 8 vắc xin có nhiều lượt đặt nhất
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");
                int orderCount = rs.getInt("orderCount"); // Lấy số lượt đặt


                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);
                vaccine.setOrderCount(orderCount); // Gán số lượt đặt thực tế

                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }

    public List<Vaccines> getRandomVaccines() {
        List<Vaccines> vaccines = new ArrayList<>();
        try {
            String sql = "SELECT * FROM vaccines ORDER BY RAND() LIMIT 8";  // Lấy ngẫu nhiên 8 vaccine
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");


                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);

                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }


    public List<Vaccines> getByAgeGroup(String searchQuery, int page) {
        List<Vaccines> vaccines = new ArrayList<>();
        String sql = "SELECT v.* FROM vaccines v " +
                "JOIN vaccinetypes vt ON v.id = vt.idVaccine " +
                "JOIN agegroups ag ON vt.idAgeGroup = ag.id " +
                "WHERE v.name LIKE ? " +
                "LIMIT 12 OFFSET ?";

        try (PreparedStatement stmt = DBConnect.get(sql)) {
            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setInt(2, (page - 1) * 12);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");

                // Thêm vaccine vào danh sách
                // Thêm vaccine vào danh sách
                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);

                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }


    public List<Vaccines> getByDiseaseGroup() {
        List<Vaccines> vaccines = new ArrayList<>();
        String sql = "SELECT v.* FROM vaccines v " +
                "JOIN vaccinetypes vt ON v.id = vt.idVaccine " +
                "JOIN disasegroups dg ON vt.idDisaseGroup = dg.id";

        try (PreparedStatement stmt = DBConnect.get(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSupplier = rs.getInt("idSupplier");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");

                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("createdAt");
                LocalDateTime createAt = timestamp != null ? timestamp.toLocalDateTime() : null;

                String prevention = rs.getString("prevention");

                // Thêm vaccine vào danh sách
                Vaccines vaccine = new Vaccines(id, idSupplier, name, description,
                        stockQuantity, price, imageUrl, status, createAt, prevention);

                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }

    public List<Vaccines> getAlltable(String sort) {
        ArrayList<Vaccines> list = new ArrayList<>();
        try {
            // Câu SQL với JOIN để lấy countryOfOrigin từ bảng supplier và prevention từ bảng vaccines
            String sql = "SELECT v.id, v.name, v.description, v.stockQuantity, v.price, v.imageUrl, v.status, v.createdAt, v.prevention, s.countryOfOrigin " +
                    "FROM vaccines v JOIN suppliers s ON v.idSupplier = s.id";

            // Nếu có tham số sort, thêm ORDER BY
            if (sort != null) {
                String[] param = sort.split(":");
                if (param.length == 2) {
                    String key = param[0];
                    String value = param[1];
                    // Kiểm tra tính hợp lệ của key và value
                    if (Arrays.asList("price", "createdAt").contains(key) &&
                            Arrays.asList("asc", "desc").contains(value)) {
                        sql += String.format(" ORDER BY %s %s", key, value);
                    } else {
                        System.err.println("Invalid sort parameter: " + sort);
                    }
                }
            }

            // Debug: In câu SQL
            System.out.println("Executing SQL: " + sql);

            // Thực thi truy vấn
            PreparedStatement pst = DBConnect.get(sql);  // Cách bạn đã yêu cầu
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Lấy thông tin vaccine
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int stockQuantity = rs.getInt("stockQuantity");
                float price = rs.getFloat("price");
                String imageUrl = rs.getString("imageUrl");
                String status = rs.getString("status");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                String prevention = rs.getString("prevention");  // Lấy phòng bệnh từ bảng vaccines
                String countryOfOrigin = rs.getString("countryOfOrigin");  // Lấy quốc gia xuất xứ từ bảng suppliers

                // Tạo đối tượng vaccine với prevention và countryOfOrigin
                Vaccines vaccines = new Vaccines(id, name, description, stockQuantity, price, imageUrl, status, createdAt, prevention, countryOfOrigin);

                list.add(vaccines);
            }
        } catch (SQLException e) {
            System.err.println("Error while querying getAll: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }


    public Map<String, Object> getVaccineDetailsById(int id) {
        VaccineContents vaccineContents = null;
        VaccineDetails vaccineDetails = null;

        // Cập nhật SQL để lấy imageUrl từ bảng vaccines
        String sql = "SELECT vc.*, v.name, v.description, v.imageUrl, " +
                "vd.targetGroup, vd.immunization, vd.adverseReactions " +
                "FROM vaccinecontents vc " +
                "LEFT JOIN vaccinedetails vd ON vc.idDetail = vd.id " +
                "LEFT JOIN vaccines v ON vc.id = v.id " +
                "WHERE vc.id = ?";

        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Lấy thêm imageUrl từ database
                    vaccineContents = new VaccineContents(
                            rs.getInt("id"),
                            rs.getInt("idDetail"),
                            rs.getString("origin"),
                            rs.getString("administrationRoute"),
                            rs.getString("contraindications"),
                            rs.getString("precaution"),
                            rs.getString("drugInteractions"),
                            rs.getString("sideEffects"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("imageUrl") //
                    );

                    vaccineDetails = new VaccineDetails(
                            rs.getInt("idDetail"),
                            rs.getInt("id"),
                            rs.getString("targetGroup"),
                            rs.getString("immunization"),
                            rs.getString("adverseReactions")
                    );

                    // Debug kiểm tra giá trị imageUrl
                    System.out.println("Image URL from DB: " + vaccineContents.getImageUrl());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về cả hai đối tượng bằng Map
        Map<String, Object> result = new HashMap<>();
        result.put("vaccineContents", vaccineContents);
        result.put("vaccineDetails", vaccineDetails);
        return result;
    }

    public List<Map<String, Object>> export() {
        List<Map<String, Object>> vaccineList = new ArrayList<>();

        try {
            String sql = "SELECT v.id, v.name, s.countryOfOrigin, v.status, v.price, v.description " +
                    "FROM vaccines v " +
                    "JOIN suppliers s ON v.idSupplier = s.id " +
                    "ORDER BY v.id";

            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Map<String, Object> vaccineData = new HashMap<>();
                vaccineData.put("id", rs.getInt("id")); // Bổ sung ID để có thể sử dụng map() trong JS
                vaccineData.put("name", rs.getString("name"));
                vaccineData.put("countryOfOrigin", rs.getString("countryOfOrigin"));
                vaccineData.put("status", rs.getString("status"));
                vaccineData.put("price", rs.getFloat("price"));
                vaccineData.put("description", rs.getString("description"));

                vaccineList.add(vaccineData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccineList;
    }

    // Đếm số vắc xin còn hàng
    public int countInStock() {
        String sql = "SELECT COUNT(*) FROM vaccines WHERE stockQuantity > 0";
        try (PreparedStatement pst = DBConnect.get(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm số vắc xin hết hàng
    public int countOutOfStock() {
        String sql = "SELECT COUNT(*) FROM vaccines WHERE stockQuantity <= 0";
        try (PreparedStatement pst = DBConnect.get(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
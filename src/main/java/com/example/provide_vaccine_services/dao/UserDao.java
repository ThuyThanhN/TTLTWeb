package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Users;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDao {
    private Users u;

    // Them
    public int insert(Users u) {
        int re = 0;

        // Sử dụng try-with-resources để đảm bảo tài nguyên được đóng đúng cách
        try (PreparedStatement pst = DBConnect.get("INSERT INTO users (fullname, gender, identification, dateOfBirth, " +
                "address, province, district, ward, phone, email, password, role, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            // Set các tham số
            pst.setString(1, u.getFullname());
            pst.setString(2, u.getGender());
            pst.setString(3, u.getIdentification());
            pst.setDate(4, u.getDateOfBirth());
            pst.setString(5, u.getAddress());
            pst.setString(6, u.getProvince());
            pst.setString(7, u.getDistrict());
            pst.setString(8, u.getWard());
            pst.setString(9, u.getPhone());
            pst.setString(10, u.getEmail());
            pst.setString(11, u.getPassword());
            pst.setInt(12, u.getRole());  // Gán giá trị cho cột role
            pst.setInt(13, u.getStatus());  // Gán giá trị cho cột status

            // Thực thi câu lệnh SQL
            re = pst.executeUpdate();

            // Kiểm tra kết quả và thông báo
            if (re > 0) {
                System.out.println("Thêm dữ liệu thành công");
            } else {
                System.out.println("Thêm dữ liệu thất bại!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }


    // Cap nhat thong tin
    public int update(Users u) {
        int re = 0;

        try {
            String sql = "UPDATE users SET fullname = ?, gender = ?, identification = ?, dateOfBirth = ?, " +
                    "address = ?, province = ?, district = ?, ward = ?, phone = ?, email = ?, password = ?, role = ? " +
                    "WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);

            pst.setString(1, u.getFullname());
            pst.setString(2, u.getGender());
            pst.setString(3, u.getIdentification());
            pst.setDate(4, u.getDateOfBirth());
            pst.setString(5, u.getAddress());
            pst.setString(6, u.getProvince());
            pst.setString(7, u.getDistrict());
            pst.setString(8, u.getWard());
            pst.setString(9, u.getPhone());
            pst.setString(10, u.getEmail());
            pst.setString(11, u.getPassword());
            pst.setInt(12, u.getRole());
            pst.setInt(13, u.getId());

            re = pst.executeUpdate();

            if (re > 0) {
                System.out.println("Cap nhat du lieu thanh cong");
            } else {
                System.out.println("Cap nhat du lieu that bai!");
            }
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    // Xoa
    public int delete(int idS) {
        int re = 0;

        try {
            String sql = "delete from users where id=?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, idS);

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

    // Lay danh sach bao gom nhan vien va admin
    public List<Users> getAllByStaff() {
        List<Users> re = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users WHERE role IN (1, 2)";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("fullname");
                String gender = rs.getString("gender");
                String identification = rs.getString("identification");
                Date date = rs.getDate("dateOfBirth");
                String address = rs.getString("address");
                String province = rs.getString("province");
                String district = rs.getString("district");
                String ward = rs.getString("ward");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                int role = rs.getInt("role");

                Users user = new Users(id, name, gender, identification, date, address, province, ward, district, phone, email, pass, role);

                re.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    // Lay danh sach khach hang
    public List<Users> getAllByCus() {
        List<Users> re = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users WHERE role = 0";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("fullname");
                String gender = rs.getString("gender");
                String identification = rs.getString("identification");
                Date date = rs.getDate("dateOfBirth");
                String address = rs.getString("address");
                String province = rs.getString("province");
                String district = rs.getString("district");
                String ward = rs.getString("ward");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                int role = rs.getInt("role");

                Users user = new Users(id, name, gender, identification, date, address, district, ward, province, phone, email, pass, role);

                re.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public int totalUser() {
        int result = 0;

        try {
            String sql = "SELECT COUNT(*) AS total FROM users where role = 0";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int insertUser(Users u) {
        int re = 0;

        try {
            // Câu lệnh SQL để chèn dữ liệu vào bảng users
            String sql = "INSERT INTO users(fullname, gender, identification, dateOfBirth, address, province, district, ward, phone, email, password, role) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);

            // Gán các giá trị từ đối tượng `Users`
            pst.setString(1, u.getFullname());
            pst.setString(2, u.getGender());
            pst.setString(3, u.getIdentification());
            pst.setDate(4, u.getDateOfBirth());
            pst.setString(5, u.getAddress());
            pst.setString(6, u.getProvince());
            pst.setString(7, u.getDistrict());
            pst.setString(8, u.getWard());
            pst.setString(9, u.getPhone());
            pst.setString(10, u.getEmail());

            String rawPassword = u.getPassword();
            String hashedPassword = MD5Hash.hashPassword(rawPassword);


            // Debug mật khẩu
//            System.out.println("Password trước khi mã hóa: " + rawPassword);
//            System.out.println("Password sau khi mã hóa: " + hashedPassword);

            pst.setString(11, u.getPassword());

            pst.setInt(12, u.getRole());

            pst.setInt(12, u.getRole());

            System.out.println("sql: " + sql);
            System.out.println("pst: " + pst);


            // Thực hiện câu lệnh và kiểm tra kết quả
            re = pst.executeUpdate();


            if (re > 0) {
                System.out.println("Them du lieu thanh cong");

            } else {
                System.out.println("Them du lieu that bai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return re; // Trả về kết quả (số bản ghi đã được thêm)
    }

    public int insertGGUser(Users u) {
        int re = 0;

        try {
            // Câu lệnh SQL để chèn dữ liệu vào bảng users
            String sql = "INSERT INTO users(fullname, gender, identification, dateOfBirth, address, province, district, ward, phone, email, password, role, status)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = DBConnect.get(sql);

            String s = u.toString();

            System.out.println("user: " + s);

            // Gán các giá trị từ đối tượng `Users`
            pst.setString(1, u.getFullname());
            pst.setString(2, "");
            pst.setString(3, "");
            pst.setDate(4, new Date(System.currentTimeMillis())); // thơi gian hiện tại
            pst.setString(5, "");
            pst.setString(6, "");
            pst.setString(7, "");
            pst.setString(8, "");
            pst.setString(9, "");
            pst.setString(10, u.getEmail());
            pst.setString(11, genPassword());
            pst.setInt(12, u.getRole());
            pst.setInt(13, 1);


            // Thực hiện câu lệnh và kiểm tra kết quả
            re = pst.executeUpdate();

            if (re > 0) {
                System.out.println("Them du lieu thanh cong");

            } else {
                System.out.println("Them du lieu that bai!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return re; // Trả về kết quả (số bản ghi đã được thêm)
    }

    public Users checkLogin(String phone, String password) {
        Users user = null;
        try {
            String sql = "SELECT * FROM users WHERE phone = ? AND password = ? ";
            PreparedStatement pst = DBConnect.get(sql);

            // Mã hóa mật khẩu trước khi so sánh

            pst.setString(1, phone);
            String hashedPassword = MD5Hash.hashPassword(password.trim());
            pst.setString(2, hashedPassword);

            // Debug kiểm tra giá trị đầu vào
            System.out.println("Phone: " + phone);
            System.out.println("Password truoc khi ma hoa: " + password);
            System.out.println("Password ma hoa: " + hashedPassword);


            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                user = new Users(
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("gender"),
                        rs.getString("identification"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getString("province"),
                        rs.getString("district"),
                        rs.getString("ward"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role")
                );

                // Debug khi đăng nhập thành công
                System.out.println("Dang nhap thanh cong cho ID: " + user.getId());
            } else {
                // Debug khi đăng nhập thất bại
                System.out.println("Dang nhap that bai. Khong tim thay tai khoan phu hop.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updatePassword(int userId, String newPassword) {
        boolean isUpdated = false;
        try {
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, newPassword);
            pst.setInt(2, userId);

            int rows = pst.executeUpdate();
            isUpdated = rows > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public int getUserIdByEmail(String email) {
        int userId = -1; // Giá trị mặc định nếu không tìm thấy
        String sql = "SELECT id FROM users WHERE email = ?";

        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id"); // Lấy ID của người dùng từ kết quả truy vấn
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId; // Trả về ID của người dùng (hoặc -1 nếu không tìm thấy)
    }

    public int updateUserDetails(Users user) {
        // Cập nhật câu SQL để bao gồm province, district, ward
        String sql = "UPDATE users SET fullname = ?, identification = ?, phone = ?, gender = ?, dateOfBirth = ?, address = ?, province = ?, district = ?, ward = ? WHERE id = ?";

        try (PreparedStatement pst = DBConnect.get(sql)) {
            // Gán giá trị cho các tham số
            pst.setString(1, user.getFullname());
            pst.setString(2, user.getIdentification());
            pst.setString(3, user.getPhone());
            pst.setString(4, user.getGender());

            // Kiểm tra nếu dateOfBirth là null
            if (user.getDateOfBirth() != null) {
                pst.setDate(5, user.getDateOfBirth());
            } else {
                pst.setNull(5, java.sql.Types.DATE);  // Gán null cho dateOfBirth nếu cần thiết
            }

            // Gán giá trị cho địa chỉ (address)
            pst.setString(6, user.getAddress());

            // Gán giá trị cho province, district, ward
            pst.setString(7, user.getProvince());
            pst.setString(8, user.getDistrict());
            pst.setString(9, user.getWard());

            // Gán giá trị cho ID người dùng
            pst.setInt(10, user.getId());

            // Thực thi câu lệnh
            int rowsUpdated = pst.executeUpdate();

            // Ghi log kết quả
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thành công. ID người dùng: " + user.getId());
            } else {
                System.out.println("Cập nhật thất bại. Không tìm thấy người dùng với ID: " + user.getId());
            }

            return rowsUpdated;
        } catch (SQLException e) {
            // Ghi lỗi chi tiết
            System.err.println("Lỗi khi cập nhật thông tin người dùng với ID: " + user.getId());
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi xảy ra
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement pst = DBConnect.get(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement pst = DBConnect.get(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getString("gender"));
                user.setIdentification(rs.getString("identification"));
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setAddress(rs.getString("address"));
                user.setProvince(rs.getString("province"));
                user.setDistrict(rs.getString("district"));
                user.setWard(rs.getString("ward"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("role"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }


    // tạo mật khẩu ngẫu nhiên
    private String genPassword() {
        Random rand = new Random();
        int randomNum = rand.nextInt(100000, 999999);
        String password = String.valueOf(randomNum);
        return MD5Hash.hashPassword(password);
    }

}

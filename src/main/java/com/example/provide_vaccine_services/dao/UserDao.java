package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.Service.MD5Hash;
import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.Users;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDao {
    private Users u;
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

     // Them nhan vien trong Admin
    public int insertStaff(Users u) {
        int newId = -1;

        try {
            String sql = "insert into users(fullname, gender, identification, dateOfBirth, " +
                    "address, province, district, ward, phone, email, password, role, status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            pst.setInt(13, 1);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                // lay id moi nhat
                String getIdSql = "SELECT MAX(id) FROM users";
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

            pst.setString(11,u.getPassword());

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
    public Users checkLogin(String username, String password) {
        Users user = null;
        try {
            // Kiểm tra xem username là số điện thoại hay email
            String sql = "";
            if (username.contains("@")) {
                // Nếu username chứa dấu "@" thì coi như email
                sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            } else {
                // Nếu không có dấu "@" thì coi như số điện thoại
                sql = "SELECT * FROM users WHERE phone = ? AND password = ?";
            }

            PreparedStatement pst = DBConnect.get(sql);

            // Mã hóa mật khẩu trước khi so sánh
            pst.setString(1, username); // email hoặc số điện thoại
            String hashedPassword = MD5Hash.hashPassword(password.trim());
            pst.setString(2, hashedPassword);

            // Debug kiểm tra giá trị đầu vào
            System.out.println("Username: " + username);
            System.out.println("Password trước khi mã hóa: " + password);
            System.out.println("Password mã hóa: " + hashedPassword);

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
                        rs.getInt("role"),
                        rs.getInt("status")  // Đảm bảo rằng bạn đang lấy đúng giá trị status
                );

                // Debug khi đăng nhập thành công
                System.out.println("Đăng nhập thành công cho ID: " + user.getId());
            } else {
                // Debug khi đăng nhập thất bại
                System.out.println("Đăng nhập thất bại. Không tìm thấy tài khoản phù hợp.");
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
    // Giải mã token trước khi lưu vào cơ sở dữ liệu hoặc kiểm tra
    private String decodeToken(String token) {
        try {
            // Giải mã token từ URL (nếu có ký tự đặc biệt)
            return URLDecoder.decode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return token;  // Trả về token gốc nếu có lỗi giải mã
        }
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

    public boolean updateUserStatusToActive(String token) {
        boolean isUpdated = false;
        try {
            // Câu lệnh SQL để cập nhật trạng thái người dùng
            String sql = "UPDATE users SET status = 1 WHERE verification_token = ?";

            // Lấy PreparedStatement từ DBConnect
            PreparedStatement pst = DBConnect.get(sql);  // Sử dụng phương thức get() từ DBConnect
            pst.setString(1, token);  // Gán giá trị token vào câu lệnh SQL

            // Thực thi câu lệnh UPDATE
            int rows = pst.executeUpdate();
            isUpdated = rows > 0;  // Nếu có ít nhất một dòng bị cập nhật, trả về true
        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý lỗi nếu có
        }
        return isUpdated;  // Trả về true nếu thành công, false nếu không thành công
    }
    // Lưu token vào cơ sở dữ liệu
    public boolean saveVerificationToken(String email, String token) {
        boolean isUpdated = false;
        try {
            // Giải mã token từ URL
            String decodedToken = decodeToken(token);

            // Câu lệnh SQL để cập nhật token
            String sql = "UPDATE users SET verification_token = ? WHERE email = ?";
            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, decodedToken);  // Gán token đã giải mã vào tham số đầu tiên
            pst.setString(2, email);  // Gán email vào tham số thứ hai

            // Thực thi câu lệnh UPDATE
            int rows = pst.executeUpdate();
            isUpdated = rows > 0;  // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý lỗi nếu có
        }
        return isUpdated;  // Trả về true nếu thành công, false nếu có lỗi
    }

    // Kiểm tra tính hợp lệ của token
    public boolean isTokenValid(String token) {
        boolean isValid = false;
        try {
            // Giải mã token từ URL
            String decodedToken = decodeToken(token);

            // In ra token đã giải mã để kiểm tra
            System.out.println("Decoded Token: " + decodedToken);

            // Câu lệnh SQL để kiểm tra tính hợp lệ của token
            String sql = "SELECT COUNT(*) FROM users WHERE verification_token = ? AND status = 0";

            // In ra câu lệnh SQL và token để kiểm tra
            System.out.println("SQL Query: " + sql);
            System.out.println("Checking with token: " + decodedToken);

            PreparedStatement pst = DBConnect.get(sql);
            pst.setString(1, decodedToken);  // Gán token đã giải mã vào tham số đầu tiên trong câu lệnh SQL

            // Thực thi câu lệnh SELECT và lấy kết quả
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isValid = rs.getInt(1) > 0;  // Nếu có ít nhất một bản ghi thỏa mãn điều kiện, trả về true
            }

            // In ra kết quả kiểm tra
            System.out.println("Token validity result: " + isValid);

        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý lỗi nếu có
        }
        return isValid;  // Trả về true nếu token hợp lệ, false nếu không hợp lệ
    }

}




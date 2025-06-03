package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.LogDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "UpdateInformationServlet", value = "/updateInformation")
public class UpdateInformationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa ký tự request thành UTF-8 để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");

        // Lấy session hiện tại và đối tượng người dùng đã đăng nhập
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        // Nếu chưa đăng nhập, chuyển hướng về trang login
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy các tham số nhập từ form cập nhật thông tin
        String fullname = request.getParameter("fullname");
        String identification = request.getParameter("identification");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dateOfBirthStr = request.getParameter("dateOfBirth");

        // Lấy các tham số địa chỉ từ form
        String address = request.getParameter("address");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");

        // Chuyển đổi chuỗi ngày tháng thành đối tượng java.sql.Date
        Date dateOfBirth = null;
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            dateOfBirth = Date.valueOf(dateOfBirthStr);
        }

        // Cập nhật các thông tin mới vào đối tượng user
        user.setFullname(fullname);
        user.setIdentification(identification);
        user.setPhone(phone);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address);
        user.setProvince(province);
        user.setDistrict(district);
        user.setWard(ward);

        // Tạo đối tượng UserDao để thao tác database
        UserDao userDao = new UserDao();
        // Cập nhật thông tin người dùng trong database, trả về kết quả (số bản ghi bị ảnh hưởng)
        int result = userDao.updateUserDetails(user);

        // Khởi tạo LogDao để ghi log hoạt động
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr(); // Lấy IP của người dùng

        if (result > 0) {
            // Nếu cập nhật thành công, ghi log mức INFO
            try {
                logDao.insertLog("INFO", "User updated profile information successfully", user.getEmail(), userIp);
            } catch (Exception e) {
                e.printStackTrace(); // In lỗi log nếu có nhưng không ảnh hưởng flow chính
            }

            // Đặt thông báo thành công vào session để hiển thị trên trang thông tin
            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            // Cập nhật lại đối tượng user trong session với thông tin mới
            session.setAttribute("user", user);
            // Chuyển hướng về trang thông tin cá nhân
            response.sendRedirect("information.jsp");
        } else {
            // Nếu cập nhật thất bại, ghi log mức ERROR
            try {
                logDao.insertLog("ERROR", "Failed to update user profile information", user.getEmail(), userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Đặt lỗi hiển thị trên trang thông tin cá nhân
            request.setAttribute("error", "Cập nhật thông tin thất bại!");
            // Chuyển tiếp yêu cầu về trang thông tin cá nhân để người dùng xem lỗi
            request.getRequestDispatcher("information.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu truy cập bằng GET, chuyển hướng đến trang thông tin cá nhân
        response.sendRedirect("information.jsp");
    }
}

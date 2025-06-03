package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.Users;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TKAdmin", value = "/admin/dashboard")
public class TKAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VaccineDao vaccineDao = new VaccineDao();
        UserDao userDao = new UserDao();
        OrderDetailDao odd = new OrderDetailDao();
        OrderDao od = new OrderDao();

        // Lấy tổng số người dùng
        int totalUser = userDao.totalUser();
        // Lấy số người dùng đăng ký tuần trước
        int userCountChange = userDao.getUsersCountLastWeek();
        // Lấy danh sách người dùng đăng ký trong tháng này
        List<Users> userRegisterThisMonth = userDao.getUsersRegisterThisMonth();

        // Lấy tổng đơn hàng
        int totalOrder = odd.totalOrder();
        // Lấy số đơn hàng tuần trước
        int orderCountChange = odd.getOrdersCountLastWeek();

        // Lấy tổng doanh thu
        float totalRevenue = odd.totalRevenue();
        // Lấy thay đổi doanh thu tuần trước
        float revenueCountChange = odd.getTotalRevenueLaskWeeks();

        // Đếm vắc xin còn hàng và hết hàng
        int countInStock = vaccineDao.countInStock();
        int countOutOfStock = vaccineDao.countOutOfStock();


        List<Vaccines> countOrder = od.quantityVaccine();

        // Đặt các giá trị vào request để truyền cho JSP
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("userCountChange", userCountChange);
        request.setAttribute("userRegister", userRegisterThisMonth);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("orderCountChange", orderCountChange);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("revenueCountChange", revenueCountChange);
        request.setAttribute("countInStock", countInStock);
        request.setAttribute("countOutOfStock", countOutOfStock);
        request.setAttribute("countOrder", countOrder);

        // Forward tới trang dashboard.jsp để hiển thị
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        UserDao userDao = new UserDao();
        boolean isUpdated = userDao.updateStatus(id, status);

        if (isUpdated) {
            try {
                logDao.insertLog("INFO", "User status updated successfully. User ID: " + id + ", New status: " + status, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"message\": \"success\"}");
        } else {
            try {
                logDao.insertLog("ERROR", "Failed to update user status. User ID: " + id + ", Attempted status: " + status, "admin", userIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().write("{\"message\": \"error\"}");
        }
    }
}

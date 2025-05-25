package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.OrderDetailDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.OrderDetails;
import com.example.provide_vaccine_services.dao.model.Orders;
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

        // Người dùng
        int totalUser = userDao.totalUser();
        int userCountChange = userDao.getUsersCountLastWeek();
        List<Users> userRegisterThisMonth = userDao.getUsersRegisterThisMonth();


        // Đơn hàng
        int totalOrder = odd.totalOrder();
        int orderCountChange = odd.getOrdersCountLastWeek();

        // Doanh thu
        float totalRevenue = odd.totalRevenue();
        float revenueCountChange = odd.getTotalRevenueLaskWeeks();

        // Vắc xin còn hàng hoặc hết hàng
        int countInStock = vaccineDao.countInStock();
        int countOutOfStock = vaccineDao.countOutOfStock();

        request.setAttribute("totalUser", totalUser);
        request.setAttribute("userCountChange", userCountChange);
        request.setAttribute("userRegister", userRegisterThisMonth);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("orderCountChange", orderCountChange);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("revenueCountChange", revenueCountChange);
        request.setAttribute("countInStock", countInStock);
        request.setAttribute("countOutOfStock", countOutOfStock);

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        UserDao userDao = new UserDao();
        boolean isUpdated = userDao.updateStatus(id, status);

        if (isUpdated) {
            response.getWriter().write("{\"message\": \"success\"}");
        } else {
            response.getWriter().write("{\"message\": \"error\"}");
        }
    }
}
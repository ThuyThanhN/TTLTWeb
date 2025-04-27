package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.OrderDetailDao;
import com.example.provide_vaccine_services.dao.UserDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.OrderDetails;
import com.example.provide_vaccine_services.dao.model.Orders;
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

//        int totalVacine = vaccineDao.totalVaccines();
//        int totalExpire = vaccineDao.statusVaccines();
        int totalUser = userDao.totalUser();
        int userCountChange = userDao.getUsersCountLastWeek();
        int totalOrder = odd.totalOrder();
        List<Vaccines> countOrder = od.quantityVaccine();

//        request.setAttribute("totalVacine", totalVacine);
//        request.setAttribute("totalExpire", totalExpire);
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("userCountChange", userCountChange);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("countOrder", countOrder);

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
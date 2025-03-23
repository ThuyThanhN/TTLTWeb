package com.example.provide_vaccine_services.filter;

import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AdminFilter",
        urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest re, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) re;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(true);
        Users u = (Users) session.getAttribute("user");

        if (u == null || u.getRole() == 0) {
            response.sendRedirect("../error404.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}
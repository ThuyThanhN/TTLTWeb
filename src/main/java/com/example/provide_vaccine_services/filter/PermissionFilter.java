package com.example.provide_vaccine_services.filter;

import com.example.provide_vaccine_services.dao.PermissionDao;
import com.example.provide_vaccine_services.dao.model.Permissions;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.FilterChain;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "PermissionFilter", urlPatterns = {"/admin/*"})
public class PermissionFilter implements Filter {
    private PermissionDao permissionDao = new PermissionDao();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

//      Nếu chưa đăng nhập thì chuyển trang đến login, nếu đăng nhập rồi thì lấy id của user đó ra
        Users users = (session != null) ? (Users) session.getAttribute("user") : null;
        if (users == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        int userId = users.getId();

//      Lấy toàn bộ URI (bao gồm context path và servlet path)
        String uri = req.getRequestURI().toLowerCase();

//      Lấy context path
        String contextPath = req.getContextPath().toLowerCase();

//      Cắt bỏ context path khỏi URI để lấy phần đường dẫn thực sự mà người dùng đang truy cập
//      Ví dụ: nếu URI là "/provide_vaccine_services/admin/addstaff"
//      và contextPath là "/provide_vaccine_services"
//      thì path sẽ là "/admin/addstaff"
        String path = uri.substring(contextPath.length()).toLowerCase();

        System.out.println("Requested Uri: " + uri);
        System.out.println("Requested Path: " + path);

//      Phân quyền cho staff gồm thêm, sửa, xóa (3) cho userId quản lý nhân viên
        if (path.endsWith("/admin/addstaff") || path.endsWith("/admin/updatestaff") || path.endsWith("/admin/removestaff")) {
            int requiredPermission = Permissions.WRITE | Permissions.EXECUTE;
            // Kiểm tra xem user hiện tại có đủ quyền đối với module "staff" không
            if (!permissionDao.hasPermission(userId, "staff", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }

//      Phân quyền cho supplier gồm thêm, sửa, xóa (3) cho userId quản lý nhà cung cấp
        if (path.endsWith("/admin/addsupplier") || path.endsWith("/admin/updatesupplier") || path.endsWith("/admin/removesupplier")) {
            int requiredPermission = Permissions.WRITE | Permissions.EXECUTE | Permissions.READ;
            // Kiểm tra xem user hiện tại có đủ quyền đối với module "supplier" không
            if (!permissionDao.hasPermission(userId, "supplier", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }

//      Phân quyền cho center gồm thêm, sửa, xóa (3) cho userId quản lý trung tâm tiêm chủng
        if (path.endsWith("/admin/addcenter") || path.endsWith("/admin/updatecenter") || path.endsWith("/admin/removecenter")) {
            int requiredPermission = Permissions.WRITE | Permissions.EXECUTE;
            // Kiểm tra xem user hiện tại có đủ quyền đối với module "center" không
            if (!permissionDao.hasPermission(userId, "center", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }

        // Phân quyền cho vaccine gồm thêm, sửa, xóa (3) cho userId quản lý vaccine
        if (path.endsWith("/admin/addvaccine") || path.endsWith("/admin/updatevaccine") || path.endsWith("/admin/removevaccine")) {
            int requiredPermission = Permissions.WRITE | Permissions.EXECUTE;

            // Kiểm tra xem user hiện tại có đủ quyền đối với module "vaccine" không
            if (!permissionDao.hasPermission(userId, "vaccine", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }

        // Phân quyền cho duyet don hang cho userId quản lý order
        if (path.endsWith("/admin/updateorderstatus")) {
            int requiredPermission = Permissions.EXECUTE;

            // Kiểm tra xem user hiện tại có đủ quyền đối với module "order" không
            if (!permissionDao.hasPermission(userId, "order", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }

        // Phân quyền cho goi vaccine gồm thêm, sửa, xóa (3) cho userId quản lý goi vaccine
        if (path.endsWith("/admin/addpmapping") || path.endsWith("/admin/updatepackage") || path.endsWith("/admin/removepackage")) {
            int requiredPermission = Permissions.WRITE | Permissions.EXECUTE;

            // Kiểm tra xem user hiện tại có đủ quyền đối với module "package" không
            if (!permissionDao.hasPermission(userId, "package", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }

        // Phân quyền cho goi vaccine gồm thêm, sửa, xóa (3) cho userId quản lý giao dich (nhap/xuat)
        if (path.endsWith("/admin/form-add-transaction")) {
            int requiredPermission = Permissions.WRITE | Permissions.EXECUTE;

            // Kiểm tra xem user hiện tại có đủ quyền đối với module "package" không
            if (!permissionDao.hasPermission(userId, "transaction", requiredPermission)) {
                // Nếu không có quyền, từ chối truy cập
                deny(resp, req);
                return;
            }
        }


        chain.doFilter(request, response);
    }

    // Xử lý khi người dùng không có quyền truy cập
    private void deny(HttpServletResponse resp, HttpServletRequest req) throws IOException, ServletException {
        String requestedWith = req.getHeader("X-Requested-With");
        System.out.println(requestedWith);

        if ("XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
            // Trả về JSON lỗi nếu là request AJAX
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"Bạn không có quyền truy cập chức năng này!\"}");
        } else {
            // Nếu không phải AJAX, chuyển hướng tới trang 403.jsp
            req.getRequestDispatcher("../error403.jsp").forward(req, resp);
        }
    }
}


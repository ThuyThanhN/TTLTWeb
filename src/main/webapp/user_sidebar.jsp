<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    // Lấy thông tin người dùng từ session
    Users user = (Users) session.getAttribute("user");
    if (user == null) {
        // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
        response.sendRedirect("login.jsp");
        return;
    }

    // Lấy thông báo thành công từ session
    String successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
        session.removeAttribute("successMessage");
%>
<div class="alert alert-success" role="alert">
    <%= successMessage %>
</div>
<% } %>
<div class="sidebar">
    <div style="height: 32%">
        <div class="profile-card">
            <img src="image/avatar1.png" alt="Avatar" class="profile-image">
            <h2 id="sidebar-username" class="sidebar-username"><%= user.getFullname() %></h2>
            <p style="font-size: 14px"><%= user.getPhone() %></p>
        </div>
        <div>&nbsp;</div>
    </div>
    <ul class="menu">
        <li>
            <a href="information.jsp" class="d-flex align-items-center <%= request.getRequestURI().contains("information.jsp") ? "active" : "" %>">
                <img src="image/user.png" width="20" height="20" class="me-2">
                Thông tin cá nhân
                <i class="fa-solid fa-chevron-right ms-auto"></i>
            </a>
        </li>
        <li>
            <a href="my-appointments" class="d-flex align-items-center <%= request.getRequestURI().contains("my-appointments") ? "active" : "" %>">
                <img src="image/syringe.png" width="20" height="20" class="me-2">
                Lịch hẹn tiêm vắc xin
                <i class="fa-solid fa-chevron-right ms-auto"></i>
            </a>
        </li>
        <li>
            <a href="changePassword" class="d-flex align-items-center">
                <img src="image/key.png" width="20" height="20" class="me-2">
                Đổi mật khẩu
                <i class="fa-solid fa-chevron-right ms-auto"></i>
            </a>
        </li>
        <li>
            <a href="#" data-bs-toggle="modal" data-bs-target="#lockAccountModal" class="d-flex align-items-center">
                <img src="image/padlock.png" width="20" height="20" class="me-2">
                Khóa tài khoản
                <i class="fa-solid fa-chevron-right ms-auto"></i>
            </a>
        </li>
        <li>
            <a href="logout" class="d-flex align-items-center">
                <img src="image/enter.png" width="20" height="20" class="me-2">
                Đăng xuất
                <i class="fa-solid fa-chevron-right ms-auto"></i>
            </a>
        </li>
    </ul>

    <!-- Modal lock account -->
    <div class="modal fade" id="lockAccountModal" tabindex="-1" aria-labelledby="lockAccountModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="lockAccount" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="lockAccountModalLabel">Thông Báo Khóa Tài Khoản
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body">
                        <div class="text-center">Tài khoản của bạn sẽ bị khóa. <br> Bạn có chắc chắn muốn tiếp
                            tục?
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-danger">Xác Nhận</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

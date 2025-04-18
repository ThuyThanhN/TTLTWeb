<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Khóa tài khoản</title>
    <!-- Thêm Bootstrap và Font Awesome -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <style>
        .modal-header {
            background: linear-gradient(90deg, #2a388f, #68aaf6, rgba(170, 229, 236, 0.73));
            color: white;
        }
    </style>
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>

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
    <!-- Content -->
    <div class="main">
        <div class="container">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="profile-card">
                    <img src="image/avatar1.png" alt="Avatar" class="profile-image">
                    <%
                        // Kiểm tra trạng thái tài khoản của người dùng
                        int status = user.getStatus();// Lấy giá trị status từ đối tượng user
                    %>

                    <!-- Hiển thị thông báo về trạng thái xác thực tài khoản -->
                    <%--                    <div class="status-message">--%>
                    <%--                        <%= (status == 0) ? "Tài khoản chưa xác thực" : "Tài khoản đã xác minh" %>--%>
                    <%--                    </div>--%>

                    <h2 class="sidebar-username"><%= user.getFullname() %>
                    </h2>
                    <p><%= user.getPhone() %>
                    </p>
                </div>
                <ul class="menu">
                    <li><a href="information.jsp" class="active"><i class="fa-regular fa-user"></i> Thông tin cá
                        nhân</a></li>
                    <li><a href="my-appointments"><i class="fa-solid fa-calendar-check"></i> Lịch hẹn tiêm vắc xin</a>
                    </li>
                    <li><a href="changePassword"><i class="fa-solid fa-key"></i> Đổi mật khẩu</a></li>
                    <li>
                        <a href="#" data-bs-toggle="modal" data-bs-target="#lockAccountModal">
                            <i class="fas fa-user-lock"></i> Khóa tài khoản
                        </a>
                    </li>
                    <li><a href="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </div>

            <!-- Modal Bootstrap 5 -->
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
    </div>
</div>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
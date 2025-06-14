<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin cá nhân</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
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
    <link rel="stylesheet" href="css/information.css">
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
            <jsp:include page="user_sidebar.jsp"></jsp:include>

            <!-- Main Content -->
            <div class="main-content">
                <div class="p-3 border-bottom title">Thông tin cá nhân</div>
                <div class="p-3">
                    <div class="welcome-message" id="user-name">Xin chào, <%= user.getFullname() %></div>
                    <div class="info-card">
                        <div class="info-header">
                            <img src="image/avatar1.png" alt="Avatar" class="info-profile-image">
                        </div>

                        <div id="personal-info" class="info-details">
                            <div class="item-info">
                                <span class="label">Họ và tên:</span>
                                <span class="value" id="info-username"> <%= user.getFullname() %></span>
                            </div>
                            <div class="item-info">
                                <span class="label">Mã định danh:</span>
                                <span class="value"><%= user.getIdentification() %></span>
                            </div>
                            <div class="item-info">
                                <span class="label">Email:</span>
                                <span class="value"><%= user.getEmail() %></span>
                            </div>
                            <div class="item-info">
                                <span class="label">Số điện thoại:</span>
                                <span class="value"><%= user.getPhone() %></span>
                            </div>
                            <div class="item-info">
                                <span class="label">Giới tính:</span>
                                <span class="value"><%= user.getGender() %></span>
                            </div>
                            <div class="item-info">
                                <span class="label">Ngày sinh:</span>
                                <span class="value"><f:formatDate value="${user.dateOfBirth}"
                                                                  pattern="dd-MM-yyyy"/></span>
                            </div>
                            <div class="item-info">
                                <span class="label">Địa chỉ:</span>
                                <span class="value"><%= user.getAddress() %>,
                                                    <%= user.getWard() %>, <br>
                                                    <%= user.getDistrict() %> ,
                                                    <%= user.getProvince() %></span>
                            </div>
                            <button id="edit-btn" class="btn-edit">Chỉnh sửa thông tin</button>
                        </div>
                    </div>

                    <!-- Form chỉnh sửa thông tin -->
                    <div class="scrollable" id="edit-form" style="display: none;">
                        <form action="updateInformation" method="post" class="p-4 rounded shadow-lg bg-light">
                            <div class="mb-3">
                                <label for="fullname" class="form-label">Họ và tên:</label>
                                <input type="text" id="fullname" name="fullname" value="<%= user.getFullname() %>"
                                       class="form-control" placeholder="Nhập họ và tên">
                            </div>

                            <div class="mb-3 row">
                                <!-- Mã định danh -->
                                <div class="col-md-6">
                                    <label for="identification" class="form-label">Mã định danh:</label>
                                    <input type="text" id="identification" name="identification"
                                           value="<%= user.getIdentification() %>" class="form-control">
                                </div>

                                <!-- Số điện thoại -->
                                <div class="col-md-6">
                                    <label for="phone" class="form-label">Số điện thoại:</label>
                                    <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <!-- Giới tính -->
                                <div class="col-md-6">
                                    <label class="form-label">Giới tính:</label>
                                    <div class="border rounded" style="height: 40.25px; padding: 7px 0 0 10px">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" id="male" name="gender"
                                                   value="Nam" <%= "Nam".equals(user.getGender()) ? "checked" : "" %>>
                                            <label class="form-check-label" for="male">Nam</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" id="female" name="gender"
                                                   value="Nữ" <%= "Nữ".equals(user.getGender()) ? "checked" : "" %>>
                                            <label class="form-check-label" for="female">Nữ</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- Ngày sinh -->
                                <div class="col-md-6">
                                    <label for="dateOfBirth" class="form-label">Ngày sinh:</label>
                                    <input type="date" id="dateOfBirth" name="dateOfBirth"
                                           value="<%= user.getDateOfBirth() %>" class="form-control">
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email:</label>
                                <input type="text" id="email" name="email" value="<%= user.getEmail() %>"
                                       class="form-control" placeholder="Nhập email cần thay đổi">
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Số nhà/Đường:</label>
                                <input type="text" id="address" name="address" value="<%= user.getAddress() %>"
                                       class="form-control" placeholder="Nhập địa chỉ chi tiết">
                            </div>

                            <div class="mb-3">
                                <label for="province-select" class="form-label">Tỉnh/Thành:</label>
                                <div class="dropdown">
                                    <input type="text" id="province-select" name="province"
                                           class="form-control dropdown-toggle province-select"
                                           data-bs-toggle="dropdown" aria-expanded="false"
                                           value="<%= user.getProvince() %>" placeholder="Chọn tỉnh/thành">
                                    <i class="fa-solid fa-angle-down"></i>
                                    <ul class="dropdown-menu province-menu"></ul>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="district-select" class="form-label">Quận/Huyện:</label>
                                <div class="dropdown">
                                    <input type="text" id="district-select" name="district"
                                           class="form-control dropdown-toggle district-select"
                                           data-bs-toggle="dropdown" aria-expanded="false"
                                           placeholder="Chọn tỉnh/thành trước" value="<%= user.getDistrict() %>">
                                    <i class="fa-solid fa-angle-down"></i>
                                    <ul class="dropdown-menu district-menu"></ul>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="ward-select" class="form-label">Phường/Xã:</label>
                                <div class="dropdown">
                                    <input type="text" id="ward-select" name="ward"
                                           class="form-control dropdown-toggle ward-select"
                                           data-bs-toggle="dropdown" aria-expanded="false"
                                           placeholder="Chọn quận/huyện trước" value="<%= user.getWard() %>">
                                    <i class="fa-solid fa-angle-down"></i>
                                    <ul class="dropdown-menu ward-menu"></ul>
                                </div>
                            </div>

                            <div class="text-center">
                                <button type="submit" class="btn btn-primary px-4 py-2 rounded-pill shadow-sm">Lưu thông tin</button>
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
<script src="js/api_address.js"></script>
<script src="js/information.js"></script>
</body>
</html>
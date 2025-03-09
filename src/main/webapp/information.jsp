<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin cá nhân</title>
    <!-- Thêm Bootstrap và Font Awesome -->
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
            <div class="sidebar">
                <div class="profile-card">
                    <img src="image/avatar1.png" alt="Avatar" class="profile-image">
                    <h2 class="sidebar-username"><%= user.getFullname() %></h2>
                    <p><%= user.getPhone() %></p>
                </div>
                <ul class="menu">
                    <li><a href="information.jsp" class="active"><i class="fa-regular fa-user"></i> Thông tin cá nhân</a></li>
                    <li><a href="my-appointments"><i class="fa-solid fa-calendar-check"></i> Lịch hẹn tiêm vắc xin</a></li>
                    <li><a href="changePassword"><i class="fa-solid fa-key"></i> Đổi mật khẩu</a></li>
                    <li><a href="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </div>

            <!-- Main Content -->
            <div class="main-content">
                <div class="welcome-message" id="user-name">Xin chào, <%= user.getFullname() %></div>
                <h1></h1>
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
                            <span class="label">Số điện thoại:</span>
                            <span class="value"><%= user.getPhone() %></span>
                        </div>
                        <div class="item-info">
                            <span class="label">Giới tính:</span>
                            <span class="value"><%= user.getGender() %></span>
                        </div>
                        <div class="item-info">
                            <span class="label">Ngày sinh:</span>
                            <span class="value"><%= user.getDateOfBirth() %></span>
                        </div>
                        <div class="item-info">
                            <span class="label">Địa chỉ:</span>
                            <span class="value"><%= user.getAddress() %>,<%= user.getProvince() %>,<%= user.getDistrict() %>,<%= user.getWard() %></span>
                        </div>
                        <button id="edit-btn" class="btn-edit">Chỉnh sửa thông tin</button>
                    </div>
                    <%--                    <button id="edit-btn" class="btn-edit">Chỉnh sửa thông tin</button>--%>
                    <!-- Form chỉnh sửa thông tin -->
                    <div id="edit-form" style="display: none;">
                        <form action="updateInformation" method="post" class="p-4 rounded shadow-lg bg-light">
                            <h3 class="text-center mb-4">Chỉnh sửa thông tin cá nhân</h3>

                            <div class="mb-3">
                                <label for="fullname" class="form-label"><strong>Họ và tên:</strong></label>
                                <input type="text" id="fullname" name="fullname" value="<%= user.getFullname() %>" class="form-control" placeholder="Nhập họ và tên">
                            </div>

                            <div class="mb-3 row">
                                <!-- Mã định danh -->
                                <div class="col-md-6">
                                    <label for="identification" class="form-label"><strong>Mã định danh:</strong></label>
                                    <input type="text" id="identification" name="identification" value="<%= user.getIdentification() %>" class="form-control">
                                </div>

                                <!-- Số điện thoại -->
                                <div class="col-md-6">
                                    <label for="phone" class="form-label"><strong>Số điện thoại:</strong></label>
                                    <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>" class="form-control">
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <!-- Giới tính -->
                                <div class="col-md-6">
                                    <label class="form-label"><strong>Giới tính:</strong></label>
                                    <div class="border rounded" style="height: 40.25px; padding: 7px 0 0 10px">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" id="male" name="gender" value="Nam" <%= "Nam".equals(user.getGender()) ? "checked" : "" %>>
                                            <label class="form-check-label" for="male">Nam</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" id="female" name="gender" value="Nữ" <%= "Nữ".equals(user.getGender()) ? "checked" : "" %>>
                                            <label class="form-check-label" for="female">Nữ</label>
                                        </div>
                                    </div>
                                </div>

                                <!-- Ngày sinh -->
                                <div class="col-md-6">
                                    <label for="dateOfBirth" class="form-label"><strong>Ngày sinh:</strong></label>
                                    <input type="date" id="dateOfBirth" name="dateOfBirth" value="<%= user.getDateOfBirth() %>" class="form-control">
                                </div>
                            </div>


                            <div class="mb-3">
                                <label for="address" class="form-label"><strong>Số nhà/Đường:</strong></label>
                                <input type="text" id="address" name="address" value="<%= user.getAddress() %>" class="form-control" placeholder="Nhập địa chỉ chi tiết">
                            </div>

                            <div class="mb-3">
                                <label for="province-select" class="form-label"><strong>Tỉnh/Thành:</strong></label>
                                <div class="dropdown">
                                    <input type="text" id="province-select" name="province" class="form-control dropdown-toggle"
                                           data-bs-toggle="dropdown" aria-expanded="false" value="<%= user.getProvince() %>" placeholder="Chọn tỉnh/thành">
                                    <ul class="dropdown-menu province-menu"></ul>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="district-select" class="form-label"><strong>Quận/Huyện:</strong></label>
                                <div class="dropdown">
                                    <input type="text" id="district-select" name="district" class="form-control dropdown-toggle"
                                           data-bs-toggle="dropdown" aria-expanded="false" placeholder="Chọn tỉnh/thành trước" value="<%= user.getDistrict() %>">
                                    <ul class="dropdown-menu district-menu"></ul>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="ward-select" class="form-label"><strong>Phường/Xã:</strong></label>
                                <div class="dropdown">
                                    <input type="text" id="ward-select" name="ward" class="form-control dropdown-toggle"
                                           data-bs-toggle="dropdown" aria-expanded="false" placeholder="Chọn quận/huyện trước" value="<%= user.getWard() %>">
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
</div>

<!-- JavaScript để điều khiển việc chỉnh sửa -->
<script src="js/show_navbarNav.js"></script>
<script>
    document.getElementById('edit-btn').addEventListener('click', function() {
        document.getElementById('personal-info').style.display = 'none';
        document.getElementById('edit-form').style.display = 'block';
    });
</script>
<script>
    document.getElementById('edit-btn').addEventListener('click', function() {
        document.getElementById('personal-info').style.display = 'none';
        document.getElementById('edit-form').style.display = 'block';
    });
</script>
<script src="js/api_address.js"></script>
</body>
</html>
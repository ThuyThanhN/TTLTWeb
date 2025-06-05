<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
    <!-- Css -->
    <link rel="stylesheet" href="css/header.css">
</head>
<body>
<%
    // Kiểm tra thông tin người dùng từ session
    Users user = (Users) session.getAttribute("user");
%>
<div class="header">
    <div class="row g-0">
        <div class="col-8 col-sm-7 col-lg-12 order-2 order-lg-1">
            <div class="header-top">
                <div class="container">
                    <div class="row g-0">
                        <div class="col-6 col-lg-3">
                            <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
                        </div>
                        <div class="col-6 col-lg-9">
                            <div class="btn-group d-none d-lg-block">
                                <a href="shoppingCart"><img src="image/shopping_cart.png"></a>
                            </div>
                            <div class="calendar-days">
                                <% if (user == null) { %>
                                <!-- Hien thi toast khi chua dang nhap -->
                                <a href="#" onclick="showToast()"><i class="fa-regular fa-calendar-days"></i> Đặt lịch
                                    tiêm</a>
                                <% } else { %>
                                <a href="dosing_schedule"><i class="fa-regular fa-calendar-days"></i> Đặt lịch tiêm</a>
                                <% } %>
                            </div>

                            <!-- Toast thong bao khi chua dang nhap -->
                            <div class="toast-container position-fixed" style="z-index: 1050;">
                                <div id="loginToast" class="toast" role="alert" aria-live="assertive"
                                     aria-atomic="true">
                                    <div class="toast-body">
                                        <div class="d-flex justify-content-between">
                                            <div>Bạn cần đăng nhập để đặt lịch tiêm.</div>
                                            <div>
                                                <button type="button" class="btn-close" data-bs-dismiss="toast"
                                                        aria-label="Close"></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="position-relative btn-login">
                                <% if (user != null) { %>
                                <!-- Nếu đã đăng nhập, hiển thị tên người dùng -->
                                <a href="#" class="d-flex">
                                    <span><i class="fa-solid fa-user"></i></span>
                                    <span class="d-none d-lg-block">Xin chào, <%= user.getFullname() %>!</span>
                                </a>
                                <ul class="menu-items">
                                    <li><a href="updateInformation">Thông tin cá nhân</a>
                                    </li>
                                    <li><a href="my-appointments">Lịch hẹn tiêm vắc xin</a></li>
                                    <li><a href="changePassword">Đổi mật khẩu</a></li>
                                    <li><a href="logout">Đăng xuất</a></li>
                                </ul>
                                <% } else { %>
                                <!-- Nếu chưa đăng nhập, hiển thị nút Đăng nhập -->
                                <a href="login" class="d-flex">
                                    <span><i class="fa-solid fa-user"></i></span>
                                    <span class="d-none d-lg-block">Đăng nhập</span>
                                </a>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4 col-sm-5 col-lg-12 order-1 order-lg-2">
            <div class="header-middle">
                <div class="container d-flex justify-content-between align-items-center">
                    <div>
                        <nav class="navbar navbar-expand-lg navbar-light">
                            <div class="">
                                <%-- Nút mở navbar --%>
                                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#navbarNav" aria-expanded="false"
                                        aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarNav">
                                    <!-- Nút đóng navbar-->
                                    <span class="close-btn" onclick="closeNav()"><i class="fa-solid fa-x"></i></span>
                                    <ul class="navbar-nav">
                                        <li class="nav-item d-lg-none " >
                                            <a class="nav-link" href="index"> <img src="image/logo.png" alt="Logo"></a>
                                        </li>
                                        <li class="nav-item search-info d-lg-none">
                                            <a class="nav-link" href="#">
                                                <input type="text" name="input-search-info" id="" placeholder="Tìm kiếm"
                                                       class="form-control">
                                                <i class="fa-solid fa-magnifying-glass"></i>
                                            </a>
                                        </li>




                                        <li class="nav-item <%= (request.getRequestURI().contains("index")) ? "active" : "" %>">
                                            <a class="nav-link " href="index">Trang chủ</a>
                                        </li>
                                        <li class="nav-item <%= (request.getRequestURI().contains("introduction")) ? "active" : "" %>">
                                            <a class="nav-link" href="introduction.jsp">Giới thiệu</a>
                                        </li>
                                        <li class="nav-item position-relative d-none d-lg-block">
                                            <a class="nav-link <%= (request.getRequestURI().contains("pre-vaccination") || request.getRequestURI().contains("after-vaccination")) ? "active" : "" %>" href="#">Cẩm nang</a>
                                            <ul class="menu-items">
                                                <li><a href="pre-vaccination.jsp">Những điều cần biết trước khi tiêm
                                                    chủng</a></li>
                                                <li><a href="after-vaccinations.jsp">Những điều cần biết sau khi tiêm
                                                    chủng</a></li>
                                            </ul>
                                        </li>
                                        <li class="nav-item position-relative d-lg-none">
                                            <div class="accordion accordion-flush" id="accordionFlushExample">
                                                <div class="accordion-item">
                                                    <h2 class="accordion-header" id="flush-headingOne">
                                                        <a href="#" class="accordion-button collapsed nav-link"
                                                           type="button" data-bs-toggle="collapse"
                                                           data-bs-target="#flush-collapseOne" aria-expanded="false"
                                                           aria-controls="flush-collapseOne">
                                                            Cẩm nang
                                                        </a>
                                                    </h2>
                                                    <div id="flush-collapseOne" class="accordion-collapse collapse"
                                                         aria-labelledby="flush-headingOne"
                                                         data-bs-parent="#accordionFlushExample">
                                                        <div class="accordion-body">
                                                            <div><a href="pre-vaccination.jsp">Những điều cần biết trước
                                                                khi tiêm chủng</a></div>

                                                        </div>
                                                        <div class="accordion-body">
                                                            <div><a href="after-vaccinations.jsp">Những điều cần biết
                                                                sau khi tiêm chủng</a></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="nav-item <%= (request.getRequestURI().contains("price")) ? "active" : "" %>">
                                            <a class="nav-link" href="price">Bảng giá</a>
                                        </li>
                                        <li class="nav-item <%= (request.getRequestURI().contains("information_vacxin")) ? "active" : "" %>">
                                            <a class="nav-link" href="information_vacxin.jsp">Bệnh học về vắc xin</a>
                                        </li>
                                        <li class="nav-item d-lg-none">
                                            <a class="nav-link" href="dosing_schedule.jsp"><i
                                                    class="fa-regular fa-calendar-days"></i> Đặt lịch tiêm</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </nav>
                    </div>
                    <div class="single-contact">
                        <h6><i class="fa-solid fa-phone-volume shake-icon"></i> Tư vấn ngay: 023 2343 8445</h6>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="js/header.js"></script>

</html>

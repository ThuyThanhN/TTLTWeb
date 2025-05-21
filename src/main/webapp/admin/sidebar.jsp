<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="sidebar" id="sidebar">
    <div class="text-center user-res">
        <img class="mt-2 img-admin" src="../image/hacker.png" alt=""><span>Thanh Thủy</span>
        <div class="search-box">
            <i class="fa-solid fa-search"></i>
            <input type="text" name="" placeholder="Search">
        </div>
    </div>
    <ul class="menu">
        <li class="<%= (request.getRequestURI().contains("dashboard")) ? "active" : "" %>">
            <a href="dashboard">
                <i class="fas fa-chart-bar"></i>
                <span>Thống kê</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-staff")) ? "active" : "" %>">
            <a href="table-data-staff">
                <i class="fa-solid fa-address-card"></i>
                <span>Nhân viên</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-user")) ? "active" : "" %>">
            <a href="table-data-user">
                <i class="fas fa-user"></i>
                <span>Khách hàng</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-order")) ? "active" : "" %>">
            <a href="table-data-order">
                <i class="fa-solid fa-calendar-check"></i>
                <span>Đơn hàng</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-vacxin")) ? "active" : "" %>">
            <a href="table-data-vacxin">
                <i class="fa-solid fa-syringe"></i>
                <span>Vắc xin</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-vax-package")) ? "active" : "" %>">
            <a href="table-data-vax-package">
                <i class="fa-solid fa-syringe"></i>
                <span>Gói vắc xin</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-supplier")) ? "active" : "" %>">
            <a href="table-data-supplier">
                <i class="fa-solid fa-truck-field"></i>
                <span>Nhà cung cấp</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-center")) ? "active" : "" %>">
            <a href="table-data-center">
                <i class="fa-regular fa-hospital"></i>
                <span>Trung tâm</span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-transaction")) ? "active" : "" %>">
            <a href="table-data-transaction">
                <i class="fa-solid fa-truck"></i>
                <span> Nhập / xuất hàng </span>
            </a>
        </li>
        <li class="<%= (request.getRequestURI().contains("table-data-warehouse")) ? "active" : "" %>">
            <a href="table-data-warehouse">
                <i class="fa-solid fa-warehouse"></i>
                <span> Kho hàng </span>
            </a>
        </li>
        <li class="logout">
            <a href="logout">
                <i class="fas fa-sign-out-alt"></i>
                <span>Đăng xuất</span>
            </a>
        </li>
    </ul>
</div>
</body>
</html>

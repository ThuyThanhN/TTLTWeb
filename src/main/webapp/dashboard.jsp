<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <script src="https://kit.fontawesome.com/33ad855007.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <!-- Css   -->
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
<jsp:include page="sidebar.jsp"></jsp:include>
<!-- Main Content -->
<div class="main-content">
    <!-- Header -->
    <jsp:include page="headerAdmin.jsp"></jsp:include>
    <div class="card-container">
        <h5 class="main-title">Thống kê</h5>
        <div class="row">
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card-wrapper light-green">
                    <div class="card-left drak-green">
                        <i class="fa-solid fa-user fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Tổng người dùng</span>
                        <div class="amount-value">${totalUser} người dùng</div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card-wrapper light-blue">
                    <div class="card-left dark-blue">
                        <i class="fa-solid fa-tags fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Tổng vắc xin</span>
                        <div class="amount-value">${totalVacine} vắc xin</div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card-wrapper light-orange">
                    <div class="card-left dark-orange">
                        <i class="fa-solid fa-calendar-check fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Tổng đơn hàng</span>
                        <div class="amount-value">${totalOrder} đơn hàng</div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-3">
                <div class="card-wrapper light-red">
                    <div class="card-left dark-red">
                        <i class="fa-solid fa-id-badge fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Hết hàng</span>
                        <div class="amount-value">${totalExpire} vắc xin</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="tabular-wrapper">
        <h5 class="main-title">Vắc xin bán chạy trong tháng</h5>
        <div class="table-container">
            <table class="w-100 table table-striped" id="admin">
                <thead>
                <tr>
                    <th>Mã vắc xin</th>
                    <th>Tên vắc xin</th>
                    <th style="width: 60%">Mô tả</th>
                    <th>Số lần đặt</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cd" items="${countOrder}">
                    <tr>
                        <td>${cd.id}</td>
                        <td>${cd.name}</td>
                        <td>${cd.description}</td>
                        <td>${cd.orderCount}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="js/dashboard.js"></script>
</body>
</html>

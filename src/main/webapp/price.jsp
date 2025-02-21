<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bảng giá</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/33ad855007.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/price.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content -->
    <div class="container-lg my-5">
        <div class="price-select-title">
            <h4 class="text-primary">Bảng giá tiêm chủng TTT</h4>
            <div class="dropdown">
                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="sortDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                    Sắp xếp theo
                </button>
                <ul class="dropdown-menu" aria-labelledby="sortDropdown">
                    <li><a class="dropdown-item" href="?sort=price:asc" onclick="sortTable(4, 'asc')">Giá thấp đến cao</a></li>
                    <li><a class="dropdown-item" href="?sort=price:desc" onclick="sortTable(4, 'desc')">Giá cao đến thấp</a></li>
                    <li><a class="dropdown-item" href="?sort=createdAt:desc" onclick="sortTable(5, 'desc')">Vacxin mới nhất </a></li>
                </ul>
            </div>
        </div>
        <div id="vaccine-price-table" class="my-3">
            <table class="table table-bordered text-center">
                <thead class="bg-primary text-white">
                <tr>
                    <th>STT</th>
                    <th>Phòng bệnh</th>
                    <th>Tên vắc-xin</th>
                    <th>Giá bán lẻ/liều (VNĐ)</th>
                    <th>Nước sản xuất</th>
                    <th>Tình trạng</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="vaccine" items="${vaccines}" varStatus="stt">
                    <tr>
                        <td>${stt.index + 1}</td>
                        <td>${vaccine.prevention}</td>
                        <td>${vaccine.name}</td>
                        <td>${vaccine.price}</td>
                        <td>${vaccine.countryOfOrigin}</td>
                        <td>${vaccine.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="payment-methods" class="mb-5">
            <h4 class="text-primary">Các hình thức thanh toán khi tiêm vắc xin tại TTT</h4>
            <p>Có 2 hình thức thanh toán:</p>
            <ul>
                <li>
                    <strong>Thanh toán tiền mặt:</strong> Khách hàng có thể thanh toán trực tiếp tại cơ sở tiêm đã đặt lịch.
                </li>
                <li>
                    <strong>Thanh toán qua các ứng dụng thương mại điện tử:</strong> Dịch vụ thanh toán di động, ví điện tử VNPay-QR, Momo, Apple Pay… ngay tại cơ sở.
                </li>
            </ul>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/show_navbarNav.js"></script>
</html>
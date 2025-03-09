<%@ page import="com.example.provide_vaccine_services.dao.model.Cart" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <!-- Bootstrap CSS -->
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="css/cart.css">
</head>
<body>
<!-- Header -->
<jsp:include page="header.jsp"></jsp:include>
<!-- Content -->
<div class="main">
    <c:set var="cart" value="${requestScope.cart}"/>

    <div class="container my-5">
        <h4 class="text-center mb-4">Danh sách đặt lịch tiêm vắc xin</h4>

        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Danh sách đặt lịch tiêm vắc xin</li>
            </ol>
        </nav>
        <form action="shoppingCart" method="post">
            <!-- Kiểm tra nếu cart tồn tại -->
            <div class="row">
                <c:forEach var="entry" items="${cart.ordersOrderDetailsMap}">
                    <c:set var="orders" value="${entry.key}"/>
                    <c:set var="orderDetails" value="${entry.value}"/>

                    <div class="col-6 d-flex align-items-stretch">
                        <div class="p-2 border rounded mb-2">
                            <div class="row">
                                <div class="col-5">
                                    <img src="image/online-appointment.png" alt="Product" class="img-thumbnail">
                                </div>
                                <div class="col-7">
                                    <div class="mb-1"><b>Ngày hẹn:</b> <f:formatDate value="${orders.appointmentDate}"
                                                                                     pattern="dd-MM-yyyy"/></div>
                                    <div class="mb-1"><b>Giờ hẹn:</b> ${orders.appointmentTime}</div>
                                    <c:if test="${orderDetails != null && orderDetails[0].idVaccine > 0}"><b>Loại
                                        Vaccine:</b></c:if>
                                    <c:if test="${orderDetails != null && orderDetails[0].idPackage > 0}"><b>Gói
                                        Vaccine:</b></c:if>
                                    <ol>
                                        <c:forEach var="detail" items="${orderDetails}">
                                            <li>${(detail.idVaccine>0) ? cart.getVaccineName(detail.idVaccine) : cart.getVaccinePackageName(detail.idPackage)}</li>
                                        </c:forEach>
                                    </ol>
                                    <div style="font-size: 14px">Tổng tiền: <span
                                            class="total">${cart.calculateTotal(orders.id)} đ</span></div>
                                    <a href="dosing_schedule?cartId=${orders.id}">
                                        <img src="image/edit.png" alt="Sửa">
                                    </a>
                                    <a href="#"
                                       class="delete-link"
                                       data-bs-toggle="modal"
                                       data-bs-target="#deleteCart"
                                       data-id="${orders.id}"
                                       data-url="shoppingCart?cartId=${orders.id}&options=remove">
                                        <img src="image/bin.png" alt="Xóa">
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="d-flex gap-3">
                <div>
                    <a href="dosing_schedule">
                        <button type="button" class="btn btn-add">Thêm</button>
                    </a>
                </div>
                <div>
                    <button type="submit" class="btn btn-order">Đặt lịch</button>
                </div>
            </div>
        </form>
    </div>
</div>
<%-- Modal xoa --%>
<div class="modal fade" id="deleteCart" tabindex="-1" aria-labelledby="deleteLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteLabel">Xác nhận</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <a href="#" id="confirmDelete" class="btn btn-danger">Xóa</a>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script src="js/cart.js"></script>
</html>

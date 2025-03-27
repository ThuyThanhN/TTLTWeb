<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vắc xin | Quản trị Admin</title>
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
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <!-- Css   -->
    <link rel="stylesheet" href="../css/main_admin.css">
</head>
<body>
<jsp:include page="sidebar.jsp"></jsp:include>
<!-- Main Content -->
<div class="main-content">
    <!-- Header -->
    <jsp:include page="headerAdmin.jsp"></jsp:include>

    <div class="tabular-wrapper">
        <h5 class="main-title">Vắc xin</h5>
        <div>
            <a href="form-add-vacxin" class="btn btn-add btn-sm">
                <i class="fa-solid fa-plus"></i> Thêm vắc xin
            </a>
        </div>
        <table class="w-100 table table-striped" id="vaccine">
            <thead>
            <tr class="list-header">
                <th scope="col">ID</th>
                <th scope="col">Tên vắc xin</th>
                <th scope="col">Ảnh</th>
                <th scope="col">Số lượng</th>
                <th scope="col">Tình trạng</th>
                <th scope="col">Giá</th>
                <th scope="col">Tính năng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${vaccines}">
                <tr>
                    <td>${v.id}</td>
                    <td>${v.name}</td>
                    <td>
                        <c:if test="${not empty v.imageUrl}">
                            <!-- Kiem tra xem imageUrl co '/upload'-->
                            <c:choose>
                                <c:when test="${v.imageUrl.startsWith('/uploads')}">
                                    <!-- Neu co, hien thi anh tu thu muc uploads -->
                                    <img src="${pageContext.request.contextPath}${v.imageUrl}" alt="Vaccine Image"
                                         width="150" height="50">
                                </c:when>
                                <c:otherwise>
                                    <!-- Neu khong, hien thi anh tu duong dan mac dinh -->
                                    <img src="${v.imageUrl}" alt="Vaccine Image" width="150" height="50">
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    <td>${v.stockQuantity}</td>
                    <td>
                        <span class="status light-green color-green">${v.status}</span>
                    </td>
                    <td>${v.price}đ</td>
                    <td>
                        <!-- Nut sua -->
                        <a href="updateVaccine?id=${v.id}" class="text-decoration-none edit-btn">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                        <!-- Nut xoa -->
                        <a href="#"
                           class="text-decoration-none delete-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#deleteVaccine"
                           data-id="${v.id}" data-name="${v.name}">
                            <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal xoa -->
    <div class="modal fade" id="deleteVaccine" tabindex="-1" aria-labelledby="deleteVaccineLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteVaccineLabel">Xác nhận</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                    <a href="#" id="confirmDelete" class="btn btn-danger">Xóa</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="../js/table-data-vacxin.js"></script>
</html>

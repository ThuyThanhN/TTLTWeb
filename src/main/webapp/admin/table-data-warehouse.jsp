<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kho hàng | Quản trị Admin</title>
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
    <%-- Ajax --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <!-- pdfMake (xuat PDF) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/vfs_fonts.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
        <h5 class="main-title">Kho hàng</h5>
        <div>
            <button class="btn btn-common btn-sm" id="exportPDF">
                <i class="fas fa-file-pdf"></i> Xuất PDF
            </button>

            <button class="btn btn-common btn-sm" id="exportExcel">
                <i class="fas fas fa-file-excel"></i> Xuất Excel
            </button>
        </div>
        <table class="w-100 table table-striped" id="vaccine">
            <thead>
            <tr class="list-header">
                <th scope="col">ID Vaccine</th>
                <th scope="col">Tên Vaccine</th>
                <th scope="col">Số lượng</th>
                <th scope="col"> Tổng Giá </th>
                <th scope="col"> Hao hụt </th>
                <th scope="col"> Ngày hết hạn </th>
                <th scope="col">Chỉnh sửa</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ps" items="${productStocks}">
                <tr data-id="${ps.id}">
                    <td>${ps.vaccineId}</td>
                    <td>${ps.productName}</td>
                    <td>${vaccinesMap.get(ps.vaccineId).getStockQuantity()}</td>
                    <td><f:formatNumber value="${ps.totalPrice}" type="number" pattern="#,##0"/>đ</td>
                    <td>${ps.loss}</td>
                    <td>${ps.expired}</td>
                    <td>
                        <!-- Nut sua -->
                        <a href="#"
                           class="text-decoration-none edit-btn">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                        <!-- Nut xoa -->
                        <a href="#"
                           class="text-decoration-none delete-btn"
<%--                           data-bs-toggle="modal"--%>
<%--                           data-bs-target="#deleteVaccine"--%>
<%--                           data-id="${t.id}" data-name="${t.name}">--%>
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
</html>

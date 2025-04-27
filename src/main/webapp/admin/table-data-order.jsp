<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Đơn hàng | Quản trị Admin</title>
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

<div class="main-content">
    <!-- Header -->
    <jsp:include page="headerAdmin.jsp"></jsp:include>
    <div class="tabular-wrapper">
        <h5 class="main-title">Đơn hàng</h5>
        <div>
            <a href="dosing_schedule" class="btn btn-add btn-sm">
                <i class="fa-solid fa-plus"></i> Thêm đơn hàng
            </a>
            <button class="btn btn-common btn-sm" id="exportPDF">
                <i class="fas fa-file-pdf"></i> Xuất PDF
            </button>

            <button class="btn btn-common btn-sm" id="exportExcel">
                <i class="fas fas fa-file-excel"></i> Xuất Excel
            </button>
        </div>
        <table class="w-100 table table-striped" id="order">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Tên người tiêm</th>
                <th scope="col">Ngày hẹn</th>
                <th scope="col">Giờ hẹn</th>
                <th scope="col">Trung tâm</th>
                <%--                <th scope="col" style="width: 30%;">Các vắc xin trong đơn hàng</th>--%>
                <th scope="col">Trạng thái</th>
                <th scope="col">Tính năng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="o" items="${orders}">
                <tr>
                    <td>${o.order_id}</td>
                    <td>${o.patient_name}</td>
                    <td>
                        <f:formatDate value="${o.appointment_date}" pattern="dd-MM-yyyy"/>
                    </td>
                    <td>${o.appointment_time}</td>
                    <td>${o.center_name}</td>
                    <td>
                        <form id="updateOrderStatus" method="post">
                            <select class="status" name="status" data-order-id="${o.order_id}">
                                <option value="Chưa được duyệt"
                                        class="status-select" ${o.order_status == 'Chưa được duyệt' ? 'selected' : ''}>
                                    Chưa được duyệt
                                </option>
                                <option value="Đã duyệt"
                                        class="status-select" ${o.order_status == 'Đã duyệt' ? 'selected' : ''}>Đã duyệt
                                </option>
                            </select>
                        </form>
                    </td>
                    <td>
                        <button class="btn btn-circle view-detail" data-id="${o.order_id}">
                            <i class="fa-solid fa-eye" style="font-size: 12px"></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="displayDetail" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="modalTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-0" id="modalBody">
                    <p>Loading...</p>
                </div>
            </div>
        </div>
    </div>



</div>

</body>
<script src="../js/table-data-order.js"></script>
</html>
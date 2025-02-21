<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Đơn hàng | Quản trị Admin</title>
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
    <link rel="stylesheet" href="css/main_admin.css">
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
        </div>
        <table class="w-100 table table-striped" id="order">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Tên người tiêm</th>
                <th scope="col">Ngày tiêm</th>
                <th scope="col">Giờ tiêm</th>
                <th scope="col">Tổng tiền</th>
                <th scope="col" style="width: 30%;">Các vắc xin trong đơn hàng</th>
                <th scope="col">Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="o" items="${orders}">
                <tr>
                    <td>${o.order_id}</td>
                    <td>${o.patient_name}</td>
                    <td>
                        <f:formatDate value="${o.appointment_date}" pattern="dd-MM-yyyy" />
                    </td>
                    <td>${o.appointment_time}</td>
                    <td>
                        <f:formatNumber value="${o.total_price}" type="number" pattern="#,##0" />đ
                    </td>
                    <td>${o.vaccine_or_package_names}</td>
                    <td>
                        <form action="updateOrderStatus" method="post">
                            <input type="hidden" name="order_id" value="${o.order_id}">
                            <select class="status" name="status" onchange="this.form.submit()">
                                <option value="Chưa được duyệt" class="status-select" ${o.order_status == 'Chưa được duyệt' ? 'selected' : ''}>Chưa được duyệt</option>
                                <option value="Đã duyệt" class="status-select" ${o.order_status == 'Đã duyệt' ? 'selected' : ''}>Đã duyệt</option>
                            </select>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
<script src="js/table-data-order.js"></script>
</script>
</html>
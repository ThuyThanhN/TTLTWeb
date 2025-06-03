<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Logs | Quản trị Admin</title>
    <!-- Bootstrap, jquery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    <!-- Font awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <!-- pdfMake (xuất PDF) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/vfs_fonts.js"></script>
    <!-- JSZip (xuất Excel) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
    <!-- DataTable Buttons -->
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.3.6/js/buttons.print.min.js"></script>
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!-- Css -->
    <link rel="stylesheet" href="../css/main_admin.css">
</head>
<body>
<jsp:include page="sidebar.jsp"></jsp:include>
<div class="main-content">
    <jsp:include page="headerAdmin.jsp"></jsp:include>
    <div class="tabular-wrapper">
        <h5 class="main-title">Logs hệ thống</h5>
        <div>
            <button class="btn btn-common btn-sm" id="print">
                <i class="fas fa-print"></i> In dữ liệu
            </button>

            <button class="btn btn-common btn-sm" id="exportPDF">
                <i class="fas fa-file-pdf"></i> Xuất PDF
            </button>

            <button class="btn btn-common btn-sm" id="exportExcel">
                <i class="fas fas fa-file-excel"></i> Xuất Excel
            </button>
        </div>
        <table class="w-100 table table-striped" id="logsTable">
            <thead>
            <tr>
                <th>ID</th>
                <th>Mức độ</th>
                <th>Nội dung log</th>
                <th>Người dùng</th>
                <th>Địa chỉ IP</th>
                <th>Thời gian</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="log" items="${logs}">
                <tr>
                    <td>${log.id}</td>
                    <td>${log.logLevel}</td>
                    <td>${log.logMessage}</td>
                    <td>${log.userName}</td>
                    <td>${log.userIp}</td>
                    <td><f:formatDate value="${log.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="../js/table-data-logs.js"></script>
<script>
</script>
</body>
</html>

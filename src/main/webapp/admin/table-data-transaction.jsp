<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giao dịch | Quản trị Admin</title>
    <link rel="icon" type="image/png" href="../image/logo1.png">
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
        <h5 class="main-title">Giao dịch</h5>
        <div>
            <a href="form-add-transaction" class="btn btn-add btn-sm">
                <i class="fa-solid fa-plus"></i> Tạo nhập/xuất kho hàng
            </a>

            <input type="file" id="excelFileInput" accept=".xls,.xlsx" style="display:none" />
            <button class="btn btn-common btn-sm" id="importExcel">
                <i class="fas fa-file-excel"></i> Nhập Excel
            </button>


            <button class="btn btn-common btn-sm" id="exportPDF">
                <i class="fas fa-file-pdf"></i> Xuất PDF
            </button>

            <button class="btn btn-common btn-sm" id="exportExcel">
                <i class="fas fas fa-file-excel"></i> Xuất Excel
            </button>
        </div>
        <table class="w-100 table table-striped" id="transaction">
            <thead>
            <tr class="list-header">
                <th scope="col">ID</th>
                <th scope="col">Tên vắc xin</th>
                <th scope="col">Nhập/Xuất</th>
                <th scope="col">Số lượng</th>
                <th scope="col">Ngày Tạo </th>
                <th scope="col"> Ngày hết hạn vaccine </th>
                <th scope="col">Người thực hiện</th>
                <th scope="col">Chỉnh sửa</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="t" items="${transactions}">
                <tr data-id="${t.transactionId}">
                    <td>${t.transactionId}</td>
                    <td>${vaccines.get(t.vaccineId)}</td>
                    <td>${t.type == 'Nhập' ? 'Nhập' : t.type == 'Xuất' ? 'Xuất' : 'Không xác định'}</td>
                    <td>${t.quantity}</td>
                    <td>${t.date}</td>
                    <td>${t.expiry_date}</td>
                    <td>${t.user.id}</td>
                    <td>
                        <!-- Nut sua -->
                        <a href="updateTransaction?id=${t.transactionId}"
                           class="text-decoration-none edit-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#editTransaction-${t.transactionId}">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                            <%--    Modal sửa --%>
                        <div class="modal fade" id="editTransaction-${t.transactionId}" tabindex="-1" aria-labelledby="editTransactionLabel-${t.transactionId}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editTransactionLabel-${t.transactionId}">Sửa giao dịch ${t.transactionId}</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form class="editTransactionForm" method="post">
                                        <div class="mb-3">
                                                <label for="transactionId" class="form-label">Transaction ID</label>
                                                <input type="text" name="id" class="form-control" id="transactionId" value="${t.transactionId}">
                                            </div>
                                            <div class="mb-3">
                                                <label for="vaccineId" class="form-label">Tên Vaccine</label>
                                                <input type="text" class="form-control" id="vaccineId" value="${vaccines.get(t.vaccineId)}" disabled>
                                            </div>
                                            <div class="mb-3">
                                                <label for="vaccineName" class="form-label">Vaccine ID</label>
                                                <input type="text" name="vaccinesId"  class="form-control" id="vaccineName" value="${t.vaccineId}">
                                            </div>
                                            <div class="mb-3">
                                                <label for="transactionType" class="form-label">Loại giao dịch</label>
                                                <select class="form-select" id="transactionType" name="type" required>
                                                    <option value="Nhập" ${t.type == 'Nhập' ? 'selected' : ''}>Nhập</option>
                                                    <option value="Xuất" ${t.type == 'Xuất' ? 'selected' : ''}>Xuất</option>
                                                </select>
                                            </div>
                                            <div class="mb-3">
                                                <label for="transactionQuantity" class="form-label">Số lượng</label>
                                                <input type="number" name="quantity" class="form-control" id="transactionQuantity" value="${t.quantity}">
                                            </div>
                                            <div class="mb-3">
                                                <label for="expiry_date" class="form-label">Ngày hết hạn</label>
                                                <input type="date" name="expiry_date" class="form-control" id="expiry_date" value="${t.expiry_date}">
                                            </div>
                                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy bỏ</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Nut xoa -->
                        <a href="#"
                           class="text-decoration-none delete-transaction-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#deleteTransaction"
                           data-id="${t.transactionId}"
                           data-name="${vaccines.get(t.vaccineId)}">
                            <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                        </a>

                    </td>
                </tr>
            </c:forEach>
            <!-- Modal xóa -->
            <div class="modal fade" id="deleteTransaction" tabindex="-1" aria-labelledby="deleteTransactionLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteTransactionLabel">Xác nhận xóa giao dịch</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">Bạn có chắc chắn muốn xóa giao dịch <strong id="transactionName"></strong>?</div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button id="confirmDeleteTransaction" class="btn btn-danger">Xóa</button>
                        </div>
                    </div>
                </div>
            </div>
            </tbody>
        </table>
    </div>




</div>
</body>
<script src="../js/table-data-transaction.js"></script>
</html>

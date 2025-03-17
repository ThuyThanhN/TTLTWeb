<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nhà cung cấp | Quản trị Admin</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <%-- Ajax --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <!-- pdfMake (xuat PDF) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/vfs_fonts.js"></script>
    <!-- JSZip (xuat Excel) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
    <!-- DataTable Buttons -->
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.3.6/js/buttons.print.min.js"></script>
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
        <h5 class="main-title">Nhà cung cấp</h5>
        <div>
            <a href="#" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addModal">
                <i class="fa-solid fa-plus"></i> Thêm nhà cung cấp
            </a>

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
        <table class="w-100 table table-striped" id="supplier">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Tên nhà cung cấp</th>
                <th scope="col">Nước sản xuất</th>
                <th scope="col">Tính năng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="supplier" items="${suppliers}">
                <tr>
                    <td>${supplier.id}</td>
                    <td>${supplier.name}</td>
                    <td>${supplier.countryOfOrigin}</td>
                    <td>
                        <!-- Nut sua -->
                        <a href="updateSupplier?id=${supplier.id}" class="text-decoration-none edit-btn"  data-bs-toggle="modal"
                           data-bs-target="#editSupplierModal-${supplier.id}">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                        <!-- Nut xoa -->
                        <a href="#"
                           class="text-decoration-none delete-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#deleteSupplier"
                           data-id="${supplier.id}" data-name="${supplier.name}">
                            <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                        </a>
                        <!-- Modal nut sua -->
                        <div class="modal fade" id="editSupplierModal-${supplier.id}" tabindex="-1" aria-labelledby="editSupplierLabel-${supplier.id}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Chỉnh sửa nhà cung cấp</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form class="editSupplierForm" method="post">
                                            <input type="hidden" name="id" value="${supplier.id}">
                                            <div class="mb-3">
                                                <label for="supplier-name-${supplier.id}" class="form-label">Nhập tên nhà cung cấp</label>
                                                <input type="text" class="form-control" id="supplier-name-${supplier.id}" name="supplier-name" value="${supplier.name}" maxlength="80" data-validate>
                                                <div class="error-message">Vui lòng nhập đúng.</div>
                                            </div>
                                            <div class="mb-3">
                                                <label for="supplier-country-${supplier.id}" class="form-label">Nhập nước sản xuất</label>
                                                <input type="text" class="form-control" id="supplier-country-${supplier.id}" name="supplier-country" value="${supplier.countryOfOrigin}" maxlength="80" data-validate>
                                                <div class="error-message">Vui lòng nhập đúng.</div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" class="btn btn-save">Lưu lại</button>
                                                <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy bỏ</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- Modal nut xoa -->
        <div class="modal fade" id="deleteSupplier" tabindex="-1" aria-labelledby="deleteSupplierLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteSupplierLabel">Xác nhận</h5>
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
        <!-- Modal them nha cung cap -->
        <div class="modal fade" id="addModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addSupplierLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addSupplierLabel">Thêm mới nhà cung cấp</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="addSupplierForm" method="post">
                            <div class="mb-3">
                                <label for="supplier-name" class="form-label">Nhập tên nhà cung cấp</label>
                                <input type="text" class="form-control" id="supplier-name" name="supplier-name" maxlength="80" required data-validate>
                                <div class="error-message">Vui lòng nhập đúng.</div>
                            </div>
                            <div class="mb-3">
                                <label for="supplier-country" class="form-label">Nhập nước sản xuất</label>
                                <input type="text" class="form-control" id="supplier-country" name="supplier-country" maxlength="80" required data-validate>
                                <div class="error-message">Vui lòng nhập đúng.</div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-save">Lưu lại</button>
                                <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy bỏ</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="../js/table-data-supplier.js"></script>
</html>

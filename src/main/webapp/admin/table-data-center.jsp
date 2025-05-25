<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Trung tâm | Quản trị Admin</title>
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
    <!-- JSZip (xuat Excel) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
    <!-- DataTable Buttons -->
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.3.6/js/buttons.print.min.js"></script>
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
        <h5 class="main-title">Trung tâm</h5>
        <div>
            <a href="#" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addCenterModal">
                <i class="fa-solid fa-plus"></i> Thêm trung tâm
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
        <table class="w-100 table table-striped" id="center">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Tên trung tâm</th>
                <th scope="col">Địa chỉ</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Tính năng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="center" items="${centers}">
                <tr data-id="${center.id}">
                    <td>${center.id}</td>
                    <td>${center.name}</td>
                    <td>
                            ${center.address}, ${center.ward}, ${center.district}, ${center.province}
                    </td>
                    <td>${center.phone}</td>
                    <td>
                        <!-- Nut sua -->
                        <a href="updateCenter?id=${center.id}" class="text-decoration-none edit-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#editCenterModal-${center.id}">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                        <!-- Nut xoa -->
                        <a href="#"
                           class="text-decoration-none delete-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#deleteCenter"
                           data-id="${center.id}" data-name="${center.name}">
                            <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                        </a>
                        <!-- Modal nut sua -->
                        <div class="modal fade" id="editCenterModal-${center.id}" data-bs-backdrop="static"
                             data-bs-keyboard="false" tabindex="-1" aria-labelledby="editCenterModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editCenterModalLabel">Cập nhật trung tâm</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form class="editCenterForm" method="post">
                                            <input type="hidden" name="id" value="${center.id}">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <label for="center-name-${center.id}" class="form-label">Nhập
                                                            tên trung tâm</label>
                                                        <input type="text" class="form-control"
                                                               id="center-name-${center.id}" name="center-name"
                                                               value="${center.name}" maxlength="80" required
                                                               data-validate>
                                                        <div class="error-message">Vui lòng nhập đúng.</div>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <label for="center-address-${center.id}" class="form-label">Địa
                                                            chỉ:</label>
                                                        <input type="text" class="form-control"
                                                               id="center-address-${center.id}" name="center-address"
                                                               value="${center.address}" maxlength="80" required>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Tỉnh thành</label>
                                                        <div class="dropdown">
                                                            <input type="text" placeholder="Chọn tỉnh thành"
                                                                   name="center-province"
                                                                   value="${center.province}"
                                                                   class="form-control dropdown-toggle province-select"
                                                                   id="province-select-${center.id}"
                                                                   data-code="" data-bs-toggle="dropdown"
                                                                   aria-expanded="false">
                                                            <i class="fa-solid fa-angle-down"></i>
                                                            <ul class="dropdown-menu province-menu"></ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Quận huyện</label>
                                                        <div class="dropdown">
                                                            <input type="text" placeholder="Chọn quận huyện"
                                                                   name="center-district"
                                                                   value="${center.district}"
                                                                   class="form-control dropdown-toggle district-select"
                                                                   id="district-select-${center.id}"
                                                                   data-code="" data-bs-toggle="dropdown"
                                                                   aria-expanded="false">
                                                            <i class="fa-solid fa-angle-down"></i>
                                                            <ul class="dropdown-menu district-menu"></ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="mb-3">
                                                        <label class="form-label">Phường xã</label>
                                                        <div class="dropdown">
                                                            <input type="text" placeholder="Chọn phường xã"
                                                                   name="center-ward"
                                                                   value="${center.ward}"
                                                                   class="form-control dropdown-toggle ward-select"
                                                                   id="ward-select-${center.id}"
                                                                   data-code="" data-bs-toggle="dropdown"
                                                                   aria-expanded="false">
                                                            <i class="fa-solid fa-angle-down"></i>
                                                            <ul class="dropdown-menu ward-menu"></ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="mb-3">
                                                        <label for="center-phone-${center.id}" class="form-label">Số
                                                            điện thoại</label>
                                                        <input type="tel" class="form-control"
                                                               id="center-phone-${center.id}" name="center-phone"
                                                               value="${center.phone}" required data-phone>
                                                        <div class="error-message">Số điện thoại không hợp lệ.</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" class="btn btn-save">Lưu lại</button>
                                                <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy
                                                    bỏ
                                                </button>
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
        <!-- Modal them trung tam -->
        <div class="modal fade" id="addCenterModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="addCenterModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addCenterLabel">Thêm mới trung tâm</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="addCenterForm" method="post">
                            <div class="row">
                                <div class="col-12">
                                    <div class="mb-3">
                                        <label for="center-name" class="form-label">Nhập tên trung tâm</label>
                                        <input type="text" class="form-control" id="center-name" name="center-name"
                                               maxlength="80" required data-validate>
                                        <div class="error-message">Vui lòng nhập đúng.</div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="mb-3">
                                        <label for="center-address" class="form-label">Địa chỉ:</label>
                                        <input type="text" class="form-control" id="center-address"
                                               name="center-address" maxlength="80" required>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="province" class="form-label">Tỉnh thành</label>
                                        <div class="dropdown">
                                            <input type="text" placeholder="Chọn" id="province" name="center-province"
                                                   class="form-control dropdown-toggle province-select" data-code=""
                                                   data-bs-toggle="dropdown" aria-expanded="false" required>
                                            <i class="fa-solid fa-angle-down"></i>
                                            <ul class="dropdown-menu province-menu"></ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="district" class="form-label">Quận huyện</label>
                                        <div class="dropdown">
                                            <input type="text" placeholder="Chọn tỉnh thành trước" id="district"
                                                   name="center-district"
                                                   class="form-control dropdown-toggle district-select" data-code=""
                                                   data-bs-toggle="dropdown" aria-expanded="false" required>
                                            <i class="fa-solid fa-angle-down"></i>
                                            <ul class="dropdown-menu district-menu"></ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="ward" class="form-label">Phường xã</label>
                                        <div class="dropdown">
                                            <input type="text" placeholder="Chọn quận huyện trước" id="ward"
                                                   name="center-ward"
                                                   class="form-control dropdown-toggle ward-select" data-code=""
                                                   data-bs-toggle="dropdown" aria-expanded="false" required>
                                            <i class="fa-solid fa-angle-down"></i>
                                            <ul class="dropdown-menu ward-menu"></ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="center-phone" class="form-label">Số điện thoại</label>
                                        <input type="tel" class="form-control" id="center-phone" name="center-phone"
                                               required data-phone>
                                        <div class="error-message">Số điện thoại không hợp lệ.</div>
                                    </div>
                                </div>
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
        <!-- Modal xoa trung tam -->
        <div class="modal fade" id="deleteCenter" tabindex="-1" aria-labelledby="deleteCenterLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteCenterLabel">Xác nhận</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                        <button id="confirmDelete" class="btn btn-danger">Xóa</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../js/admin_address.js"></script>
<script src="../js/table-data-center.js"></script>
</body>
</html>

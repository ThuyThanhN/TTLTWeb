<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nhân viên | Quản trị Admin</title>
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
<!-- Main Content -->
<div class="main-content">
    <!-- Header -->
    <jsp:include page="headerAdmin.jsp"></jsp:include>

    <div class="tabular-wrapper">
        <h5 class="main-title">Nhân viên</h5>
        <div>
            <a href="addStaff" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addModal">
                <i class="fa-solid fa-plus"></i> Thêm nhân viên
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
        <table class="w-100 table table-striped" id="staff">
            <thead>
            <tr class="list-header">
                <th scope="col">ID</th>
                <th scope="col">Họ và tên</th>
                <th scope="col">Địa chỉ</th>
                <th scope="col">Giới tính</th>
                <th scope="col">Ngày sinh</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Tính năng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="staff" items="${staffs}">
                <tr data-id="${staff.id}">
                    <td>${staff.id}</td>
                    <td>${staff.fullname}</td>
                    <td>
                            ${staff.address}, ${staff.province}, ${staff.district}, ${staff.ward}
                    </td>
                    <td>${staff.gender}</td>
                    <td>
                        <f:formatDate value="${staff.dateOfBirth}" pattern="dd-MM-yyyy" />
                    </td>
                    <td>${staff.phone}</td>
                    <td>
                        <!-- Nut sua -->
                        <a href="updateStaff?id=${staff.id}" class="text-decoration-none edit-btn"  data-bs-toggle="modal"
                           data-bs-target="#editStaff-${staff.id}">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                        <!-- Nut xoa -->
                        <a href="#"
                           class="text-decoration-none delete-btn"
                           data-bs-toggle="modal"
                           data-bs-target="#deleteStaff"
                           data-id="${staff.id}" data-name="${staff.fullname}">
                            <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                        </a>

                            <%-- Modal sua --%>
                        <div class="modal fade" id="editStaff-${staff.id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                             aria-labelledby="editLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editLabel">Cập nhật nhân viên</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form class="editStaffForm" method="post">
                                            <input type="hidden" name="id" value="${staff.id}">
                                            <div class="row">
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label for="fullname-${staff.id}" class="form-label">Họ và tên</label>
                                                        <input type="text" class="form-control" id="fullname-${staff.id}" name="fullname" value="${staff.fullname}" required>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3 position-relative">
                                                        <label for="gender${staff.id}" class="form-label">Giới tính</label>
                                                        <select class="form-select" id="gender${staff.id}" name="gender">
                                                            <option value="" selected>--Chọn giới tính--</option>
                                                            <option value="Nam" ${'Nam'.equals(staff.gender) ? 'selected' : ''}>Nam</option>
                                                            <option value="Nữ" ${'Nữ'.equals(staff.gender) ? 'selected' : ''}>Nữ</option>
                                                            <option value="Khác" ${'Khác'.equals(staff.gender) ? 'selected' : ''}>Khác</option>
                                                        </select>
                                                        <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label for="ident-${staff.id}" class="form-label">Mã định danh </label>
                                                        <input type="text" class="form-control" id="ident-${staff.id}" name="ident" value="${staff.identification}">
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label for="date-${staff.id}" class="form-label">Ngày sinh </label> <br>
                                                        <input type="date" class="form-control" name="date" id="date-${staff.id}" value="${staff.dateOfBirth}">
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label for="email-${staff.id}" class="form-label">Email </label> <br>
                                                        <input type="email" class="form-control" name="email" id="email-${staff.id}" value="${staff.email}">
                                                        <div class="error-message">Vui lòng nhập một địa chỉ email hợp lệ.</div>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <label for="address-${staff.id}" class="form-label">Địa chỉ</label>
                                                        <input type="text" class="form-control" id="address-${staff.id}" value="${staff.address}" name="address" maxlength="80" required>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label class="form-label">Tỉnh thành</label>
                                                        <div class="dropdown">
                                                            <input type="text" placeholder="Chọn tỉnh thành"
                                                                   name="province"
                                                                   value="${staff.province}"
                                                                   class="form-control dropdown-toggle province-select"
                                                                   id="province-select-${staff.id}"
                                                                   data-code="" data-bs-toggle="dropdown"
                                                                   aria-expanded="false">
                                                            <i class="fa-solid fa-angle-down"></i>
                                                            <ul class="dropdown-menu province-menu"></ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label class="form-label">Quận huyện</label>
                                                        <div class="dropdown">
                                                            <input type="text" placeholder="Chọn quận huyện"
                                                                   name="district"
                                                                   value="${staff.district}"
                                                                   class="form-control dropdown-toggle district-select"
                                                                   id="district-select-${staff.id}"
                                                                   data-code="${districtCode}" data-bs-toggle="dropdown"
                                                                   aria-expanded="false">
                                                            <i class="fa-solid fa-angle-down"></i>
                                                            <ul class="dropdown-menu district-menu"></ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="mb-3">
                                                        <label class="form-label">Phường xã</label>
                                                        <div class="dropdown">
                                                            <input type="text" placeholder="Chọn phường xã"
                                                                   name="ward"
                                                                   value="${staff.ward}"
                                                                   class="form-control dropdown-toggle ward-select"
                                                                   id="ward-select-${staff.id}"
                                                                   data-code="${wardCode}" data-bs-toggle="dropdown"
                                                                   aria-expanded="false">
                                                            <i class="fa-solid fa-angle-down"></i>
                                                            <ul class="dropdown-menu ward-menu"></ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-3">
                                                    <div class="mb-3">
                                                        <label for="phone-${staff.id}" class="form-label">Số điện thoại</label>
                                                        <input type="tel" class="form-control" id="phone-${staff.id}" name="phone" value="${staff.phone}" required data-phone>
                                                        <div class="error-message">Số điện thoại không hợp lệ.</div>
                                                    </div>
                                                </div>
                                                <div class="col-3">
                                                    <div class="mb-3 position-relative">
                                                        <label for="role-${staff.id}" class="form-label">Chức vụ</label>
                                                        <select class="form-select" id="role-${staff.id}" name="role" required>
                                                            <option value="" selected>--Chọn chức vụ--</option>
                                                            <option value="Admin" ${staff.role == 1 ? 'selected' : ''}>Admin</option>
                                                            <option value="Nhân viên" ${staff.role == 2 ? 'selected' : ''}>Nhân viên</option>
                                                        </select>
                                                        <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                                    </div>
                                                </div>
                                                <div class="col-3">
                                                    <div class="mb-3 position-relative">
                                                        <label for="module-${staff.id}" class="form-label">Phân quyền:</label>
                                                        <select class="form-select" id="module-${staff.id}" name="module" required>
                                                            <option value="" ${staff.module == null? 'selected' : ''}>-- Chọn --</option>
                                                            <option value="none" ${staff.module == 'none' ? 'selected' : ''}>Nhân viên</option>
                                                            <option value="staff" ${staff.module == 'staff' ? 'selected' : ''}>Quản lý nhân viên</option>
                                                            <option value="customer" ${staff.module == 'customer' ? 'selected' : ''}>Quản lý khách hàng</option>
                                                            <option value="order" ${staff.module == 'order' ? 'selected' : ''}>Quản lý đơn hàng</option>
                                                            <option value="vaccine" ${staff.module == 'vaccine' ? 'selected' : ''}>Quản lý vắc xin</option>
                                                            <option value="package" ${staff.module == 'package' ? 'selected' : ''}>Quản lý gói vắc xin</option>
                                                            <option value="supplier" ${staff.module == 'supplier' ? 'selected' : ''}>Quản lý nhà cung cấp</option>
                                                            <option value="center" ${staff.module == 'center' ? 'selected' : ''}>Quản lý trung tâm</option>
                                                            <option value="log" ${staff.module == 'log' ? 'selected' : ''}>Quản lý log</option>
                                                            <option value="transaction" ${staff.module == 'transaction' ? 'selected' : ''}>Quản lý giao dịch</option>
                                                            <option value="warehouse" ${staff.module == 'warehouse' ? 'selected' : ''}>Quản lý kho hàng</option>
                                                        </select>
                                                        <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                                    </div>
                                                </div>
                                                <div class="col-3">
                                                    <div class="mb-3">
                                                        <label for="pass-${staff.id}" class="form-label">Mật khẩu</label>
                                                        <input type="password" class="form-control" id="pass-${staff.id}" name="password" value="${staff.password}" required data-password>
                                                        <div class="error-message">Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, chữ số và ký tự đặc biệt.</div>
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
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal xoa -->
    <div class="modal fade" id="deleteStaff" tabindex="-1" aria-labelledby="deleteStaffLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteStaffLabel">Xác nhận</h5>
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

    <%-- Modal them --%>
    <div class="modal fade" id="addModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="addStaffModal" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCenterLabel">Thêm mới nhân viên</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addStaffForm" method="post">
                        <div class="row">
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="fullname" class="form-label">Họ và tên</label>
                                    <input type="text" class="form-control" id="fullname" name="fullname" required>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3 position-relative">
                                    <label for="gender" class="form-label">Giới tính</label>
                                    <select class="form-select" id="gender" name="gender">
                                        <option value="" selected>--Chọn giới tính--</option>
                                        <option value="Nam">Nam</option>
                                        <option value="Nữ">Nữ</option>
                                        <option value="Khác">Khác</option>
                                    </select>
                                    <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="ident" class="form-label">Mã định danh: </label>
                                    <input type="text" class="form-control" id="ident" name="ident">
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="date" class="form-label">Ngày sinh: </label> <br>
                                    <input type="date" class="form-control" name="date" id="date">
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email: </label> <br>
                                    <input type="email" class="form-control" name="email" id="email" data-email>
                                    <div class="error-message">Vui lòng nhập một địa chỉ email hợp lệ.</div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="mb-3">
                                    <label for="staff-address" class="form-label">Địa chỉ:</label>
                                    <input type="text" class="form-control" id="staff-address" name="address" maxlength="80" required>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="province" class="form-label">Tỉnh thành</label>
                                    <div class="dropdown">
                                        <input type="text" placeholder="Chọn" id="province" name="province"
                                               class="form-control dropdown-toggle province-select" data-code=""
                                               data-bs-toggle="dropdown" aria-expanded="false" required>
                                        <i class="fa-solid fa-angle-down"></i>
                                        <ul class="dropdown-menu province-menu"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="district" class="form-label">Quận huyện</label>
                                    <div class="dropdown">
                                        <input type="text" placeholder="Chọn tỉnh thành trước" id="district"
                                               name="district"
                                               class="form-control dropdown-toggle district-select" data-code=""
                                               data-bs-toggle="dropdown" aria-expanded="false" required>
                                        <i class="fa-solid fa-angle-down"></i>
                                        <ul class="dropdown-menu district-menu"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="mb-3">
                                    <label for="ward" class="form-label">Phường xã</label>
                                    <div class="dropdown">
                                        <input type="text" placeholder="Chọn quận huyện trước" id="ward"
                                               name="ward"
                                               class="form-control dropdown-toggle ward-select" data-code=""
                                               data-bs-toggle="dropdown" aria-expanded="false" required>
                                        <i class="fa-solid fa-angle-down"></i>
                                        <ul class="dropdown-menu ward-menu"></ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="mb-3">
                                    <label for="staff-phone" class="form-label">Số điện thoại</label>
                                    <input type="tel" class="form-control" id="staff-phone" name="phone" required data-phone>
                                    <div class="error-message">Số điện thoại không hợp lệ.</div>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="mb-3 position-relative">
                                    <label for="role" class="form-label">Chức vụ</label>
                                    <select class="form-select" id="role" name="role" required>
                                        <option value="" selected>--Chọn chức vụ--</option>
                                        <option value="1">Admin</option>
                                        <option value="2 viên">Nhân viên</option>
                                    </select>
                                    <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="mb-3 position-relative">
                                    <label for="module" class="form-label">Phân quyền:</label>
                                    <select class="form-select" id="module" name="module" required>
                                        <option value="" selected>--Phân quyền--</option>
                                        <option value="none">Nhân viên</option>
                                        <option value="staff">Quản lý nhân viên</option>
                                        <option value="customer">Quản lý khách hàng</option>
                                        <option value="order">Quản lý đơn hàng</option>
                                        <option value="vaccine">Quản lý vắc xin</option>
                                        <option value="package">Quản lý gói vắc xin</option>
                                        <option value="supplier">Quản lý nhà cung cấp</option>
                                        <option value="center">Quản lý trung tâm</option>
                                        <option value="log">Quản lý log</option>
                                        <option value="center">Quản lý giao dịch</option>
                                        <option value="log">Quản lý kho hàng</option>
                                    </select>
                                    <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="mb-3">
                                    <label for="password" class="form-label">Mật khẩu</label>
                                    <input type="password" class="form-control" id="password" name="password" required data-password>
                                    <div class="error-message">Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, chữ số và ký tự đặc biệt.</div>
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
</div>
</body>
<script src="../js/admin_address.js"></script>
<script src="../js/table-data-staff.js"></script>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Trung tâm | Quản trị Admin</title>
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
  <!-- DataTable -->
  <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
  <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
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
      <a  href="#" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addCenterModal">
        <i class="fa-solid fa-plus"></i> Thêm trung tâm
      </a>
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
        <tr>
          <td>${center.id}</td>
          <td>${center.name}</td>
          <td>
              ${center.address}, ${center.province}, ${center.district}, ${center.ward}
          </td>
          <td>${center.phone}</td>
          <td>
            <!-- Nut sua -->
            <a href="updateCenter?id=${center.id}" class="text-decoration-none edit-btn" data-bs-toggle="modal"
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
            <div class="modal fade" id="editCenterModal-${center.id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editCenterModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="editCenterModalLabel">Cập nhật trung tâm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form action="updateCenter" method="post">
                      <input type="hidden" name="center-id" value="${center.id}">
                      <div class="row">
                        <div class="col-12">
                          <div class="mb-3">
                            <label for="center-name-${center.id}" class="form-label">Nhập tên trung tâm</label>
                            <input type="text" class="form-control" id="center-name-${center.id}" name="center-name" value="${center.name}" maxlength="80" required>
                          </div>
                        </div>
                        <div class="col-12">
                          <div class="mb-3">
                            <label for="center-address-${center.id}" class="form-label">Địa chỉ:</label>
                            <input type="text" class="form-control" id="center-address-${center.id}" name="center-address" value="${center.address}" maxlength="80" required>
                          </div>
                        </div>
                        <div class="col-6">
                          <div class="mb-3">
                            <div class="dropdown">
                              <label for="province-select-${center.id}" class="form-label">Tỉnh thành</label> <br>
                              <input type="text" placeholder="Chọn" name="center-province"  value="${center.province}" class="form-control dropdown-toggle province-select" id="province-select-${center.id}" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="fa-solid fa-angle-down"></i>
                              <ul class="dropdown-menu province-menu"></ul>
                            </div>
                          </div>
                        </div>
                        <div class="col-6">
                          <div class="mb-3">
                            <div class="dropdown">
                              <label for="district-select-${center.id}" class="form-label">Quận huyện</label> <br>
                              <input type="text"  name="center-district"  value="${center.district}" class="form-control dropdown-toggle required-field" id="district-select-${center.id}" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="fa-solid fa-angle-down"></i>
                              <ul class="dropdown-menu" id="district-menu-${center.id}"></ul>
                            </div>
                          </div>
                        </div>
                        <div class="col-6">
                          <div class="mb-3">
                            <div class="dropdown">
                              <label for="ward-select-${center.id}" class="form-label">Phường xã</label> <br>
                              <input type="text" placeholder="Chọn quận huyện trước" name="center-ward"  value="${center.ward}" class="form-control dropdown-toggle required-field" id="ward-select-${center.id}" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="fa-solid fa-angle-down"></i>
                              <ul class="dropdown-menu" id="ward-menu-${center.id}"></ul>
                            </div>
                          </div>
                        </div>
                        <div class="col-6">
                          <div class="mb-3">
                            <label for="center-phone-${center.id}" class="form-label">Số điện thoại</label>
                            <input type="tel" class="form-control" id="center-phone-${center.id}" name="center-phone" value="${center.phone}" required>
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
    <!-- Modal nut xoa -->
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
            <a href="#" id="confirmDelete" class="btn btn-danger">Xóa</a>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal them nha cung cap -->
    <div class="modal fade" id="addCenterModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addCenterModal" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="addCenterLabel">Thêm mới trung tâm</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form action="addCenter" method="post">
              <div class="row">
                <div class="col-12">
                  <div class="mb-3">
                    <label for="center-name" class="form-label">Nhập tên trung tâm</label>
                    <input type="text" class="form-control" id="center-name" name="center-name" maxlength="80" required>
                  </div>
                </div>
                <div class="col-12">
                  <div class="mb-3">
                    <label for="center-address" class="form-label">Địa chỉ:</label>
                    <input type="text" class="form-control" id="center-address" name="center-address" maxlength="80" required>
                  </div>
                </div>
                <div class="col-6">
                  <div class="mb-3">
                    <div class="dropdown">
                      <label for="province" class="form-label">Tỉnh thành</label> <br>
                      <input type="text" placeholder="Chọn" id="province" name="center-province" class="form-control dropdown-toggle province-select" data-bs-toggle="dropdown" aria-expanded="false" required>
                      <i class="fa-solid fa-angle-down"></i>
                      <ul class="dropdown-menu province-menu"></ul>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                  <div class="mb-3">
                    <div class="dropdown">
                      <label for="district" class="form-label">Quận huyện</label> <br>
                      <input type="text" placeholder="Chọn tỉnh thành trước" id="district" name="center-district" class="form-control dropdown-toggle district-select hidden" required>
                      <i class="fa-solid fa-angle-down"></i>
                      <ul class="dropdown-menu district-menu"></ul>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                  <div class="mb-3">
                    <div class="dropdown">
                      <label for="ward" class="form-label">Phường xã</label> <br>
                      <input type="text" placeholder="Chọn quận huyện trước" id="ward" name="center-ward" class="form-control dropdown-toggle ward-select hidden" required>
                      <i class="fa-solid fa-angle-down"></i>
                      <ul class="dropdown-menu ward-menu"></ul>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                  <div class="mb-3">
                    <label for="center-phone" class="form-label">Số điện thoại</label>
                    <input type="tel" class="form-control" id="center-phone" name="center-phone" required>
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
</div>
<script src="../js/api_address.js"></script>
<script src="../js/table-data-center.js"></script>
</body>
</html>

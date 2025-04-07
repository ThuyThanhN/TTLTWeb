<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gói vắc xin | Quản trị Admin</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
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
        <h5 class="main-title">Gói vắc xin</h5>
        <div>
            <a href="" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addModal">
                <i class="fa-solid fa-plus"></i> Tạo mới gói vắc xin
            </a>

        </div>
        <table class="w-100 table table-striped" id="package">
            <thead>
            <tr class="list-header">
                <th scope="col">ID</th>
                <th scope="col">Tên gói vắc xin</th>
                <th scope="col">Tổng giá</th>
<%--                <th scope="col">Các vắc xin trong gói</th>--%>
                <th scope="col">Tính năng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="pkg" items="${packageList}">
                <tr data-id="${pkg.package_id}">
                    <td>${pkg.package_id}</td>
                    <td>${pkg.package_name}</td>
                    <td><f:formatNumber value="${pkg.total_price}" type="number" pattern="#,##0" />đ</td>
                <%--                    <td>--%>
<%--                        <c:forEach var="vaccine" items="${pkg.vaccines}" varStatus="status">--%>
<%--                            ${vaccine.vaccine_name}<c:if test="${!status.last}">, </c:if>--%>
<%--                        </c:forEach>--%>
<%--                    </td>--%>
                    <td>
                        <!-- Nut sua -->
                        <a href="updatePackage?id=${pkg.package_id}" class="text-decoration-none edit-btn"
                           data-bs-target="#editModal-${pkg.package_id}" data-bs-toggle="modal">
                            <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                        </a>
                        <!-- Nut xoa -->
                        <a href="#" class="text-decoration-none delete-btn"
                           data-bs-toggle="modal" data-bs-target="#deleteModal"
                           data-id="${pkg.package_id}"
                           data-name="${pkg.package_name}">
                            <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                        </a>

                            <%-- Sua   --%>
                        <div class="modal fade" id="editModal-${pkg.package_id}" data-bs-backdrop="static"
                             data-bs-keyboard="false" tabindex="-1"
                             aria-labelledby="editLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editLabel">Cập nhật gói vắc xin</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="updatePackage" method="post">
                                            <input type="hidden" name="id" value="${pkg.package_id}">
                                            <div class="row">
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <label for="package-select-${pkg.package_id}"
                                                               class="form-label">Tên gói</label>
                                                        <input type="text" class="form-control"
                                                               id="package-select-${pkg.package_id}"
                                                               value="${pkg.package_name}" name="package-name" required>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <span class="form-label" style="font-weight: 500">Chọn vắc xin</span>
                                                    <div class="mb-3 border p-3 border-vaccine" style="margin-top: .5rem">
                                                        <div id="vaccines-${pkg.package_id}" name="vaccine">
                                                            <c:forEach var="v" items="${vaccines}">
                                                                <label class="vaccine-option">
                                                                    <c:set var="checked" value="false"/>
                                                                    <c:forEach var="vaccine" items="${pkg.vaccine_ids}">
                                                                        <c:if test="${vaccine == v.id}">
                                                                            <c:set var="checked" value="true"/>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <input type="checkbox" class="vaccine-checkbox"
                                                                           name="vaccineId"
                                                                           value="${v.id}" data-id="${v.id}"
                                                                           data-name="${v.name}" data-price="${v.price}"
                                                                        ${checked ? 'checked' : ''}>
                                                                        ${v.name}
                                                                </label>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <span class="form-label" style="font-weight: 500">Vắc xin đã chọn</span>
                                                        <c:forEach var="vaccine" items="${pkg.vaccine_ids}">
                                                            <input type="hidden" name="vaccine-dosage" id="vaccine-dosage-${vaccine}" data-id="${vaccine}" class="vaccine-dosage"
                                                                   data-value="${(pkg.vaccine_dosage_map != null && pkg.vaccine_dosage_map.get(vaccine) > 0) ? pkg.vaccine_dosage_map.get(vaccine) : '1' }"/>
                                                        </c:forEach>
                                                            <%--Hien vaccine da chon--%>
                                                        <div class="border list-packages"
                                                             style="height: 100px; overflow-y: auto">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="mb-3">
                                                    <span class="form-label" style="font-weight: 500">Tổng giá</span>
                                                    <input type="text" value="${pkg.total_price}"
                                                           class="form-control totalPriceDisplay" name="totalPrice" style="margin-top: .5rem">
                                                </div>
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <label for="age-select-${pkg.package_id}" class="form-label">Độ tuổi</label>
                                                        <select class="form-select form-control"
                                                                id="age-select-${pkg.package_id}" name="ageId"
                                                                onchange="updatePackageName(this)" required>
                                                            <option value="">---Chọn độ tuổi ---</option>
                                                            <c:forEach var="a" items="${ages}">
                                                                <option value="${a.id}" ${a.id == pkg.age_id ? 'selected' : ''}>${a.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <label for="description-name-${pkg.package_id}">Mô tả</label>
                                                        <textarea class="form-control"
                                                                  id="description-name-${pkg.package_id}"
                                                                  name="description-name" rows="5" style="margin-top: .5rem">
                                                                ${pkg.package_description}
                                                        </textarea>
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

    <%-- Modal them goi vac xin --%>
    <div class="modal fade" id="addModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="addStaffModal" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCenterLabel">Thêm gói vắc xin</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addPMappingForm" method="post">
                        <div class="row">
                            <div class="col-12">
                                <div class="mb-3">
                                    <label for="package-select" class="form-label">Tên gói</label>
                                    <input type="text" class="form-control" id="package-select" name="package-name"
                                           required>
                                </div>
                            </div>
                            <div class="col-12">
                                <span class="form-label" style="font-weight: 500">Chọn vắc xin</span>
                                <div class="mb-3 border p-3 border-vaccine" style="margin-top: .5rem">
                                    <div id="vaccines" name="vaccine">
                                        <c:forEach var="v" items="${vaccines}">
                                            <label class="vaccine-option">
                                                <input type="checkbox" class="vaccine-checkbox" name="vaccineId"
                                                       value="${v.id}" data-id="${v.id}" data-name="${v.name}"
                                                       data-price="${v.price}">${v.name}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="mb-3">
                                    <span class="form-label" style="font-weight: 500">Vắc xin đã chọn</span>
                                    <%--Hien vaccine da chon--%>
                                    <div class="border list-packages" style="height: 100px; overflow-y: auto; margin-top: .5rem"></div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="mb-3">
                                    <span class="form-label" style="font-weight: 500">Tổng giá</span>
                                    <input type="text" id="" class="form-control totalPriceDisplay" name="totalPrice"
                                           value="0" style="margin-top: .5rem">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="mb-3">
                                    <label for="age-select" class="form-label">Độ tuổi</label>
                                    <select class="form-select form-control" id="age-select" name="age-select"
                                            required>
                                        <option value="0">---Chọn độ tuổi ---</option>
                                        <c:forEach var="a" items="${ages}">
                                            <option value="${a.id}">${a.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="mb-3">
                                    <label for="description-packag">Mô tả</label>
                                    <textarea class="form-control" id="description-packag" name="description-name"
                                              rows="5" style="margin-top: .5rem"></textarea>
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

    <!-- Modal nut xoa -->
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteLabel">Xác nhận</h5>
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

<script src="../js/table-data-vax-package.js"></script>
</html>
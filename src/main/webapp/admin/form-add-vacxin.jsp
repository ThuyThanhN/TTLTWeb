
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm vắc xin | Quản trị Admin </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <script src="https://cdn.ckeditor.com/ckeditor5/37.0.1/classic/ckeditor.js"></script>
    <link rel="stylesheet" href="../css/main_admin.css">
</head>
<body>
<jsp:include page="sidebar.jsp"></jsp:include>
<!-- Main Content -->
<div class="main-content">
    <!-- Header -->
    <jsp:include page="headerAdmin.jsp"></jsp:include>

    <div class="info-vaccine-wrapper">
        <form action="addVaccine" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-12 col-md-7">
                    <div class="vaccine-info-form">
                        <div class="section-title border-bottom p-3">Tạo mới vắc xin</div>
                        <div class="p-3">
                            <div class="row">
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="vaccine-name" class="form-label">Tên vắc xin</label>
                                        <input type="text" class="form-control" id="vaccine-name" name="vaccineName">
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="quantity-vaccine" class="form-label">Số lượng</label>
                                        <input type="number" class="form-control" id="quantity-vaccine" name="quantityVaccine">
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="price-vaccine" class="form-label">Giá bán</label>
                                        <input type="text" class="form-control" id="price-vaccine" name="price">
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="form-select-vaccine" class="form-label">Tình trạng</label>
                                        <select class="form-select form-control" id="form-select-vaccine" name="status">
                                            <option value="">---Chọn tình trạng---</option>
                                            <option value="1">Còn hàng</option>
                                            <option value="2">Hết hàng</option>
                                        </select>
                                    </div>
                                </div>
                                <%--Hien danh sach NCC--%>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="supplier-select" class="form-label">Nhà cung cấp</label>
                                        <select class="form-select form-control" id="supplier-select" name="supplier">
                                            <option value="">---Chọn nhà cung cấp---</option>
                                            <c:forEach var="s" items="${suppliers}">
                                                <option value="${s.id}">${s.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-label">Ảnh vắc xin</div>
                                    <div class="mb-3">
                                        <div class="w-100 border-black border-dotted position-relative pt-3" style="height: 96px">
                                            <label for="upload" class="position-absolute w-100 h-100 text-center d-flex flex-column  align-items-center justify-content-center">
                                                <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 448 512" height="24px" width="24px" xmlns="http://www.w3.org/2000/svg">
                                                    <path d="M416 208H272V64c0-17.67-14.33-32-32-32h-32c-17.67 0-32 14.33-32 32v144H32c-17.67 0-32 14.33-32 32v32c0 17.67 14.33 32 32 32h144v144c0 17.67 14.33 32 32 32h32c17.67 0 32-14.33 32-32V304h144c17.67 0 32-14.33 32-32v-32c0-17.67-14.33-32-32-32z"></path>
                                                </svg>
                                                <div>Click to upload images</div>
                                            </label>
                                            <input id="upload" class="hidden-image" type="file" name="file">
                                        </div>
                                        <!-- Placeholder for displaying images -->
                                        <div id="image-preview" class="mt-3"></div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="editor-des" class="form-label">Mô tả vắc xin</label>
                                        <textarea id="editor-des" class="ckeditor" name="description"></textarea>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <label for="editor-pre" class="form-label">Phòng bệnh</label>
                                        <textarea id="editor-pre" class="ckeditor" name="prevention"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-5">
                    <div class="vaccine-info-form">
                        <div class="section-title border-bottom p-3">Thông tin chi tiết</div>
                        <div class="p-3">
                            <div class="d-flex">
                                <div class="nav-item active" data-target="#vaccine-content">Nội dung</div>
                                <div class="nav-item" data-target="#object-content">Đối tượng</div>
                                <div class="nav-item" data-target="#schedule-content">Phác đồ tiêm</div>
                                <div class="nav-item" data-target="#reaction-content">Phản ứng</div>
                            </div>
                            <div class="my-3 content" id="vaccine-content">
                                <div>
                                    <div class="accordion" id="accordionExample">
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingOne">
                                                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                                    Nguồn gốc
                                                </button>
                                            </h2>
                                            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                                <div class="col-12">
                                                    <div class="mb-3">
                                                        <textarea class="ckeditor" name="editor-ng"></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingTwo">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                                    Đường tiêm
                                                </button>
                                            </h2>
                                            <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                                                <div class="form-floating p-3">
                                                    <textarea class="ckeditor" name="editor-dt"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingThree">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                                    Chống chỉ định
                                                </button>
                                            </h2>
                                            <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
                                                <div class="form-floating p-3">
                                                    <textarea class="ckeditor" name="editor-ccd"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingFour">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                                    Thận trọng
                                                </button>
                                            </h2>
                                            <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
                                                <div class="form-floating p-3">
                                                    <textarea class="ckeditor" name="editor-tt"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingFive">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                                    Tương tác thuốc
                                                </button>
                                            </h2>
                                            <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive" data-bs-parent="#accordionExample">
                                                <div class="form-floating p-3">
                                                    <textarea class="ckeditor" name="editor-ttt"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingSix">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseFive">
                                                    Tác dụng phụ
                                                </button>
                                            </h2>
                                            <div id="collapseSix" class="accordion-collapse collapse" aria-labelledby="headingSix" data-bs-parent="#accordionExample">
                                                <div class="form-floating p-3">
                                                    <textarea class="ckeditor" name="editor-tdp"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="my-3 content d-none" id="object-content">
                                <div class="form-floating">
                                    <div>Đối tượng</div>
                                    <textarea class="ckeditor" name="editor-dt"></textarea>
                                </div>
                            </div>
                            <div class="my-3 content d-none" id="schedule-content">
                                <div class="form-floating">
                                    <div>Phác đồ tiêm</div>
                                    <textarea class="ckeditor" name="editor-pdt"></textarea>
                                </div>
                            </div>
                            <div class="my-3 content d-none" id="reaction-content">
                                <div class="form-floating">
                                    <div>Phản ứng</div>
                                    <textarea class="ckeditor" name="editor-pu"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="">
                        <div class="vaccine-info-form">
                            <div class="section-title border-bottom p-3">Phân loại</div>
                            <div class="p-3">
                                <a href="" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addDisase">
                                    <i class="fa-solid fa-plus"></i> Thêm nhóm bệnh
                                </a>
                                <a href="" class="btn btn-add btn-sm" data-bs-toggle="modal" data-bs-target="#addAge">
                                    <i class="fa-solid fa-plus"></i> Thêm độ tuổi
                                </a>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="mb-3">
                                            <div class="mb-3">
                                                <label for="form-select-vaccine1" class="form-label">Vắc xin theo nhóm bệnh</label>
                                                <select class="form-select form-control" id="form-select-vaccine1" name="disage-name">
                                                    <option value="0">---Chọn vắc xin---</option>
                                                    <c:forEach var="d" items="${disases}">
                                                        <option value="${d.id}">${d.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="mb-3">
                                            <label for="form-select-vaccine2" class="form-label">Vắc xin theo độ tuổi</label>
                                            <select class="form-select form-control" id="form-select-vaccine2" name="age-name">
                                                <option value="0">---Chọn vắc xin---</option>
                                                <c:forEach var="a" items="${ages}">
                                                    <option value="${a.id}">${a.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-save">Lưu lại</button>
                    <a href="table-data-vacxin">
                        <button type="button" class="btn btn-return">Quay lại</button>
                    </a>
                </div>
            </div>
        </form>
    </div>

    <%-- Modal them nhom benh --%>
    <div class="modal fade" id="addDisase" data-bs-backdrop="static" data-bs-keyboard="false"
         tabindex="-1" aria-labelledby="addDisaseLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addDisaseLabel">Thêm nhóm bệnh</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="addDisase" method="post">
                        <div class="mb-3">
                            <label for="disase" class="form-label">Nhập tên nhóm bệnh</label>
                            <input type="text" class="form-control" id="disase" name="name" maxlength="80" required>
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

    <%-- Modal them do tuoi --%>
    <div class="modal fade" id="addAge" data-bs-backdrop="static" data-bs-keyboard="false"
         tabindex="-1" aria-labelledby="addAgeLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addAgeLabel">Thêm độ tuổi</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="addAge" method="post">
                        <div class="mb-3">
                            <label for="age" class="form-label">Nhập tên độ tuổi</label>
                            <input type="text" class="form-control" id="age" name="name" maxlength="80" required>
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
<script src="../js/form-add-vacxin.js"></script>
</html>

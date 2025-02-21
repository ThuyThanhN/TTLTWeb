<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lịch hẹn tiêm vắc xin</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <script src="https://kit.fontawesome.com/33ad855007.js" crossorigin="anonymous"></script>
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <!-- Css -->
    <link rel="stylesheet" href="css/dosing_schedule.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content -->
    <div id="section-content">
        <div class="container">
            <form action="dosing_schedule" method="post" id="myForm">
                <input type="hidden" name="cartId" value="${cartId}">
                <div class="row">
                    <div class="col-lg-9">
                        <h4 class="text-center section-content-title">Đặt lịch hẹn</h4>
                        <div class="row">
                            <div class="col-12 section-col-title">Thông tin người tiêm</div>
                            <!-- Họ tên người tiêm -->
                            <div class="col-12 col-md-6">
                                <label for="fullname">Họ tên người tiêm <span class="asterisk">(*)</span></label> <br>
                                <input type="text" class="form-control required-field" id="fullname"
                                       name="patient-name" value="${patient.fullname}">
                                <div class="hidden-field error-message" id="fullname-error">Vui lòng chọn/điền</div>
                            </div>
                            <!-- Ngày sinh người tiêm -->
                            <div class="col-12 col-md-6">
                                <label for="date">Ngày sinh người tiêm <span class="asterisk">(*)</span></label> <br>
                                <input type="date" class="form-control required-field" name="date" id="date"
                                       value="${patient.dateOfBirth}">
                                <div class="hidden-field error-message" id="date-error">Vui lòng chọn/điền</div>
                            </div>
                            <!-- Giới tính -->
                            <div class="col-12 col-md-6">
                                <div class="form-section-label">Giới tính <span class="asterisk">(*)</span></div>
                                <div class="btn-group w-100" role="group" aria-label="Gender selection">
                                    <input type="radio" class="btn-check" value="Nam" name="gender" id="male"
                                           autocomplete="off" <c:if test="${patient.gender == 'Nam'}">checked</c:if>>
                                    <label class="btn" for="male">Nam</label>

                                    <input type="radio" class="btn-check" value="Nữ" name="gender" id="female"
                                           autocomplete="off" <c:if test="${patient.gender == 'Nữ'}">checked</c:if>>
                                    <label class="btn" for="female">Nữ</label>
                                </div>
                                <div class="hidden-field error-gender">Vui lòng chọn/điền</div>
                            </div>
                            <!-- CCCD -->
                            <div class="col-12 col-md-6">
                                <label for="identifier">Mã định danh (hoặc CCCD) </label> <br>
                                <input type="text" class="form-control required-field" id="identifier"
                                       name="identifier" value="${patient.identification}">
                                <div class="hidden-field error-message" id="identifier-error">Vui lòng chọn/điền</div>
                            </div>
                            <!-- Chọn Tỉnh -->
                            <div class="col-12 col-md-4">
                                <div class="dropdown">
                                    <label for="province">Tỉnh thành</label> <br>
                                    <input type="text" placeholder="Chọn" id="province" name="province"
                                           class="form-control dropdown-toggle province-select required-field"
                                           data-bs-toggle="dropdown" aria-expanded="false" value="${patient.province}">
                                    <i class="fa-solid fa-angle-down"></i>
                                    <ul class="dropdown-menu province-menu"></ul>
                                </div>
                                <div class="hidden-field error-message" id="province-error">Vui lòng chọn/điền</div>
                            </div>
                            <!-- Chọn Quận -->
                            <div class="col-12 col-md-4">
                                <div class="dropdown">
                                    <label for="district">Quận huyện</label> <br>
                                    <input type="text" placeholder="Chọn tỉnh thành trước" id="district" name="district"
                                           class="form-control dropdown-toggle district-select"
                                           value="${patient.district}">
                                    <i class="fa-solid fa-angle-down"></i>
                                    <ul class="dropdown-menu district-menu"></ul>
                                </div>
                            </div>
                            <!-- Chọn Phường -->
                            <div class="col-12 col-md-4">
                                <div class="dropdown">
                                    <label for="ward">Phường xã</label> <br>
                                    <input type="text" placeholder="Chọn quận huyện trước" id="ward" name="ward"
                                           class="form-control dropdown-toggle ward-select" value="${patient.ward}">
                                    <i class="fa-solid fa-angle-down"></i>
                                    <ul class="dropdown-menu ward-menu"></ul>
                                </div>
                            </div>
                            <!-- Số nhà, tên đường -->
                            <div class="col-12">
                                <label for="address">Số nhà, tên đường</label> <br>
                                <input type="text" class="form-control" id="address" name="address"
                                       value="${patient.address}">
                            </div>
                            <div class="col-12 section-col-title">Thông tin liên hệ</div>
                            <!-- Họ tên người liên hệ -->
                            <div class="col-12">
                                <label for="contact_name">Họ tên người liên hệ <span class="asterisk">(*)</span></label>
                                <br>
                                <input type="text" class="form-control required-field" id="contact_name"
                                       name="contact_name" value="${contact.fullname}">
                                <div class="hidden-field error-message" id="contact_name-error">Vui lòng chọn/điền</div>
                            </div>
                            <!-- Mối quan hệ -->
                            <div class="col-12 col-md-6">
                                <label for="relationship">Mối quan hệ với người tiêm <span
                                        class="asterisk">(*)</span></label> <br>
                                <select class="form-select my-1 required-field" id="relationship" name="relationship">
                                    <option value="" ${contact.relationship == null ? "selected" : ""}>Chọn</option>
                                    <option value="Bản thân" ${"Bản thân".equals(contact.relationship) ? "selected" : ""}>
                                        Bản thân
                                    </option>
                                    <option value="Con" ${"Con".equals(contact.relationship) ? "selected" : ""}>Con
                                    </option>
                                    <option value="Cha" ${"Cha".equals(contact.relationship) ? "selected" : ""}>Cha
                                    </option>
                                    <option value="Mẹ" ${"Mẹ".equals(contact.relationship) ? "selected" : ""}>Mẹ
                                    </option>
                                    <option value="Vợ" ${"Vợ".equals(contact.relationship) ? "selected" : ""}>Vợ
                                    </option>
                                    <option value="Chồng" ${"Chồng".equals(contact.relationship) ? "selected" : ""}>
                                        Chồng
                                    </option>
                                    <option value="Anh" ${"Anh".equals(contact.relationship) ? "selected" : ""}>Anh
                                    </option>
                                    <option value="Chị" ${"Chị".equals(contact.relationship) ? "selected" : ""}>Chị
                                    </option>
                                    <option value="Em" ${"Em".equals(contact.relationship) ? "selected" : ""}>Em
                                    </option>
                                    <option value="Ông" ${"Ông".equals(contact.relationship) ? "selected" : ""}>Ông
                                    </option>
                                    <option value="Bà" ${"Bà".equals(contact.relationship) ? "selected" : ""}>Bà
                                    </option>
                                    <option value="Họ hàng" ${"Họ hàng".equals(contact.relationship) ? "selected" : ""}>
                                        Họ hàng
                                    </option>
                                    <option value="Khác" ${"Khác".equals(contact.relationship) ? "selected" : ""}>Khác
                                    </option>
                                </select>
                                <div class="hidden-field error-message" id="relationship-error">Vui lòng chọn/điền</div>
                            </div>
                            <!-- Sđt -->
                            <div class="col-12 col-md-6">
                                <label for="contact_phone">Số điện thoại người liên hệ <span class="asterisk">(*)</span></label>
                                <br>
                                <input type="tel" class="form-control required-field" id="contact_phone"
                                       name="contact_phone" value="${contact.phone}">
                                <div class="hidden-field error-message" id="contact_phone-error">Vui lòng chọn/điền
                                </div>
                                <div class="hidden-field error-phone">Số điện thoại không hợp lệ. Số điện thoại phải là
                                    10 chữ số.
                                </div>
                            </div>

                            <div class="col-12 section-col-title">Thông tin dịch vụ</div>
                            <!-- Loại vắc xin -->
                            <div class="col-12">
                                <div class="form-section-label">Loại vắc xin muốn đặt hẹn <span
                                        class="asterisk">(*)</span></div>
                                <div class="btn-group" role="group" aria-label="Vaccine type selection">
                                    <input type="radio" class="btn-check vaccine-type-btn" name="vaccineType"
                                           id="package-vaccine" value="package-vaccine">
                                    <label class="btn <c:if test="${cartId > 0 && isPackageVaccine}">selected</c:if>"
                                           for="package-vaccine">Vắc xin gói</label>

                                    <input type="radio" class="btn-check vaccine-type-btn" name="vaccineType"
                                           id="single-vaccine" value="single-vaccine">
                                    <label class="btn <c:if test="${cartId  > 0 && isSingleVaccine}">selected</c:if>"
                                           for="single-vaccine">Vắc xin lẻ</label>
                                </div>
                                <c:set var="orderDetailsCart" value="${requestScope.orderDetailsCart}"/>
                                <div>
                                    <!-- Loại vắc xin gói -->
                                    <div class="<c:if test="${!(cartId != null && cartId >0 && isPackageVaccine)}">hidden-vx</c:if> vaccine-type-content">
                                        <div style="color: #4d63a6; font-weight: 500">Chọn vắc xin</div>
                                        <!--Vắc xin cho trẻ em / 0-9 Tháng-->

                                        <c:forEach var="a" items="${ages}">
                                            <div class="vaccine-type-content-item">
                                                <img src="image/down-arrow.png" alt="Icon down-arrow">
                                                <span>${a.name}</span> <!-- Hiển thị tên nhóm độ tuổi -->
                                            </div>

                                            <!-- Hiển thị các gói vắc xin trong nhóm tuổi -->
                                            <div class="hidden-vx vaccine-package-list">
                                                <div class="row">
                                                    <c:forEach var="vp" items="${a.packages}">
                                                        <div class="col-12 col-sm-6 col-lg-4">
                                                            <div class="vaccine-package">
                                                                <div class="form-check">
                                                                    <label class="form-check-label"
                                                                           for="package-${vp.id}">
                                                                        <div class="row">
                                                                            <div class="col-6">
                                                                                <input class="form-check-input"
                                                                                       type="checkbox"
                                                                                       id="package-${vp.id}"
                                                                                       name="selectedPackages"
                                                                                       value="${vp.id}"
                                                                                <c:if test="${not empty orderDetailsCart && not empty cartId}">
                                                                                <c:forEach var="od"
                                                                                           items="${orderDetailsCart}">
                                                                                <c:if test="${od.idPackage == vp.id}">
                                                                                       checked
                                                                                </c:if>
                                                                                </c:forEach>
                                                                                </c:if>>
                                                                                    ${vp.name}
                                                                            </div>
                                                                            <div class="col-6 vaccine-price">
                                                                                <f:formatNumber value="${vp.totalPrice}" type="number" pattern="#,##0" />đ
                                                                            </div>
                                                                            <div class="col-12 vaccine-description">
                                                                                    ${vp.description}
                                                                            </div>
                                                                        </div>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <!-- Loại vắc xin lẻ -->
                                    <div class="<c:if test="${!(cartId != null && cartId >0 && isSingleVaccine)}">hidden-vx</c:if> vaccine-type-content">
                                        <div style="color: #4d63a6; font-weight: 500">Chọn vắc xin</div>
                                        <div class="row">
                                            <c:forEach var="v" items="${vaccines}">
                                                <div class="col-12 col-sm-6 col-lg-4">
                                                    <div class="vaccine-package">
                                                        <div class="form-check">
                                                            <label class="form-check-label" for="vaccine-${v.id}">
                                                                <div class="row">
                                                                    <div class="col-6">
                                                                        <input class="form-check-input" type="checkbox"
                                                                               id="vaccine-${v.id}"
                                                                               name="selectedVaccines" value="${v.id}"
                                                                        <c:if test="${not empty orderDetailsCart && not empty cartId}">
                                                                        <c:forEach var="od" items="${orderDetailsCart}">
                                                                        <c:if test="${od.idVaccine == v.id}">
                                                                               checked
                                                                        </c:if>
                                                                        </c:forEach>
                                                                        </c:if>
                                                                        >
                                                                            ${v.name}
                                                                    </div>
                                                                    <div class="col-6 vaccine-price">
                                                                        <td><f:formatNumber value="${v.price}" type="number" pattern="#,##0" />đ</td>
                                                                    </div>
                                                                    <div class="col-12 vaccine-description">
                                                                            ${v.prevention}
                                                                    </div>
                                                                </div>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="hidden-field error-vaccine">Vui lòng chọn/điều</div>
                            </div>
                            <!-- Trung tâm -->
                            <div class="col-12">
                                <div class="mb-3">
                                    <div class="dropdown">
                                        <label for="center">Trung tâm mong muốn tiêm <span
                                                class="asterisk">(*)</span></label> <br>
                                        <select class="form-select required-field" id="center" name="center-select">
                                            <option value="">Chọn trung tâm</option>
                                            <c:forEach var="c" items="${centers}">
                                                <option value="${c.id}"
                                                        <c:if test="${c.id == order.idCenter}">selected</c:if>>
                                                        ${c.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <i class="fa-solid fa-angle-down"></i>
                                    </div>
                                    <div class="hidden-field error-message" id="center-error">Vui lòng chọn/điền</div>
                                </div>
                            </div>
                            <!-- Ngày tiêm -->
                            <div class="col-12 col-md-4">
                                <label for="preferred_date">Ngày mong muốn tiêm <span
                                        class="asterisk">(*)</span></label> <br>
                                <input type="date" class="form-control required-field" name="preferred_date"
                                       id="preferred_date" value="${order.appointmentDate}">
                                <div class="hidden-field error-message" id="preferred_date-error">Vui lòng chọn/điền
                                </div>
                            </div>
                            <!-- Giờ tiêm -->
                            <div class="col-12 col-md-3">
                                <label>Giờ tiêm <span class="asterisk">(*)</span></label> <br>
                                <button type="button" class="btn w-100" id="timeButton" data-bs-toggle="modal"
                                        data-bs-target="#timeModal">
                                    Chọn khung giờ
                                </button>
                                <input type="hidden" id="vaccination_time" name="vaccination_time"
                                       value="${order.appointmentTime}">
                                <div class="modal fade" id="timeModal" tabindex="-1" aria-labelledby="timeModalLabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="time-option">7:30 - 8:00</div>
                                                <div class="time-option">8:00 - 9:00</div>
                                                <div class="time-option">9:00 - 10:00</div>
                                                <div class="time-option">10:00 - 11:00</div>
                                                <div class="time-option">11:00 - 12:00</div>
                                                <div class="time-option">12:00 - 13:00</div>
                                                <div class="time-option">13:00 - 14:00</div>
                                                <div class="time-option">14:00 - 15:00</div>
                                                <div class="time-option">15:00 - 16:00</div>
                                                <div class="time-option">16:00 - 17:00</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="hidden-field error-message" id="vaccination_time-error">Vui lòng chọn/điền
                                </div>
                            </div>
                            <!-- Cam kết -->
                            <div class="col-12 mt-2 d-flex gap-2">
                                <input class="form-check-input" type="checkbox" id="commitment">
                                <label class="form-check-label mt-0" for="commitment">
                                    Cam kết thông tin khai báo là chính xác và đầy đủ
                                </label>
                            </div>
                            <div class="col-12 mt-2 text-center">
                                <input class="btn btn-primary" id="btn-submit" type="submit" value="Hoàn thành đăng ký">
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <img src="image/dv3-1-1.jpg" class="w-100" alt="">
                        <img src="image/dv4-1-1.jpg" class="w-100" alt="">

                        <div class="cskh">
                            <div class="cskh-title">
                                <a href="#">CHĂM SÓC KHÁCH HÀNG</a>
                            </div>
                            <p class="cskh-info"><i
                                    class="fa-solid fa-phone-volume shake-icon"></i><span> 023 2343 8445</span></p>
                            <p class="cskh-info last"><i class="fa-solid fa-envelope"></i><span> cskh@ttt.vn</span></p>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/dosing_schedule.js"></script>
<script src="js/api_address.js"></script>


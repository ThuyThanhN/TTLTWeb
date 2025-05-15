<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Phiếu hẹn tiêm chủng</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
    <!--    font awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <!--    bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/appointment-slip.css">
</head>
<body>
<div id="wrapper">
    <!-- Phần header -->
    <jsp:include page="header.jsp"></jsp:include>
    <div class="appointmentSlip-content">
        <div class="appointmentSlip-info">
            <h4><strong>Mã phiếu hẹn: </strong>${order.id != null ? order.id : "Chưa có thông tin"}</h4>
            <p><strong>Loại vắc xin: </strong>
                <c:if test="${not empty vaccines}">
                    <!-- Lặp qua tất cả các vắc xin trong danh sách -->
                    <c:forEach var="vaccine" items="${vaccines}">
                        ${vaccine.name != null ? vaccine.name : "Chưa có thông tin"} <br>
                    </c:forEach>
                </c:if>

                <!-- Hiển thị tên gói vắc xin nếu có -->
                <c:if test="${not empty vaccinePackageName}">
                    <strong>Gói vắc xin: </strong>${vaccinePackageName} <br>
                </c:if>

                <!-- Nếu không có vắc xin -->
                <c:if test="${empty vaccines}">
                </c:if>
            </p>
            <p><strong>Ngày tiêm: </strong>
                <c:choose>
                    <c:when test="${not empty order.appointmentDate}">
                        <fmt:formatDate value="${order.appointmentDate}" pattern="dd-MM-yyyy" />
                    </c:when>
                    <c:otherwise>Chưa có thông tin</c:otherwise>
                </c:choose>
            </p>
            <p><strong>Giờ tiêm: </strong>${order.appointmentTime != null ? order.appointmentTime : "Chưa có thông tin"}
            </p>
        </div>
        <div class="appointmentSlip-details">
            <h4> ${center.name != null ? center.name : "Chưa có thông tin"}</h4>
            <p>Địa Chỉ: ${center.address != null ? center.address : "Chưa có thông tin"}</p>
            <form action="payment" method="post">
                <div class="info-row">
                    <span class="order-info-label">Họ và tên người tiêm:</span>
                    <span class="value">${patient.fullname != null ? patient.fullname : "Chưa có thông tin"}</span>
                </div>
                <div class="info-row">
                    <span class="order-info-label">Ngày sinh:</span>
                    <span class="value">
                        <c:choose>
                            <c:when test="${not empty patient.dateOfBirth}">
                                <fmt:formatDate value="${patient.dateOfBirth}" pattern="dd-MM-yyyy"/>
                            </c:when>
                            <c:otherwise>Chưa có thông tin</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="info-row">
                    <span class="order-info-label">Họ và tên người liên hệ:</span>
                    <span class="value">${contactFullname != null ? contactFullname : "Chưa có thông tin"}</span>
                </div>
                <div class="info-row">
                    <span class="order-info-label">Mối quan hệ với người tiêm:</span>
                    <span class="value">${contactRelationship != null ? contactRelationship : "Chưa có thông tin"}</span>
                </div>
                <div class="info-row">
                    <span class="order-info-label">Mã định danh:</span>
                    <span class="value">${patient.identification != null ? patient.identification : "Chưa có thông tin"}</span>
                </div>
                <div class="info-row">
                    <span class="order-info-label">Số điện thoại:</span>
                    <span class="value">${contactPhone != null ? contactPhone : "Chưa có thông tin"}</span>
                </div>
                <p class="note">Vui lòng chụp lại thông tin phiếu hẹn hoặc ghi nhớ mã phiếu!</p>
                <input type="hidden" name="id" value="${order.id != null ? order.id : ''}"/>
                <c:if test="${order.paymentStatus == 'Chưa thanh toán'}">
                    <!-- Hiển thị nút thanh toán khi trạng thái thanh toán là "Chưa thanh toán" -->
                    <button type="submit" class="btn btn-order noPaid"> Thanh toán online</button>
                </c:if>

                <c:if test="${order.paymentStatus != 'Chưa thanh toán'}">
                    <!-- Nếu trạng thái thanh toán không phải là "Chưa thanh toán", không hiển thị nút -->
                    <button disabled class="btn btn-order Paid">Đơn hàng đã được thanh toán</button>
                </c:if>

            </form>
        </div>
    </div>

    <!-- Phần footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/show_navbarNav.js"></script>
</html>

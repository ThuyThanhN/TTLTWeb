<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch hẹn tiêm vắc xin</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="css/information.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content   -->
    <div class="main">
        <div class="container">
            <!-- Sidebar -->
            <jsp:include page="user_sidebar.jsp"></jsp:include>

            <!-- Main Content -->
            <div style="width: 60%">
                <div class="appointment-grid">
                    <c:forEach var="o" items="${orders}">
                        <%--                        <div class="appointment-card">--%>
                        <%--                            <a href="appointment-slip?id=${o.order_id}">--%>
                        <%--                                <ul style="color: #2A388F; font-size: 14px; font-weight: 500; float: right; margin-bottom: 0">--%>
                        <%--                                    <li>${o.order_status}</li>--%>
                        <%--                                    <li>${o.paymentStatus}</li>--%>
                        <%--                                </ul>--%>
                        <%--                                <img src="image/online-appointment1.png" alt="Hình ảnh phiếu hẹn">--%>
                        <%--                                <p>Mã phiếu: <b>${o.order_id}</b></p>--%>
                        <%--                                <p>Giá:<b><f:formatNumber value="${o.total_price}" type="number" pattern="#,##0"/>đ</b>--%>
                        <%--                                </p>--%>
                        <%--                                <p>Ngày hẹn: <b><f:formatDate value="${o.appointment_date}" pattern="dd-MM-yyyy"/></b>--%>
                        <%--                                </p>--%>
                        <%--                            </a>--%>
                        <%--                        </div>--%>

                        <div class="appointment-card mb-4">
                            <div class="d-flex justify-content-between border-bottom pb-2">
                                <div class="d-flex order_id">
                                    <span><b>Mã phiếu:</b> ${o.order_id}</span>
                                    <span class="dot-before appointment-date">
                                            <b>Ngày hẹn: <f:formatDate value="${o.appointment_date}"
                                                                       pattern="dd-MM-yyyy"/></b>
                                        </span>
                                </div>
                                <div class="status ${o.order_status eq 'Chưa được duyệt' ? 'status-pending' : 'status-approved'}">
                                        ${o.order_status}
                                </div>

                            </div>
                            <div class="d-flex justify-content-between mt-2">
                                <div>
                                    <img src="image/report.png" alt="Hình ảnh phiếu hẹn">
                                </div>
                                <div>
                                    <div style="color: #333; font-size: 14px">Tổng tiền: <span
                                            class="price"><f:formatNumber value="${o.total_price}" type="number"
                                                                          pattern="#,##0"/>đ</span></div>

                                    <div class="text-end mt-1 status ${o.paymentStatus eq 'Chưa thanh toán' ? 'status-pending' : 'status-approved'}">
                                        <img src="image/${o.paymentStatus eq 'Chưa thanh toán' ? 'remove' : 'checked'}.png"
                                             alt="${o.paymentStatus}"
                                             style="width: 16px; height: 16px; margin: 1px 3px 0 0">
                                            ${o.paymentStatus}
                                    </div>
                                    <div class="d-flex mt-5">
                                        <a href="appointment-slip?id=${o.order_id}" class="btn btn-detail ms-auto">Xem chi tiết</a>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>

</div>
</body>
<script src="js/show_navbarNav.js"></script>
</html>
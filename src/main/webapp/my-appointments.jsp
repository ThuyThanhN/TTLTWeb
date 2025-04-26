<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách lịch hẹn</title>
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
            <div class="sidebar">
                <div class="profile-card">
                    <img src="image/avatar1.png" alt="Avatar" class="profile-image">
                    <h2 id="sidebar-username" class="sidebar-username">Anh Tiến</h2>
                    <p>0703035425</p>
                </div>
                <ul class="menu">
                    <li><a href="information.jsp"><i class="fa-regular fa-user"></i> Thông tin cá
                        nhân</a></li>
                    <li><a href="my-appointments" class="active"><i class="fa-solid fa-calendar-check"></i> Lịch hẹn tiêm vắc xin</a>
                    </li>
                    <li><a href="changePassword"><i class="fa-solid fa-key"></i> Đổi mật khẩu</a></li>
                    <li><a href="#" data-bs-toggle="modal" data-bs-target="#lockAccountModal"><i class="fas fa-user-lock"></i> Khóa tài khoản</a></li>
                    <li><a href="logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>

                <!-- Modal lock account -->
                <div class="modal fade" id="lockAccountModal" tabindex="-1" aria-labelledby="lockAccountModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form action="lockAccount" method="post">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="lockAccountModalLabel">Thông Báo Khóa Tài Khoản
                                    </h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Đóng"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="text-center">Tài khoản của bạn sẽ bị khóa. <br> Bạn có chắc chắn muốn tiếp
                                        tục?
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                    <button type="submit" class="btn btn-danger">Xác Nhận</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="main-content">
                <h1 class="text-center">Danh sách lịch hẹn</h1>
                <div class="appointment-grid">
                    <c:forEach var="o" items="${orders}">
                        <div class="appointment-card">
                            <a href="appointment-slip?id=${o.order_id}">
                                <ul style="color: #2A388F; font-size: 14px; font-weight: 500; float: right; margin-bottom: 0">
                                    <li>${o.order_status}</li>
                                    <li>${o.paymentStatus}</li>
                                </ul>
                                <img src="image/online-appointment1.png" alt="Hình ảnh phiếu hẹn">
                                <p>Mã phiếu: <b>${o.order_id}</b></p>
                                <p>Giá:<b><f:formatNumber value="${o.total_price}" type="number" pattern="#,##0"/>đ</b>
                                </p>
                                <p>Ngày hẹn: <b><f:formatDate value="${o.appointment_date}" pattern="dd-MM-yyyy"/></b>
                                </p>
                            </a>
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
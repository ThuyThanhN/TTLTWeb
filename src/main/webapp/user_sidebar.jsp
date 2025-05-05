<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="sidebar">
    <div class="profile-card">
        <img src="image/avatar1.png" alt="Avatar" class="profile-image">
        <h2 id="sidebar-username" class="sidebar-username">Anh Tiến</h2>
        <p>0703035425</p>
    </div>
    <ul class="menu">
        <li>
            <a href="information.jsp" class="<%= request.getRequestURI().contains("information.jsp") ? "active" : "" %>"><i class="fa-regular fa-user"></i> Thông tin cá nhân</a>
        </li>
        <li>
            <a href="my-appointments" class="<%= request.getRequestURI().contains("my-appointments") ? "active" : "" %>"><i class="fa-solid fa-calendar-check"></i> Lịch hẹn tiêm vắc xin</a>
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
</body>
</html>

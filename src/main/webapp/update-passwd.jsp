<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cập Nhật Mật Khẩu</title>
    <!--    font awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!--    bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <!--    font chữ-->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/change-password.css">
    <!--    css của footer -->
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<div id="wrapper">
    <div class="header text-center">
        <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
    </div>
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-info text-center">
        <%= request.getAttribute("message") %>
    </div>
    <% } %>
    <div class="change-container">
        <form class="change-form" id="changePasswordForm" action="updatePasswd" method="post">
            <h2>Đổi mật khẩu</h2>

            <div class="input-group password-group">
                <label for="newPassword">Mật khẩu mới</label>
                <input type="password" id="newPassword" name="newPassword" required>
                <span class="toggle-password">
          <i class="fa-solid fa-eye" id="toggleNewPassword"></i>
        </span>
            </div>
            <div class="input-group password-group">
                <label for="confirmNewPassword">Xác nhận mật khẩu mới</label>
                <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                <span class="toggle-password">
          <i class="fa-solid fa-eye" id="toggleConfirmNewPassword"></i>
        </span>
            </div>
            <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
        </form>
    </div>
    <div class="footer">
        <div class="container-lg">
            <div class="row">
                <div class="col-12 col-lg-3">
                    <a href="index">
                        <img src="image/logo.png" alt="Logo" class="footer-logo">
                    </a>
                    <div id="copy-right">
                        <div class="content">TRƯỜNG ĐẠI HỌC NÔNG LÂM</div>
                    </div>
                    <div class="single-contact">
                        <span class="content ">Khoa Công Nghệ Thông Tin</span>
                    </div>
                    <div class="single-contact">
                        <span><i class="fa fa-map-marker-alt"></i></span>
                        <span class="content">Địa chỉ: <span>Khu Phố 6, Đường số 6, Linh Trung, TP.Thủ Đức, TP.Hồ Chí Minh</span></span>
                    </div>
                    <div class="single-contact">
                        <i class="fa-solid fa-phone-volume shake-icon"></i>
                        <div class="content">Tư vấn ngay: 023 2343 8445</div>
                    </div>
                    <div class="single-contact">
                        <i class="fa-solid fa-envelope"></i>
                        <div class="content">Email: <span>cskh@ttt.vn</span></div>
                    </div>
                </div>
                <div class="col-12 col-lg-3">
                    <div class="footer-title">Chính sách</div>
                    <ul class="list-menu">
                        <li class="li-menu"><a href="">Chính sách bảo mật</a></li>
                        <li class="li-menu"><a href="">Khảo sát tiêm chủng</a></li>
                        <li class="li-menu"><a href="">Chính sách tiêm chủng</a></li>
                    </ul>
                </div>
                <div class="col-12 col-lg-3">
                    <div class="footer-title">Hỗ trợ khách hàng</div>
                    <ul class="list-menu">
                        <li class="li-menu"><a href="vaccine-information.html">Tìm kiếm</a></li>
                        <li class="li-menu"><a href="login.html">Đăng nhập</a></li>
                        <li class="li-menu"><a href="register.html">Đăng ký</a></li>
                        <li class="li-menu"><a href="dosing_schedule.html">Đặt lịch tiêm chủng</a></li>
                    </ul>
                </div>
                <div class="col-12 col-lg-3">
                    <div class="footer-title">Đăng ký nhận tin</div>
                    <p class="text-white pt-2 newsletter">Bạn muốn nhận thông báo về tiêm chủng? Đăng ký ngay.</p>

                    <div class="email">
                        <input type="text" class="form-control input-group-field" placeholder="Nhập địa chỉ email">
                        <button class="btn text-white button-subscribe">Đăng ký</button>
                    </div>

                    <div class="d-flex flex-wrap gap-2 mt-3">
                        <div class="social-media">
                            <a href=""><img src="image/facebook.png" alt="Facebook"></a>
                        </div>
                        <div class="social-media">
                            <a href=""><img src="image/youtube.png" alt="Facebook"></a>
                        </div>
                        <div>
                            <a href="">
                                <img style="max-width: 150px; height:28px" src="image/dathongbao.png" alt="">
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/change-password.js"></script>
</body>
</html>

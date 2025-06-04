<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <!--    font awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <script src="https://kit.fontawesome.com/4760c40adb.js" crossorigin="anonymous"></script>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <script src="https://kit.fontawesome.com/4760c40adb.js" crossorigin="anonymous"></script>

    <!-- Bootstrap CSS (Sửa lại, chỉ giữ 1 liên kết) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          crossorigin="anonymous">
    <!-- Bootstrap JS (Sửa lại, chỉ giữ 1 liên kết) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    <!--    font chữ-->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
<div id="wrapper">
    <!--    Phần header -->
    <div class="header text-center">
        <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
    </div>
    <!--    Phần content -->
    <div class="login-container">
        <div class="login-form">

            <form method="POST" action="login" id="login-form">
                <h5>Đăng Nhập</h5>

                <!-- Thông báo lỗi từ server -->
                <div class="alert alert-danger" id="error-message" style="display:none;"></div>

                <div class="input-group">
                    <label for="username">Tài khoản đăng nhập</label>
                    <input type="text" id="username" name="username" required autocomplete="off"
                           placeholder="Nhập số điện thoại hoặc email">
                </div>
                <div class="input-group password-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" class="password" name="password" required autocomplete="off"
                           placeholder="Nhập mật khẩu">
                    <i class="fa-solid fa-eye eye-icon" id="togglePassword"></i>
                </div>

                <!-- Tùy chọn đăng nhập -->
                <div class="login-options">
                    <div class="forgot-password">
                        <a href="reset-password" class="link-primary">Quên mật khẩu?</a>
                    </div>
                    <div class="register-link">
                        <a href="registerUsers" class="link-primary">Đăng ký</a>
                    </div>
                </div>

                <!-- Nút đăng nhập -->
                <div class="g-recaptcha" data-sitekey="6Leh7VIrAAAAALqefOlWqNkWP7As8Bg3Zw14A7m0"></div>
                <button type="submit" id="login-button" class="btn btn-primary w-100">Đăng nhập</button>
            </form>

            <!-- Modal thông báo lỗi -->
            <div class="modal hidden" id="error-modal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Thông báo</h5>
                            <!-- Nút đóng modal -->
                            <button type="button" class="btn-close" id="close-modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p id="modal-message">Tài khoản hoặc mật khẩu không chính xác.</p>
                        </div>
                        <div class="modal-footer">
                            <!-- Nút OK đóng modal -->
                            <button type="button" class="btn btn-primary" id="ok-modal">OK</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="mt-2 text-center mt-3" style="color: #9999A4; font-size: 14px; font-weight: 500">Hoặc Đăng nhập với tài khoản mạng xã hội</div>
            <div class=" authentication-group">
                <a class="authentication-button google"
                   href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=https://vaccine.io.vn/login?provider=google&response_type=code&client_id=737894268831-9ab6vfskfnv7pfoh1i7nnagpmmau67oi.apps.googleusercontent.com&approval_prompt=force">
                    <img src="image/google.png">
                </a>
                <!-- Nút đăng nhập Facebook -->
                <button type="button" onclick="fbLogin()" class="authentication-button facebook mt-0" style="background:#4267B2; border:none;">
                    <img src="image/facebook.png">
                </button>

                <!-- Form ẩn để gửi data Facebook user lên servlet -->
                <form id="fbLoginForm" action="facebookLogin" method="POST" style="display:none;">
                    <input type="hidden" name="fbUserId" id="fbUserId" />
                    <input type="hidden" name="fbUserName" id="fbUserName" />
                    <input type="hidden" name="fbUserEmail" id="fbUserEmail" />
                </form>

<%--                <a class="authentication-button facebook"--%>
<%--                   href="https://www.facebook.com/v22.0/dialog/oauth?fields=id,name,email&client_id=1227448322128839&redirect_uri=https://vaccine.io.vn/login?provider=facebook&scope=email">--%>
<%--                    <img src="image/facebook.png">--%>
<%--                </a>--%>
            </div>
        </div>
    </div>

</div>
<!--    Phần footer -->

<jsp:include page="footer.jsp"></jsp:include>
</div>
<script src="js/login.js"></script>
<script src="js/login-facebook.js"></script>

</body>
</html>


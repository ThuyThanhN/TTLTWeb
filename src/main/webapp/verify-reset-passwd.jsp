<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lấy lại mật khẩu</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/verify-reset-passwd.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <div class="header text-center">
        <a href="index.html"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
    </div>
    <!-- Content -->
    <div class="main-content">
        <!-- Mũi tên quay lại -->
        <i class="fas fa-arrow-left back-arrow" onclick="window.location.href='reset-password';"></i>
        <h1 class="welcome-message">Xác thực mã OTP</h1>
        <form action="verify-reset-passwd" method="post">
            <div class="info-card">
                <div class="item-info">
                    <p style="margin-top: 15px; font-size: 14px; color: #333;">
                        <strong>Vui lòng nhập mã OTP:</strong>
                    </p>
                    <div class="input-container">
                        <input type="text" id="otp" name="otp" class="form-control" placeholder="Nhập mã OTP đã nhận được" required>
                        <button type="button" class="btn-resend" id="resend-otp">Lấy Mã</button>
                    </div>


                    <!-- Hiển thị thông báo khi người dùng nhập sai 3 lần -->
                    <p id="lock-message" style="color: red; font-size: 14px; margin-top: 5px; display: none;">
                        Bạn đã nhập sai 3 lần. Vui lòng đợi 1 phút để thử lại.
                    </p>

                    <!-- Thông báo lỗi từ server -->
                    <% if (request.getAttribute("error") != null) { %>
                    <p id="error-message" style="color: red; font-size: 14px; margin-top: 5px;">
                        <%= request.getAttribute("error") %>
                    </p>
                    <% } %>
                </div>
                <button type="submit" class="btn-edit">Xác nhận</button>
            </div>
        </form>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
<script>
    $(document).ready(function() {
        // Xử lý khi nhấn nút "Lấy mã"
        $('#resend-otp').click(function() {
            $.ajax({
                url: 'verify-reset-passwd',  // Chuyển hướng đến servlet để gửi lại mã OTP
                type: 'POST',
                data: { resendOtp: true },   // Cần thêm tham số này để nhận diện yêu cầu gửi lại OTP
                success: function(response) {
                    alert(response);  // Hiển thị thông báo khi gửi thành công
                },
                error: function() {
                    alert("Đã xảy ra lỗi khi gửi lại mã.");
                }
            });
        });
    });
</script>
<script src="js/verify-reset-passwd.js"></script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lấy Lại Mật Khẩu</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/reset-password.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <div class="header text-center">
        <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
    </div>
    <!-- Content -->
    <div class="main-content">
        <h1 class="welcome-message">Lấy lại mật khẩu</h1>
        <%-- Hiển thị thông báo lỗi nếu có --%>
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger text-center">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <%-- Hiển thị thông báo thành công nếu có --%>
        <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-success text-center">
            <%= request.getAttribute("message") %>
        </div>
        <% } %>
        <form action="reset-password" method="post">
            <div class="info-card">
                <div class="item-info">
                    <input type="email" id="email" name="email" class="form-control" placeholder="Nhập email khôi phục" required>
                    <p id="error-message" style="color: red; font-size: 14px; margin-top: 5px; display: none;"></p>
                </div>
                <p style="margin-top: 15px; font-size: 14px; color: #333;">
                    Chúng tôi sẽ gửi mã xác nhận đặt lại mật khẩu thông qua email.
                </p>
                <button type="submit" class="btn-edit">Gửi Mã</button>
            </div>

        </form>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>


<script src="js/reset-password.js"></script>
<script>
    // Kiểm tra định dạng email phía client
    document.querySelector("form").addEventListener("submit", function (e) {
        const emailField = document.getElementById("email");
        const errorMessage = document.getElementById("error-message");
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailRegex.test(emailField.value)) {
            e.preventDefault(); // Ngăn gửi form
            errorMessage.textContent = "Vui lòng nhập email hợp lệ.";
            errorMessage.style.display = "block";
        } else {
            errorMessage.style.display = "none";
        }
    });
</script>
</body>
</html>
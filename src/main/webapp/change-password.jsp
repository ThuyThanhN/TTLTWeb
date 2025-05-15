<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đổi mật khẩu</title>
  <link rel="icon" type="image/png" href="image/logo1.png">
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
    <form class="change-form" id="changePasswordForm" action="changePassword" method="post">
      <h5>ĐỔI MẬT KHẨU</h5>
      <div class="input-group password-group">
        <label for="currentPassword">Mật khẩu hiện tại</label>
        <div class="password-group">
          <input type="password" id="currentPassword" name="currentPassword" placeholder="Nhập mật khẩu hiện tại" required>
          <i class="fa-solid fa-eye eye-icon" id="toggleCurrentPassword"></i>
        </div>
      </div>
      <div class="input-group">
        <label for="newPassword">Mật khẩu mới</label>
        <div class="password-group">
          <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới" required>
          <i class="fa-solid fa-eye eye-icon" id="toggleNewPassword"></i>
        </div>
        <span class="error-message" id="newPassword-error">Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt.</span>
        <span class="error-message" id="samePassword-error">Mật khẩu mới phải khác mật khẩu hiện tại.</span>
      </div>
      <div class="input-group password-group">
        <label for="confirmNewPassword">Xác nhận mật khẩu mới</label>
        <div class="password-group">
          <input type="password" id="confirmNewPassword" name="confirmNewPassword" placeholder="Nhập mật khẩu mới" required>
          <i class="fa-solid fa-eye eye-icon" id="toggleConfirmNewPassword"></i>
        </div>
        <span class="error-message" id="confirmPassword-error">Xác nhận mật khẩu không khớp.</span>
      </div>
      <button type="submit" class="btn btn-primary" id="submitButton">LƯU THAY ĐỔI</button>
    </form>
  </div>

  <jsp:include page="footer.jsp"></jsp:include>
</div>

<script src="js/change-password.js"></script>
</body>
</html>

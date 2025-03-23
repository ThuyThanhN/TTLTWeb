<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng nhập</title>
  <!--    font awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
  <!--    bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <!--    font chữ-->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div id="wrapper">
  <!--    Phần header -->
  <div class="header text-center">
    <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
  </div>
  <!--    Phần content -->
  <div class="login-container">
    <form class="login-form" action="login" method="POST">
      <h2>Đăng Nhập</h2>
      <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
      </div>
      <% } %>
      <div class="input-group">
        <label for="username">SDT đăng nhập</label>
        <input type="text" id="username" name="username" required autocomplete="off" placeholder="Nhập số điện thoại">
      </div>
      <div class="input-group password-group">
        <label for="password">Mật khẩu</label>
        <input type="password" id="password" name="password" required autocomplete="off" placeholder="Nhập mật khẩu">
      </div>
      <div class="login-options">
        <div class="forgot-password">
          <a href="reset-password" class="link-primary">Quên mật khẩu?</a>        </div>
        <div class="register-link">
          <a href="registerUsers"class="link-primary">Đăng ký</a>
        </div>
      </div>
      <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
    </form>
  </div>
  <!--    Phần footer -->
  <jsp:include page="footer.jsp"></jsp:include>
</div>
<script src = "js/login.js"></script>
</body>
</html>


<%@ page import="com.example.provide_vaccine_services.dao.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="header-wrapper">
  <div class="header-title">
    <a href="index"><img src="image/logo.png" alt=""></a>
  </div>
  <div class="user-info">
    <%
      // Kiểm tra thông tin người dùng từ session
      Users user = (Users) session.getAttribute("user");
    %>
    <div class="position-relative btn-login">
      <% if (user != null) { %>
      <!-- Nếu đã đăng nhập, hiển thị tên người dùng -->
      <a href="#" class="d-flex text-decoration-none">
        <span style="margin: 5px 5px 0 0;">Xin chào, <%= user.getFullname() %>!</span>
        <span><img class="img-admin" src="image/hacker.png"> </span>
      </a>
      <% }  %>
    </div>
  </div>
</div>
</body>
</html>

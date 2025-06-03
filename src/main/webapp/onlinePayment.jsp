<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 4/9/2025
  Time: 4:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trang thanh toán</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
</head>
<body>
<h1>Thanh toán đơn hàng</h1>

<p>Tổng số tiền: <strong>${totalBill}</strong> VND</p>

<form action="payment" method="post">
    <input type="hidden" name="totalBill" value="${totalBill}">
    <button type="submit">Đặt Mua</button>
</form>

<p>Chúc bạn mua sắm vui vẻ!</p>
</body>
</html>


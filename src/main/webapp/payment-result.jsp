<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả giao dịch</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
    <link rel="stylesheet" href="css/payment-result.css">
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
</head>

<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content   -->
    <section class="transaction-result">


        <!-- Giao dịch thành công -->
        <c:if test="${transResult}">
            <div class="icon-container">
                <img src="image/sucess-payment.png"
                     alt="Transaction Status" class="transaction-icon">
            </div>
            <div class="result success">
                <h3>
                    Bạn đã giao dịch thành công!
                    <i class="fas fa-check-circle"></i>
                </h3>
            </div>
        </c:if>

        <!-- Giao dịch thất bại -->
        <c:if test="${transResult == false}">
            <div class="icon-container">
                <img src="image/fail-payment.png"
                     alt="Transaction Status" class="transaction-icon">
            </div>
            <div class="result failure">
                <h3>
                    Đơn hàng giao dịch thất bại!
                </h3>
                <p class="payment-result">Cảm ơn quý khách đã dùng dịch vụ của chúng tôi.</p>
            </div>
        </c:if>

        <!-- Đang xử lý giao dịch -->
        <c:if test="${transResult == null}">
            <div class="icon-container">
                <img src="image/fail-payment.png"
                     alt="Transaction Status" class="transaction-icon">
            </div>
            <div class="result processing">
                <h3>
                    Chúng tôi đã tiếp nhận đơn hàng, xin chờ quá trình xử lý!
                </h3>
            </div>
        </c:if>

        <!-- Các nút khác -->
        <div class="button-container">
            <a href="index.jsp" class="payment-result btn btn-home">Quay về trang chủ</a>
        </div>
    </section>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>

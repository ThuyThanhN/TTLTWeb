<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lịch tiêm thành công</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/success.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content -->
    <div class="success-page container mt-5">
        <div class="success-box text-center mx-auto p-4">
            <h1 class="fw-bold">ĐĂNG KÝ THÀNH CÔNG!</h1>
            <p>
                Quý khách đã đặt lịch tiêm vắc xin thành công. Việc đặt lịch đăng ký thông tin tại đây sẽ giúp Quý khách tiết kiệm thời gian khi làm thủ tục tại quầy lễ tân.
                Rất mong được đón tiếp Quý khách.
                <br>Trân trọng.
            </p>
            <div class="mt-4">
                <a href="dosing_schedule" class="btn btn-primary">Đăng Ký Mới</a>
                <a href="index" class="btn btn-outline-secondary">Trở về Trang Chủ</a>
            </div>                
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/show_navbarNav.js"></script>
</html>
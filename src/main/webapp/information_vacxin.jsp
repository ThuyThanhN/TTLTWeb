<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bệnh học về vắc xin</title>
    <link rel="icon" type="image/png" href="image/logo1.png">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/common-vaccination.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content -->
    <div class="main">
        <div class="container">
            <h4 class="title">Một số thông tin về vắc xin</h4>
            <div class="row">
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/nen-tiem-soi-don-hay-3-trong-1.jpg" class="card-img-top" alt="Nên tiêm sởi đơn hay 3 trong 1">
                        <div class="card-body">
                            <p class="card-text" title="Nên tiêm sởi đơn hay 3 trong 1: Lựa chọn nào tốt cho bé?">Nên tiêm sởi đơn hay 3 trong 1: Lựa chọn nào tốt cho bé?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/tiem-hpv-co-tac-dung-gi.jpg" class="card-img-top" alt="Tiêm HPV có tác dụng gì">
                        <div class="card-body">
                            <p class="card-text" title="Tiêm HPV có tác dụng gì? 5 lợi ích bạn sẽ nhận được từ vắc xin này">Tiêm HPV có tác dụng gì? 5 lợi ích bạn sẽ nhận được từ vắc xin này</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/co-nen-tiem-phe-cau-va-cum-cung-luc.jpg" class="card-img-top" alt="Có nên tiêm phế cầu và cúm cùng lúc">
                        <div class="card-body">
                            <p class="card-text" title="Có nên tiêm phế cầu và cúm cùng lúc? Mức độ an toàn và hiệu quả?">Có nên tiêm phế cầu và cúm cùng lúc? Mức độ an toàn và hiệu quả?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/bach-hau-ho-ga-uon-van-tiem-may-mui.jpg" class="card-img-top" alt="Bạch hầu ho gà uốn ván tiêm mấy mũi">
                        <div class="card-body">
                            <p class="card-text" title="Bạch hầu ho gà uốn ván tiêm mấy mũi? Tiêm khi nào hiệu quả nhất?">Bạch hầu ho gà uốn ván tiêm mấy mũi? Tiêm khi nào hiệu quả nhất?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/vac-xin-soi-tiem-may-mui-la-du.jpg" class="card-img-top" alt="Vắc xin sởi tiêm mấy mũi là đủ">
                        <div class="card-body">
                            <p class="card-text" title="Vắc xin sởi tiêm mấy mũi là đủ? Có cần tiêm nhắc lại không?">Vắc xin sởi tiêm mấy mũi là đủ? Có cần tiêm nhắc lại không?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/tre-dang-uong-siro-ho-co-tiem-phong-duoc-khong.jpg" class="card-img-top" alt="Trẻ đang uống siro ho có tiêm phòng được không">
                        <div class="card-body">
                            <p class="card-text" title="Trẻ đang uống siro ho có tiêm phòng được không? Lưu ý những gì?">Trẻ đang uống siro ho có tiêm phòng được không? Lưu ý những gì?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/tre-bi-ho-sau-khi-tiem-phong-6-trong-1.jpg" class="card-img-top" alt="Vacxin 6 trong 1 có mấy loại? Tiêm phòng được những bệnh gì?">
                        <div class="card-body">
                            <p class="card-text" title="Trẻ bị ho sau khi tiêm phòng 6 trong 1 có nguy hiểm không?">Trẻ bị ho sau khi tiêm phòng 6 trong 1 có nguy hiểm không?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/tiem-hpv-mui-3-tre.jpg" class="card-img-top" alt="Tiêm HPV mũi 3 trễ có sao không">
                        <div class="card-body">
                            <p class="card-text" title="Tiêm HPV mũi 3 trễ có sao không? Có bị giảm hiệu quả phòng bệnh?">Tiêm HPV mũi 3 trễ có sao không? Có bị giảm hiệu quả phòng bệnh?</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card">
                        <img src="image/roi-loan-tieu-hoa-co-tiem-phong-duoc-khong.jpg" class="card-img-top" alt="Rối loạn tiêu hóa có tiêm phòng được không">
                        <div class="card-body">
                            <p class="card-text" title="Rối loạn tiêu hóa có tiêm phòng được không? Cần lưu ý điều gì?">Rối loạn tiêu hóa có tiêm phòng được không? Cần lưu ý điều gì?</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/show_navbarNav.js"></script>
</html>
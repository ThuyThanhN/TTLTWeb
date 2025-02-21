<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <script src="https://kit.fontawesome.com/33ad855007.js" crossorigin="anonymous"></script>
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <!-- Css  -->
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Content -->
    <div class="main">
        <!-- slider -->
        <div id="section_slider" class="">
            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="image/slider1.jpeg" class="d-block w-100" alt="Slider1">
                    </div>
                    <div class="carousel-item">
                        <img src="image/slider2.jpeg" class="d-block w-100" alt="Slider2">
                    </div>
                    <div class="carousel-item">
                        <img src="image/slider3.jpeg" class="d-block w-100" alt="Slider3">
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <!-- Danh sach vaccine -->
        <div id="section_list_vaccine" class="">
            <div class="container px-md-0">
                <div class="row">
                    <div class="col-12 col-lg-3">
                        <div class="home_title">
                            <div class="title-left">Top vắc xin được đặt nhiều</div>
                        </div>
                        <!-- Khu vực cuộn -->
                        <div class="row scrollable-list">
                            <c:forEach var="topv" items="${topVaccines}">
                                <div class="col-12">
                                    <div class="vx_item">
                                        <a href="detail_vaccines?id=${topv.id}">
                                            <img src="${topv.imageUrl}" alt="${topv.name}">
                                            <div class="vaccine_name text-center" title="${topv.name}">${topv.name}</div>
                                        </a>
                                        <!-- Hiển thị lượt đặt -->
                                        <div class="text-center vaccine-bookings">Lượt đặt: ${topv.orderCount}</div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="col-12 col-lg-9">
                        <div class="home_title">
                            <div class="title-left">Danh mục vắc xin</div>
                            <div class="fst-italic title_view_all"><a href="vaccine-information">Xem tất cả</a></div>
                        </div>
                        <div class="row g-4">
                            <c:forEach var="ranv" items="${randomVaccines}">
                                <div class="col-12 col-md-3">
                                    <div class="vx_item">
                                        <a href="detail_vaccines?id=${ranv.id}">
                                            <img src="${ranv.imageUrl}" alt="${ranv.name}">
                                            <div class="vaccine_name text-center" title="${ranv.name}">${ranv.name}</div>
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--  Dich vu -->
        <div id="section-service" class="">
            <div class="container px-md-0">
                <div class="mb-3 home_title">
                    <div class="title-left">Dịch vụ</div>
                </div>
                <div class="row g-4">
                    <div class="col-12 col-md-3">
                        <div class="dv_item">
                            <a href="">
                                <img class="w-100" src="image/dv_yc.jpg" alt="">
                                <div class="dv_name text-center">Tiêm chủng <br> theo yêu cầu</div>
                            </a>
                        </div>
                    </div>
                    <div class="col-12 col-md-3">
                        <div class="dv_item">
                            <a href="">
                                <img class="w-100" src="image/dv_ba_bau.jpg" alt="">
                                <div class="dv_name text-center">Tiêm chủng cho người <br> chuẩn bị mang thai</div>
                            </a>
                        </div>
                    </div>
                    <div class="col-12 col-md-3">
                        <div class="dv_item">
                            <a href="">
                                <img class="w-100" src="image/dv_nguoi_lon.jpg" alt="">
                                <div class="dv_name text-center">Tiêm trọn gói <br> cho người lớn </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-12 col-md-3">
                        <div class="dv_item">
                            <a href="">
                                <img class="w-100" src="image/dv_tre_em.jpg" alt="">
                                <div class="dv_name text-center">Tiêm trọn gói <br> cho trẻ em</div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Benh hoc ve vaccine -->
        <div id="section-pathology" class="">
            <div class="container px-md-0">
                <div class="mb-3 home_title">
                    <div class="title-left">Bệnh học về vắc xin</div>
                </div>

                <div id="carouselExampleControls1" class="carousel" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <div class="card">
                                <a href=""><img src="image/bh_uon_van.jpg" class="card-img-top" alt="..."></a>
                                <div class="card-body">
                                    <a href=""><h5 class="card-title">Bị chó cắn có phải tiêm uốn ván không? Cần lưu ý điều gì?</h5></a>
                                    <p class="card-text">Theo thống kê của Bộ Y tế, chỉ tính riêng 9 tháng đầu năm 2023, đã có hàng trăm ngàn ca phơi nhiễm và 64 ca tử vong do bệnh dại.
                                        Vật nuôi, chó/mèo cắn, cào không chỉ gây bệnh dại cho người mà còn có nguy cơ nhiễm trùng uốn ván, nhiều trường hợp người bị vật nuôi cắn/cào không tử vong
                                        vì bệnh dại nhưng lại tử vong do biến chứng của uốn ván. Vậy, bị chó cắn có phải tiêm uốn ván không? Khi nào cần tiêm? Lịch tiêm như thế nào? Cần lưu ý điều gì sau khi tiêm uốn ván?
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="card">
                                <a href=""><img src="image/phe_cau_6in1.jpg" class="card-img-top" alt="..."></a>
                                <div class="card-body">
                                    <a href=""><h5 class="card-title">Nên tiêm phế cầu hay 6in1 trước? Tiêm cùng lúc được không?</h5></a>
                                    <p class="card-text">Bên cạnh các loại vắc xin phòng bệnh lao, viêm gan B, ho gà, bạch hầu, uốn ván, bại liệt,… phụ huynh nên tiêm phế cầu cho trẻ.
                                        Đây là mũi tiêm quan trọng bảo vệ trẻ trước sự đe dọa của phế cầu khuẩn.</p>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="card">
                                <a href=""><img src="image/chich_ngua_cho.jpeg" class="card-img-top" alt="..."></a>
                                <div class="card-body">
                                    <a href=""><h5 class="card-title">Chích ngưa chó cắn bao nhiêu tiền? Tiêm ở đâu uy tín, an toàn?</h5></a>
                                    <p class="card-text">Chích ngừa chó cắn bao nhiêu tiền? Địa điểm chích ngừa uy tín và an toàn là vấn đề được nhiều người dân quan tâm nhất là đối với loại vắc xin thường xuyên khan hiếm như vắc xin phòng dại.</p>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="card">
                                <a href=""><img src="image/phe_cau_sot.jpeg" class="card-img-top" alt="..."></a>
                                <div class="card-body">
                                    <a href=""><h5 class="card-title">Tiêm phế cầu có sốt không? Chăm sóc trẻ để giảm sốt ra sao?</h5></a>
                                    <p class="card-text">Tiêm phế cầu có sốt không? Tiêm phế cầu sau bao lâu thì sốt? Cách chăm sóc trẻ sau tiêm vắc xin phế cầu để giảm sốt như thế nào?…
                                        Sốt là tác dụng phụ thường gặp sau tiêm vắc xin phế cầu, tuy nhiên phụ huynh đừng quá lo lắng với phản ứng này vì sẽ tự biến mất nhanh chóng sau 1-2 ngày nếu chăm sóc trẻ đúng cách.</p>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <div class="card">
                                <a href=""><img src="image/6_bien_chung_bh.jpg" class="card-img-top" alt="..."></a>
                                <div class="card-body">
                                    <a href=""><h5 class="card-title">6 biến chứng bệnh bạch hầu đầy nguy hiểm không thể xem nhẹ</h5></a>
                                    <p class="card-text">Bạch hầu là một trong những “Bệnh truyền nhiễm nhóm B” có khả năng lây truyền nhanh, bùng phát thành dịch và tỷ lệ tử vong cao.
                                        Theo Cục Y tế Dự phòng, tỷ lệ tử vong do bệnh bạch hầu có thể lên đến 20% ở trẻ em dưới 5 tuổi và người lớn trên 40 tuổi.
                                        Nếu không có biện pháp phòng ngừa và điều trị kịp thời, các biến chứng bệnh bạch hầu như sốc nhiễm trùng, viêm cơ tim, viêm dây thần kinh, viêm phổi, suy thận… có thể đe dọa sức khỏe và tính mạng của người bệnh.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls1" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls1" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>

    <div id="backtop">
        <svg width="19" height="22" viewBox="0 0 19 22" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M2.37373 9.70711C1.98321 10.0976 1.35004 10.0976 0.959519 9.70711C0.568995 9.31658 0.568995 8.68342 0.959519 8.29289L2.37373 9.70711ZM9.66663 1L8.95952 0.292893C9.35004 -0.0976308 9.98321 -0.0976307 10.3737 0.292894L9.66663 1ZM18.3737 8.29289C18.7643 8.68342 18.7643 9.31658 18.3737 9.70711C17.9832 10.0976 17.35 10.0976 16.9595 9.70711L18.3737 8.29289ZM10.6666 21C10.6666 21.5523 10.2189 22 9.66663 22C9.11434 22 8.66663 21.5523 8.66663 21L10.6666 21ZM0.959519 8.29289L8.95952 0.292893L10.3737 1.70711L2.37373 9.70711L0.959519 8.29289ZM10.3737 0.292894L18.3737 8.29289L16.9595 9.70711L8.95952 1.70711L10.3737 0.292894ZM10.6666 1L10.6666 21L8.66663 21L8.66663 1L10.6666 1Z" fill="#222222"></path>
        </svg>
    </div>

    <div id="facebook-icon">
        <a href="https://www.facebook.com/kcntt.nlu?mibextid=ZbWKwL">
            <img src="image/facebook_64.png" alt="facebook" width="48" height="48">
        </a>
    </div>
</div>
</body>
<script src="js/index.js"></script>
<script src="js/show_navbarNav.js"></script>
</html>
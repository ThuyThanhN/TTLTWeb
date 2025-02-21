
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <!-- Css -->
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<div class="footer">
    <div class="container-lg">
        <div class="row">
            <div class="col-12 col-lg-3">
                <a href="index.jsp">
                    <img src="image/logo.png" alt="Logo" class="footer-logo">
                </a>
                <div id="copy-right">
                    <div class="content">TRƯỜNG ĐẠI HỌC NÔNG LÂM</div>
                </div>
                <div class="single-contact">
                    <span class="content ">Khoa Công Nghệ Thông Tin</span>
                </div>
                <div class="single-contact">
                    <span><i class="fa fa-map-marker-alt"></i></span>
                    <span class="content">Địa chỉ: <span>Khu Phố 6, Đường số 6, Linh Trung, TP.Thủ Đức, TP.Hồ Chí Minh</span></span>
                </div>
                <div class="single-contact">
                    <i class="fa-solid fa-phone-volume shake-icon"></i>
                    <div class="content">Tư vấn ngay: 023 2343 8445</div>
                </div>
                <div class="single-contact">
                    <i class="fa-solid fa-envelope"></i>
                    <div class="content">Email: <span>cskh@ttt.vn</span></div>
                </div>
            </div>
            <div class="col-12 col-lg-3">
                <div class="footer-title">Chính sách</div>
                <ul class="list-menu">
                    <li class="li-menu"><a href="#">Chính sách bảo mật</a></li>
                    <li class="li-menu"><a href="#">Chính sách thanh toán</a></li>
                    <li class="li-menu"><a href="#">Chính sách tiêm chủng</a></li>
                </ul>
            </div>
            <div class="col-12 col-lg-3">
                <div class="footer-title">Hỗ trợ khách hàng</div>
                <ul class="list-menu">
                    <li class="li-menu"><a href="vaccine-information.jsp">Tìm kiếm</a></li>
                    <li class="li-menu"><a href="login.jsp">Đăng nhập</a></li>
                    <li class="li-menu"><a href="register.jsp">Đăng ký</a></li>
                    <li class="li-menu"><a href="dosing_schedule.jsp">Đặt lịch tiêm chủng</a></li>
                </ul>
            </div>
            <div class="col-12 col-lg-3">
                <div class="footer-title">Đăng ký nhận tin</div>
                <p class="text-white pt-2 newsletter">Bạn muốn nhận thông báo về tiêm chủng? Đăng ký ngay.</p>

                <div class="email">
                    <input type="text" class="form-control input-group-field" placeholder="Nhập địa chỉ email">
                    <button class="btn text-white button-subscribe">Đăng ký</button>
                </div>

                <div class="d-flex flex-wrap gap-2 mt-3">
                    <div class="social-media">
                        <a href="https://www.facebook.com/kcntt.nlu?mibextid=ZbWKwL"><img src="image/facebook.png" alt="Facebook"></a>
                    </div>
                    <div class="social-media">
                        <a href="#"><img src="image/youtube.png" alt="Facebook"></a>
                    </div>
                    <div>
                        <a href="http://online.gov.vn/Home/WebDetails/47906?AspxAutoDetectCookieSupport=1">
                            <img style="max-width: 150px; height:28px" src="image/dathongbao.png" alt="">
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


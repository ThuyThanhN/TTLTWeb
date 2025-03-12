<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Sản Phẩm Vắc Xin</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <!-- Font awesome-->
<%--    <script src="https://kit.fontawesome.com/33ad855007.js" crossorigin="anonymous"></script>--%>
    <script src="https://kit.fontawesome.com/4760c40adb.js" crossorigin="anonymous"></script>
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <!-- Css   -->
    <link rel="stylesheet" href="css/vaccine-information.css">
</head>
<body>
<div id="wrapper">
    <!--    Phần header -->
</div>
<jsp:include page="header.jsp"></jsp:include>
<!--    Phần content -->
<div class="main">
    <div class="vaccine-info-container">
        <h1 class="title">Thông Tin Sản Phẩm Vắc Xin</h1>
        <div class="button-group">
            <!-- Nút "VẮC XIN THEO NHÓM ĐỘ TUỔI" -->
            <button class="btn" onclick="ageFilter()">VẮC XIN THEO NHÓM ĐỘ TUỔI
            </button>

            <!-- Nút "VẮC XIN THEO NHÓM BỆNH" -->
            <button class="btn" onclick="diseaseFilter()">VẮC XIN THEO NHÓM BỆNH
            </button>
        </div>
        <div class="search-bar">
            <form action="/vaccine-information" method="get">
                <input style="width: 500px" type="text" id="searchQuery" name="searchQuery" placeholder="Tìm kiếm..."
                       value="${searchQuery != null ? searchQuery : ''}">
                <button type="button" id="searchBtn" class="search-btn">
                    <i class="fa-solid fa-magnifying-glass" style="color: #000000;"></i>
                </button>

            </form>
        </div>


        <!-- Carousel -->
        <div id="vaccineCarousel" class="carousel slide" data-bs-ride="carousel">
            <!-- Indicators -->
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#vaccineCarousel" data-bs-slide-to="0" class="active"
                        aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#vaccineCarousel" data-bs-slide-to="1"
                        aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#vaccineCarousel" data-bs-slide-to="2"
                        aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#vaccineCarousel" data-bs-slide-to="3"
                        aria-label="Slide 4"></button>
                <button type="button" data-bs-target="#vaccineCarousel" data-bs-slide-to="4"
                        aria-label="Slide 5"></button>
            </div>

            <!-- Carousel Items -->
            <div class="carousel-inner">
                <!-- Slide 1 -->
                <div class="carousel-item active">
                    <div class="d-flex justify-content-center">
                        <!--flexbox layout, justify-content: center; cho phần tử-->
                        <div class="vaccine-item mx-3 text-center">
                            <!--mx-3 sẽ thêm một khoảng cách margin ngang là 1rem (trái và phải) vào phần tử-->
                            <img src="image/iterm-2.png" alt="Vắc Xin 1">
                            <p>VẮC XIN 5IN1</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-2-1.png" alt="Vắc Xin 2">
                            <p>VẮC XIN VIÊM GAN B</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-16.png" alt="Vắc Xin 3">
                            <p>VẮC XIN 4IN1</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-3.png" alt="Vắc Xin 4">
                            <p>VẮC XIN ROTA VIRUS</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-TruocMangThai.png" alt="Vắc Xin 5">
                            <p>VẮC XIN CHO PHỤ NỮ TRƯỚC MANG THAI</p>
                        </div>
                    </div>
                </div>
                <!-- Slide 2 -->
                <div class="carousel-item">
                    <div class="d-flex justify-content-center">
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-1-2.png" alt="Vắc Xin 6">
                            <p>VẮC XIN VIÊM NÃO</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-PheCauKhuan.png" alt="Vắc Xin 7">
                            <p>VẮC XIN PHẾ CẦU KHUẨN</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-ViemGanA.png" alt="Vắc Xin 8">
                            <p>VẮC XIN VIÊM GAN A+B</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-PhongLao.png" alt="Vắc Xin 9">
                            <p>VẮC XIN PHÒNG LAO</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-CoTuCung.png" alt="Vắc Xin 10">
                            <p>VẮC XIN PHÒNG UNG THƯ CỔ TỬ CUNG</p>
                        </div>
                    </div>
                </div>
                <!-- Slide 3 -->
                <div class="carousel-item">
                    <div class="d-flex justify-content-center">
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-UonVan.png" alt="Vắc Xin 11">
                            <p>VẮC XIN UỐN VÁN</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-ViemGanBTreEm.png" alt="Vắc Xin 12">
                            <p>VẮC XIN VIÊM GAN B TRẺ EM</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-ThuongHan.png" alt="Vắc Xin 13">
                            <p>VẮC XIN THƯƠNG HÀN</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-PhongTa.png" alt="Vắc Xin 14">
                            <p>VẮC XIN PHÒNG TẢ</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-Tre13.png" alt="Vắc Xin 15">
                            <p>VẮC XIN CHO TRẺ 13 ĐẾN 24 THÁNG TUỔI</p>
                        </div>
                    </div>
                </div>
                <!-- Slide 4 -->
                <div class="carousel-item">
                    <div class="d-flex justify-content-center">
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-ViemMangNao.png" alt="Vắc Xin 16">
                            <p>VẮC XIN VIÊM MÀNG NÃO</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-PhongDai.png" alt="Vắc Xin 17">
                            <p>VẮC XIN PHÒNG DẠI</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-6IN1.png" alt="Vắc Xin 18">
                            <p>VẮC XIN 6IN1</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-HIBpng.png" alt="Vắc Xin 19">
                            <p>VẮC XIN VIÊM MÀNG NÃO DO HIB</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-Phongcum.png" alt="Vắc Xin 20">
                            <p>VẮC XIN PHÒNG CÚM</p>
                        </div>
                    </div>
                </div>
                <!-- Slide 5 -->
                <div class="carousel-item">
                    <div class="d-flex justify-content-center">
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-SotVang.png" alt="Vắc Xin 21">
                            <p>VẮC XIN SỐT VÀNG</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-Soi.png" alt="Vắc Xin 22">
                            <p>VẮC XIN PHÒNG SỞI</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-Covid19.png" alt="Vắc Xin 23">
                            <p>VẮC XIN COVID-19</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-ThuyDau.png" alt="Vắc Xin 24">
                            <p>VẮC XIN PHÒNG THỦY ĐẬU</p>
                        </div>
                        <div class="vaccine-item mx-3 text-center">
                            <img src="image/iterm-Tre0.png" alt="Vắc Xin 25">
                            <p>VẮC XIN CHO TRẺ 0 ĐẾN DƯỚI 2 THÁNG TUỔI</p>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Controls -->
            <button class="carousel-control-prev" type="button" data-bs-target="#vaccineCarousel" data-bs-slide="prev">
                <!--                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>-->
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#vaccineCarousel" data-bs-slide="next">
                <!--                    <span class="carousel-control-next-icon" aria-hidden="true"></span>-->
                <span class="visually-hidden">Next</span>
            </button>
        </div>

    </div>

    <%-- danh sách vaccine --%>
    <div class="main-content">
        <div class="container">
            <div id="vaccine-list" class="row">

            </div>
            <!-- Phân trang -->
            <ul id="pagination" class="pagination justify-content-center mb-3">

            </ul>
        </div>
    </div>
</div>
<!--    Phần footer -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
<script src="js/search.js"></script>

</body>
</html>

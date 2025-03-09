
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<div id="wrapper">
    <div class="header text-center">
        <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
    </div>
    <div class="form-container">
        <h2>ĐĂNG KÝ</h2>
        <!-- Form gửi dữ liệu đến servlet "registerUsers" -->
        <form id="registrationForm" action="registerUsers" method="POST">
            <div class="section">
                <div class="section-title"><strong>Thông Tin Chung</strong></div>
                <div class="form-group">
                    <div>
                        <label for="full-name"><strong>Họ và tên <span class="required">*</span></strong></label>
                        <input type="text" id="full-name" name="fullname" required>
                    </div>
                    <div>
                        <label for="gender"><strong>Giới tính <span class="required">*</span></strong></label>
                        <select id="gender" name="gender" required>
                            <option value="">Chọn giới tính</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                            <option value="Khác">Khác</option>
                        </select>
                    </div>
                    <div>
                        <label for="id-number"><strong>Mã định danh </strong></label>
                        <input type="text" id="id-number" name="identification" required>
                        <span class="error-message" id="id-number-error">Vui lòng điền mã định danh.</span>
                    </div>
                    <div>
                        <label for="dob"><strong>Ngày sinh <span class="required">*</span></strong></label>
                        <input type="date" id="dob" name="dob" required>
                    </div>
                </div>
                <div class="form-group">
                    <div>
                        <label for="email"><strong>Email <span class="required">*</span></strong></label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="dropdown">
                        <label for="province-select"><strong>Tỉnh thành <span class="required">*</span></strong></label>
                        <input type="text" placeholder="Chọn" id="province-select" name="province"
                               class="form-control dropdown-toggle province-select"
                               data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-angle-down"></i>
                        <ul class="dropdown-menu province-menu"></ul>
                    </div>
                    <div class="dropdown">
                        <label for="district-select"><strong>Quận huyện <span class="required">*</span></strong></label>
                        <input type="text" placeholder="Chọn tỉnh thành trước" id="district-select" name="district"
                               class="form-control dropdown-toggle district-select"
                               data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-angle-down"></i>
                        <ul class="dropdown-menu district-menu"></ul>
                    </div>
                    <div class="dropdown">
                        <label for="ward-select"><strong>Phường xã <span class="required">*</span></strong></label>
                        <input type="text" placeholder="Chọn quận huyện trước" id="ward-select" name="ward"
                               class="form-control dropdown-toggle ward-select"
                               data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-angle-down"></i>
                        <ul class="dropdown-menu ward-menu"></ul>
                    </div>
                </div>
                <div class="form-group">
                    <div>
                        <label for="address"><strong>Số nhà/Đường <span class="required">*</span></strong></label>
                        <input type="text" id="address" name="address" required>
                    </div>
                </div>
            </div>
            <div class="section">
                <div class="section-title"><strong>Thông Tin Tài Khoản</strong></div>
                <div class="form-group">
                    <div>
                        <label for="phone"><strong>SĐT (Tài Khoản) <span class="required">*</span></strong></label>
                        <input type="text" id="phone" name="phone" required>
                    </div>
                    <div>
                        <label for="password"><strong>Mật khẩu <span class="required">*</span></strong></label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div>
                        <label for="confirm-password"><strong>Xác nhận mật khẩu <span class="required">*</span></strong></label>
                        <input type="password" id="confirm-password" name="confirmPassword" required>
                        <span class="error-message" id="confirm-password-error">Mật khẩu không khớp.</span>
                    </div>
                </div>
                <div class="note"><span class="required">(*)</span> Là trường thông tin bắt buộc.</div>
                <button type="submit" class="submit-btn">Đăng ký</button>
            </div>
        </form>
    </div>
    <!-- Footer -->
    <jsp:include page="footer.jsp"></jsp:include>
</div>
<script src="js/register.js"></script>
<script src="js/api_address.js"></script>
</body>
</html>

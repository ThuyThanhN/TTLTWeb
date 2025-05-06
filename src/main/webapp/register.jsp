<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký</title>
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
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<div id="wrapper">
    <div class="header text-center">
        <a href="index"><img src="image/logo.png" alt="Logo" class="img-responsive"></a>
    </div>
    <div class="form-container">
        <h5 class="mt-2">ĐĂNG KÝ</h5>
        <div class="text-center" style="color: #8F8F8F; font-size: 15px;">Đăng ký để tạo và truy cập ứng dụng của
            bạn
        </div>
        <form id="registrationForm" action="registerUsers" method="POST">
            <div class="form-container">
                <div class="section">
                    <h6>Thông tin chung</h6>
                    <div class="row">
                        <div class="col-sm-12 col-md-6 col-lg-3">
                            <div class="form-group">
                                <label for="full-name">Họ và tên <span class="required">*</span></label>
                                <input type="text" id="full-name" name="fullname" placeholder="Nhập tên"
                                       required>
                                <span class="error-message" id="full-name-error"
                                      style="display:none; color: red;">Vui lòng điền họ và tên.</span>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-3">
                            <div class="form-group">
                                <label for="gender">Giới tính <span class="required">*</span></label>
                                <select id="gender" name="gender" required>
                                    <option value="">Chọn giới tính</option>
                                    <option value="Nam">Nam</option>
                                    <option value="Nữ">Nữ</option>
                                    <option value="Khác">Khác</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-3">
                            <div class="form-group">
                                <label for="id-number">Mã định danh </label>
                                <input type="text" id="id-number" name="identification"
                                       placeholder="Nhập mã định danh" required>
                                <span class="error-message"
                                      id="id-number-error">Vui lòng điền mã định danh.</span>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-3">
                            <div class="form-group">
                                <label for="dob">Ngày sinh <span class="required">*</span></label>
                                <input type="date" id="dob" name="dob" required>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="dropdown">
                                    <label for="province-select">Tỉnh thành <span class="required">*</span></label>
                                    <input type="text" placeholder="Chọn" id="province-select" name="province"
                                           class="form-control dropdown-toggle province-select"
                                           data-bs-toggle="dropdown" aria-expanded="false">
                                    <ul class="dropdown-menu province-menu"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="dropdown">
                                    <label for="district-select">Quận huyện <span class="required">*</span></label>
                                    <input type="text" placeholder="Chọn tỉnh thành trước" id="district-select"
                                           name="district"
                                           class="form-control dropdown-toggle district-select"
                                           data-bs-toggle="dropdown" aria-expanded="false">
                                    <ul class="dropdown-menu district-menu"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="form-group">
                                <div class="dropdown">
                                    <label for="ward-select">Phường xã <span class="required">*</span></label>
                                    <input type="text" placeholder="Chọn quận huyện trước" id="ward-select"
                                           name="ward"
                                           class="form-control dropdown-toggle ward-select"
                                           data-bs-toggle="dropdown" aria-expanded="false">
                                    <ul class="dropdown-menu ward-menu"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <div>
                                    <label for="address">Số nhà/Đường <span class="required">*</span></label>
                                    <input type="text" id="address" name="address" placeholder="Nhập số nhà/đường"
                                           required>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6">
                            <div class="form-group">
                                <label for="email">Email <span class="required">*</span></label>
                                <input type="email" id="email" name="email" placeholder="Nhập địa chỉ email"
                                       required>
                                <span class="error-message" id="email-error" style="display:none; color: red;">Vui lòng nhập một địa chỉ email hợp lệ.</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="section">
                    <h6>Thông tin tài khoản</h6>
                    <div class="row">
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="form-group">
                                <label for="phone">SĐT (Tài Khoản) <span class="required">*</span></label>
                                <input type="tel" id="phone" name="phone" placeholder="Nhập số điện thoại" required>
                                <span class="error-message" id="phone-error"
                                      style="color: red; display: none;"></span>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="form-group">
                                <label for="password">Mật khẩu <span class="required">*</span></label>
                                <input type="password" id="password" name="password" placeholder="Nhập mật khẩu"
                                       required>
                                <span id="password-error" style="color: red; display: none;"></span>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 col-lg-4">
                            <div class="form-group">
                                <label for="confirm-password">Xác nhận mật khẩu <span
                                        class="required">*</span></label>
                                <input type="password" id="confirm-password" name="confirmPassword"
                                       placeholder="Nhập mật khẩu" required>
                                <span class="error-message" id="confirm-password-error">Mật khẩu không khớp.</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row"><div class="col-12">
                    <input class="form-check-input" type="checkbox" value="" id="checkbox">
                    <label class="m-0" for="checkbox">Tôi đã đồng ý với <a href="#">Điều khoản &amp; Điều
                        kiện</a> Và <a href="#">Chính sách bảo mật</a></label>
                    <span id="checkbox-error" style="display:none; color:red;">Bạn phải đồng ý với Điều khoản và Chính sách bảo mật để đăng ký.</span>
                </div>

                    <div class="col-12 text-center">
                        <button type="submit" class="submit-btn">Tạo tài khoản</button>
                    </div>
                    <div class="col-12 text-center" style="font-size: 16px; color: #8F8F8F;">
                        Bạn đã là thành viên? <a href="login">Đăng nhâp</a>
                    </div>
                </div>
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
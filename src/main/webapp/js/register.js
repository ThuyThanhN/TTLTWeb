document.addEventListener("DOMContentLoaded", function() {
  // Kiểm tra mã định danh
  document.getElementById("id-number").addEventListener("input", function () {
    const idNumberInput = this.value;
    const errorMessage = document.getElementById("id-number-error");

    if (idNumberInput === "") {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Vui lòng điền mã định danh.";
    } else if (!/^[0-9]*$/.test(idNumberInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Mã định danh chỉ được chứa các số.";
    } else if (idNumberInput.length < 12) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Mã định danh phải đủ 12 số.";
    } else if (idNumberInput.length > 12) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Mã định danh không được vượt quá 12 số.";
    } else {
      errorMessage.style.display = "none";
    }
  });

  // Kiểm tra họ và tên
  document.getElementById("full-name").addEventListener("input", function () {
    const fullNameInput = this.value;
    const errorMessage = document.getElementById("full-name-error");

    if (fullNameInput.trim() === "") {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Vui lòng điền họ và tên.";
    } else {
      errorMessage.style.display = "none"; // Ẩn thông báo lỗi nếu có giá trị
    }
  });

  // Ngừng thông báo mặc định của trình duyệt khi form được submit
  document.querySelector('form').addEventListener('submit', function (event) {
    const fullNameInput = document.getElementById('full-name');
    const errorMessage = document.getElementById('full-name-error');

    // Kiểm tra họ và tên
    if (fullNameInput.validity.valueMissing) {
      event.preventDefault(); // Ngừng gửi form nếu trường trống
      errorMessage.style.display = "block"; // Hiển thị thông báo lỗi tùy chỉnh
      errorMessage.textContent = "Vui lòng điền họ và tên.";
    }

    // Kiểm tra checkbox đồng ý với Điều khoản và Chính sách bảo mật
    const checkbox = document.getElementById("checkbox");
    const checkboxError = document.getElementById("checkbox-error"); // Lấy thẻ span lỗi checkbox
    if (!checkbox.checked) {
      event.preventDefault(); // Ngừng gửi form nếu checkbox không được tích
      checkboxError.style.display = "block"; // Hiển thị thông báo lỗi khi checkbox chưa được tích
    } else {
      checkboxError.style.display = "none"; // Ẩn thông báo lỗi khi checkbox được tích
    }
  });

  // Kiểm tra email
  document.getElementById("email").addEventListener("input", function () {
    const emailInput = this.value;
    const errorMessage = document.getElementById("email-error");

    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    if (!emailPattern.test(emailInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Vui lòng nhập một địa chỉ email hợp lệ.";
    } else {
      errorMessage.style.display = "none";
    }
  });

  // Kiểm tra số điện thoại
  document.getElementById("phone").addEventListener("input", function () {
    const phoneInput = this.value;
    const errorMessage = document.getElementById("phone-error");

    const phonePattern = /^\d{10,11}$/;

    if (!phonePattern.test(phoneInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Số điện thoại phải có 10 hoặc 11 chữ số.";
    } else {
      errorMessage.style.display = "none";
    }
  });

  // Kiểm tra mật khẩu
  document.getElementById("password").addEventListener("input", function () {
    const passwordInput = this.value;
    const errorMessage = document.getElementById("password-error");

    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    if (!passwordPattern.test(passwordInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, chữ số và ký tự đặc biệt.";
    } else {
      errorMessage.style.display = "none";
    }
  });

  // Kiểm tra mật khẩu và xác nhận mật khẩu
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirm-password');
  const confirmPasswordError = document.getElementById('confirm-password-error');

  function checkPasswordMatch() {
    if (confirmPassword.value.trim() === '') {
      confirmPasswordError.style.display = 'block';
      confirmPasswordError.textContent = 'Xác nhận mật khẩu không được để trống';
    } else if (password.value !== confirmPassword.value) {
      confirmPasswordError.style.display = 'block';
      confirmPasswordError.textContent = 'Mật khẩu và xác nhận mật khẩu không khớp';
    } else {
      confirmPasswordError.style.display = 'none';
    }
  }

  password.addEventListener('input', checkPasswordMatch);
  confirmPassword.addEventListener('input', checkPasswordMatch);

}); // Đóng ngoặc sự kiện DOMContentLoaded

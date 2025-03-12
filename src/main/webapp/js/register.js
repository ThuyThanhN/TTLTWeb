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

    // Kiểm tra nếu trường họ và tên trống
    if (fullNameInput.trim() === "") {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Vui lòng điền họ và tên.";
    } else {
      errorMessage.style.display = "none"; // Ẩn thông báo lỗi nếu có giá trị
    }
  });

  // Ngừng thông báo mặc định của trình duyệt khi form được submit
  document.querySelector('form').addEventListener('submit', function(event) {
    const fullNameInput = document.getElementById('full-name');
    const errorMessage = document.getElementById('full-name-error');

    if (fullNameInput.validity.valueMissing) {
      event.preventDefault(); // Ngừng gửi form nếu trường trống
      errorMessage.style.display = "block"; // Hiển thị thông báo lỗi tùy chỉnh
      errorMessage.textContent = "Vui lòng điền họ và tên.";
    }
  });

  // Kiểm tra email
  document.getElementById("email").addEventListener("input", function () {
    const emailInput = this.value;
    const errorMessage = document.getElementById("email-error");

    // Biểu thức chính quy để kiểm tra tính hợp lệ của email
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    // Kiểm tra nếu email không hợp lệ
    if (!emailPattern.test(emailInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Vui lòng nhập một địa chỉ email hợp lệ.";
    } else {
      errorMessage.style.display = "none"; // Ẩn thông báo lỗi nếu email hợp lệ
    }
  });

// Kiểm tra số điện thoại
  document.getElementById("phone").addEventListener("input", function () {
    const phoneInput = this.value;
    const errorMessage = document.getElementById("phone-error");

    // Biểu thức chính quy để kiểm tra số điện thoại có 10 hoặc 11 chữ số
    const phonePattern = /^\d{10,11}$/;

    // Kiểm tra nếu số điện thoại không hợp lệ
    if (!phonePattern.test(phoneInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Số điện thoại phải có 10 hoặc 11 chữ số.";
    } else {
      errorMessage.style.display = "none"; // Ẩn thông báo lỗi nếu số điện thoại hợp lệ
    }
  });



  // Kiểm tra mật khẩu và xác nhận mật khẩu
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirm-password');
  const confirmPasswordError = document.getElementById('confirm-password-error');

  function checkPasswordMatch() {
    if (confirmPassword.value.trim() === '') {
      confirmPasswordError.style.display = 'block';
    } else if (password.value !== confirmPassword.value) {
      confirmPasswordError.style.display = 'block';
    } else {
      confirmPasswordError.style.display = 'none';
    }
  }

  password.addEventListener('input', checkPasswordMatch);
  confirmPassword.addEventListener('input', checkPasswordMatch);
});

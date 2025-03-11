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

  document.getElementById("phone").addEventListener("input", function () {
    const phoneInput = this.value;
    const errorMessage = document.getElementById("phone-error");

    if (!/^[0-9]*$/.test(phoneInput)) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Số điện thoại chỉ được chứa các số.";
    } else if (phoneInput.length < 10) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Số điện thoại phải có ít nhất 10 số.";
    } else if (phoneInput.length > 11) {
      errorMessage.style.display = "block";
      errorMessage.textContent = "Số điện thoại chỉ được tối đa 11 số.";
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
    } else if (password.value !== confirmPassword.value) {
      confirmPasswordError.style.display = 'block';
    } else {
      confirmPasswordError.style.display = 'none';
    }
  }

  password.addEventListener('input', checkPasswordMatch);
  confirmPassword.addEventListener('input', checkPasswordMatch);
});

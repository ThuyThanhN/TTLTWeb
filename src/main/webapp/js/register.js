document.getElementById("id-number").addEventListener("input", function () {
  const idNumberInput = this.value;
  const errorMessage = document.getElementById("id-number-error");

  if (idNumberInput === "") {
    // Nếu người dùng xóa nội dung
    errorMessage.style.display = "block";
    errorMessage.textContent = "Vui lòng điền mã định danh.";
  } else if (!/^[0-9]*$/.test(idNumberInput)) {
    // Nếu có ký tự không phải số
    errorMessage.style.display = "block";
    errorMessage.textContent = "Mã định danh chỉ được chứa các số.";
  } else if (idNumberInput.length < 12) {
    // Nếu không đủ 12 số
    errorMessage.style.display = "block";
    errorMessage.textContent = "Mã định danh phải đủ 12 số.";
  } else if (idNumberInput.length > 12) {
    // Kiểm tra trường hợp vượt quá độ dài (ngăn chặn bằng maxlength, nhưng thêm để an toàn)
    errorMessage.style.display = "block";
    errorMessage.textContent = "Mã định danh không được vượt quá 12 số.";
  } else {
    // Nếu hợp lệ
    errorMessage.style.display = "none";
  }
});

// Kiểm tra mật khẩu và xác nhận mật khẩu
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirm-password');
const errorMessage = document.getElementById('confirm-password-error');

function checkPasswordMatch() {
  if (confirmPassword.value.trim() === '') {
    errorMessage.style.display = 'block';
  } else if (password.value !== confirmPassword.value) {
    errorMessage.style.display = 'block';
  } else {
    errorMessage.style.display = 'none';
  }
}

password.addEventListener('input', checkPasswordMatch);
confirmPassword.addEventListener('input', checkPasswordMatch);

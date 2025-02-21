document.addEventListener('DOMContentLoaded', function () {
    // Lấy các phần tử cho mật khẩu hiện tại, mật khẩu mới và xác nhận mật khẩu
    const toggleCurrentPassword = document.querySelector('#toggleCurrentPassword');
    const currentPasswordInput = document.querySelector('#currentPassword');

    const toggleNewPassword = document.querySelector('#toggleNewPassword');
    const newPasswordInput = document.querySelector('#newPassword');

    const toggleConfirmNewPassword = document.querySelector('#toggleConfirmNewPassword');
    const confirmNewPasswordInput = document.querySelector('#confirmNewPassword');

    const submitButton = document.querySelector('#submitButton');

    // toggle mật khẩu(tắt và hiện pass)
    const togglePasswordVisibility = (toggleButton, passwordInput) => {
        toggleButton.addEventListener('click', function () {
            // Toggle giữa "password" và "text"
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            // Thay đổi biểu tượng
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    };

    // Gọi cho từng trường mật khẩu
    togglePasswordVisibility(toggleCurrentPassword, currentPasswordInput);
    togglePasswordVisibility(toggleNewPassword, newPasswordInput);
    togglePasswordVisibility(toggleConfirmNewPassword, confirmNewPasswordInput);

    // kiểm tra điều kiện
    const validatePasswords = () => {
        const currentPassword = currentPasswordInput.value;
        const newPassword = newPasswordInput.value;
        const confirmNewPassword = confirmNewPasswordInput.value

        // Kiểm tra mật khẩu mới khác mật khẩu hiện tại
        if (newPassword === currentPassword) {
            alert("Mật khẩu mới phải khác mật khẩu hiện tại. Vui lòng nhập lại mật khẩu mới.");
            newPasswordInput.value = ""; // Xóa mật khẩu mới đã nhập
            confirmNewPasswordInput.value = ""; // Xóa xác nhận mật khẩu mới
            return false;
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu phải giống nhau
        if (newPassword !== confirmNewPassword) {
            alert("Mật khẩu mới và xác nhận mật khẩu không khớp. Vui lòng nhập lại.");
            confirmNewPasswordInput.value = ""; // Xóa xác nhận mật khẩu mới
            return false;
        }
        alert('Đổi mật khẩu thành công!');
        return true;

    };

    // Xử lý khi người dùng nhấn nút Xác nhận
    submitButton.addEventListener('click', function (e) {
        if (!validatePasswords()) {
            e.preventDefault(); // Ngăn chặn nếu không thỏa mãn điều kiện
        }

    });

    function closeNav() {
        document.querySelector('.navbar-collapse').classList.remove('show');
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const toggleCurrentPassword = document.querySelector('#toggleCurrentPassword');
    const currentPasswordInput = document.querySelector('#currentPassword');

    const toggleNewPassword = document.querySelector('#toggleNewPassword');
    const newPasswordInput = document.querySelector('#newPassword');
    const newPasswordError = document.querySelector('#newPassword-error');
    const samePasswordError = document.getElementById('samePassword-error');
    const confirmPasswordError = document.getElementById('confirmPassword-error');

    const toggleConfirmNewPassword = document.querySelector('#toggleConfirmNewPassword');
    const confirmNewPasswordInput = document.querySelector('#confirmNewPassword');
    const submitButton = document.querySelector('#submitButton');

    const form = document.querySelector('#changePasswordForm'); // Lấy phần tử form

    const togglePasswordVisibility = (toggleButton, passwordInput) => {
        if (toggleButton && passwordInput) {
            toggleButton.addEventListener('click', function () {
                const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordInput.setAttribute('type', type);
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            });
        }
    };

    togglePasswordVisibility(toggleCurrentPassword, currentPasswordInput);
    togglePasswordVisibility(toggleNewPassword, newPasswordInput);
    togglePasswordVisibility(toggleConfirmNewPassword, confirmNewPasswordInput);

    // Kiểm tra điều kiện mật khẩu
    const validatePasswords = () => {
        const currentPassword = currentPasswordInput.value;
        const newPassword = newPasswordInput.value;
        const confirmNewPassword = confirmNewPasswordInput.value;


        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordPattern.test(newPassword)) {
            newPasswordError.style.display = "block";
            newPasswordInput.value = "";
            confirmNewPasswordInput.value = "";
            return false;
        } else {
            newPasswordError.style.display = "none";
        }

        alert('Đổi mật khẩu thành công!');
        return true;
    };

    // Ngăn chặn gửi form nếu mật khẩu không hợp lệ
    if (submitButton) {
        submitButton.addEventListener('click', function (e) {
            // Ngăn chặn hành động mặc định của nút submit
            e.preventDefault();

            // Kiểm tra mật khẩu và gửi form nếu hợp lệ
            if (validatePasswords()) {
                form.submit(); // Gửi form nếu tất cả kiểm tra thành công
            }
        });
    }

    newPasswordInput.addEventListener('input', () => {
        if (newPasswordInput.value === currentPasswordInput.value && newPasswordInput.value !== "") {
            samePasswordError.style.display = "block";
        } else {
            samePasswordError.style.display = "none";
        }
    });

    confirmNewPasswordInput.addEventListener('input', () => {
        if (
            confirmNewPasswordInput.value !== "" &&
            confirmNewPasswordInput.value !== newPasswordInput.value
        ) {
            confirmPasswordError.style.display = "block";
        } else {
            confirmPasswordError.style.display = "none";
        }
    });

    function closeNav() {
        const navbarCollapse = document.querySelector('.navbar-collapse');
        if (navbarCollapse) {
            navbarCollapse.classList.remove('show');
        }
    }
});

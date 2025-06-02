document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('error-modal');
    const okButton = document.getElementById('ok-modal');
    const closeButton = document.getElementById('close-modal');
    const errorMessage = document.getElementById('error-message');
    const loginForm = document.getElementById('login-form');
    const loginButton = document.getElementById('login-button');
    const spinner = document.createElement('div');
    spinner.classList.add('spinner');
    loginButton.appendChild(spinner);

    const hideModal = () => modal.classList.add('hidden');
    const closeModal = () => hideModal();

    if (okButton) okButton.addEventListener('click', hideModal);
    if (closeButton) closeButton.addEventListener('click', closeModal);

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Kiểm tra reCAPTCHA trước
        const captchaResponse = grecaptcha.getResponse();
        console.log('Token reCAPTCHA lấy được:', captchaResponse);
        if (!captchaResponse) {
            alert('Vui lòng xác nhận reCAPTCHA trước khi đăng nhập!');
            return;  // Dừng gửi form nếu chưa tích captcha
        }

        // Sau khi có token captcha, lấy username và password
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();

        if (!username || !password) {
            alert('Vui lòng nhập đầy đủ tài khoản và mật khẩu.');
            return;
        }

        const params =
            'username=' + encodeURIComponent(username) +
            '&password=' + encodeURIComponent(password) +
            '&g-recaptcha-response=' + encodeURIComponent(captchaResponse);

        console.log('Dữ liệu gửi lên server:', params);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'login', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

        loginButton.classList.add('btn-loading');
        spinner.style.display = 'block';

        xhr.onload = function() {
            if (xhr.status === 200) {
                const response = xhr.responseText;
                console.log('Server response:', response);

                if (response === "captcha_missing") {
                    alert('Vui lòng xác nhận reCAPTCHA trước khi đăng nhập!');
                    grecaptcha.reset();
                } else if (response === "captcha_failed") {
                    alert('Xác thực reCAPTCHA thất bại, vui lòng thử lại.');
                    grecaptcha.reset();
                } else if (response === "not_verified") {
                    document.getElementById('modal-message').innerText = "Tài khoản chưa xác thực. Vui lòng kiểm tra email để xác thực tài khoản.";
                    modal.classList.remove('hidden');
                } else if (response === "lockAccount") {
                    document.getElementById('modal-message').innerText = "Tài khoản của bạn đã bị khóa. Quá nhiều yêu cầu. Vui lòng thử lại sau 1 phút.";
                    modal.classList.remove('hidden');
                } else if (response === "error") {
                    errorMessage.style.display = 'block';
                    errorMessage.innerHTML = 'Tên đăng nhập hoặc mật khẩu không đúng!';
                } else if (response === "locked") {
                    document.getElementById('modal-message').innerText = "Tài khoản đã bị khóa do quá nhiều lần nhập sai. Vui lòng thử lại sau 1 phút.";
                    modal.classList.remove('hidden');
                } else if (response === "index" || response === "admin/dashboard") {
                    window.location.href = response;
                } else {
                    alert('Lỗi không xác định, vui lòng thử lại.');
                }
            } else {
                console.error('Request failed with status:', xhr.status);
                errorMessage.style.display = 'block';
                errorMessage.innerHTML = 'Lỗi khi kết nối với server!';
            }

            loginButton.classList.remove('btn-loading');
            spinner.style.display = 'none';
        };

        xhr.send(params);
    });

    // Chức năng hiện/ẩn mật khẩu
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

    const togglePassword = document.querySelector('#togglePassword');
    const passwordInput = document.querySelector('#password');
    togglePasswordVisibility(togglePassword, passwordInput);
});

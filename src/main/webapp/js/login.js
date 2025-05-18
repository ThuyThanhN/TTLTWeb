document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('error-modal');
    const okButton = document.getElementById('ok-modal');
    const closeButton = document.getElementById('close-modal');
    const errorMessage = document.getElementById('error-message');
    const loginForm = document.getElementById('login-form');
    const loginButton = document.getElementById('login-button'); // Nút đăng nhập
    const spinner = document.createElement('div');
    spinner.classList.add('spinner');  // Thêm vòng xoay vào nút

    // Thêm spinner vào nút đăng nhập
    loginButton.appendChild(spinner);

    // Ẩn modal khi nhấn "OK"
    const hideModal = () => modal.classList.add('hidden');

    // Đóng modal khi nhấn nút "X"
    const closeModal = () => hideModal();

    // Xử lý khi nhấn nút OK
    if (okButton) {
        okButton.addEventListener('click', hideModal);
    }

    // Đóng modal khi nhấn nút "X"
    if (closeButton) {
        closeButton.addEventListener('click', closeModal);
    }

    // Xử lý sự kiện form submit qua AJAX
    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();  // Ngừng hành động gửi form mặc định

        // Lấy thông tin đăng nhập từ form
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Kiểm tra nếu username và password không rỗng
        if (!username || !password) {
            console.error('Username or password is empty');
            return;  // Dừng lại nếu username hoặc password trống
        }

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'login', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest'); // Đảm bảo header cho AJAX

        // Hiển thị vòng xoay và vô hiệu hóa nút
        loginButton.classList.add('btn-loading');
        spinner.style.display = 'block';  // Hiển thị vòng xoay

        xhr.onload = function() {
            // Xử lý phản hồi từ server
            if (xhr.status == 200) {
                const response = xhr.responseText;
                console.log('Server response:', response);

                // Kiểm tra nếu tài khoản chưa được xác thực
                if (response === "not_verified") {
                    document.getElementById('modal-message').innerText = "Tài khoản chưa xác thực. Vui lòng kiểm tra email để xác thực tài khoản.";
                    modal.classList.remove('hidden'); // Mở modal xác thực
                } else if (response === "lockAccount") {
                    // Hiển thị modal thông báo tài khoản bị khóa và yêu cầu kích hoạt lại
                    document.getElementById('modal-message').innerText = "Tài khoản của bạn đã bị khóa. Quá nhiều yêu cầu. Vui lòng thử lại sau 1 phút.";
                    modal.classList.remove('hidden'); // Mở modal xác thực
                } else if (response === "error") {
                    errorMessage.style.display = 'block';
                    errorMessage.innerHTML = 'Tên đăng nhập hoặc mật khẩu không đúng!';
                } else if (response === "locked") {
                    // Hiển thị modal cho người dùng bị khóa đăng nhập
                    document.getElementById('modal-message').innerText = "Tài khoản đã bị khóa do quá nhiều lần nhập sai. Vui lòng thử lại sau 1 phút.";
                    modal.classList.remove('hidden'); // Mở modal khóa tài khoản
                } else if (response === "index" || response === "admin/dashboard") {
                    window.location.href = response;  // Chuyển hướng thành công
                }
            } else {
                // Nếu yêu cầu không thành công
                console.error('Request failed with status:', xhr.status);
                errorMessage.style.display = 'block';
                errorMessage.innerHTML = 'Lỗi khi kết nối với server!';
            }

            // Ẩn vòng xoay và kích hoạt lại nút khi xong
            loginButton.classList.remove('btn-loading');
            spinner.style.display = 'none'; // Ẩn vòng xoay
        };

        // Gửi thông tin đăng nhập qua AJAX
        xhr.send('username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password));
    });

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
    togglePasswordVisibility(togglePassword, passwordInput)
});

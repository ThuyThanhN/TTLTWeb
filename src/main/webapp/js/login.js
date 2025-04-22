document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('error-modal');
    const okButton = document.getElementById('ok-modal');
    const closeButton = document.getElementById('close-modal');
    const errorMessage = document.getElementById('error-message');
    const loginForm = document.getElementById('login-form');

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

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'login', true);
        xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest'); // Đảm bảo header cho AJAX
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xhr.onload = function() {
            if (xhr.status == 200) {
                const response = xhr.responseText;
                console.log('Server response:', response);  // Kiểm tra phản hồi từ server

                // Kiểm tra nếu tài khoản chưa được xác thực
                if (response === "not_verified") {
                    // Hiển thị modal xác thực tài khoản
                    document.getElementById('modal-message').innerText = "Tài khoản chưa xác thực. Vui lòng kiểm tra email để xác thực tài khoản.";
                    modal.classList.remove('hidden'); // Mở modal
                } else if (response === "error") {
                    // Hiển thị thông báo lỗi đăng nhập
                    errorMessage.style.display = 'block';
                    errorMessage.innerHTML = 'Tên đăng nhập hoặc mật khẩu không đúng!';
                } else if (response === "index" || response === "admin/dashboard") {
                    // Chuyển hướng trang khi đăng nhập thành công
                    window.location.href = response;  // Chuyển hướng bằng JavaScript
                } else {
                    // Nếu không phải phản hồi dự kiến, thông báo lỗi chung
                    console.error('Unexpected server response:', response);
                }
            } else {
                // Xử lý nếu server trả về lỗi (status khác 200)
                console.error('Request failed with status:', xhr.status);
                errorMessage.style.display = 'block';
                errorMessage.innerHTML = 'Lỗi khi kết nối với server!';
            }
        };

        // Gửi thông tin đăng nhập qua AJAX
        xhr.send('username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password));
    });
});

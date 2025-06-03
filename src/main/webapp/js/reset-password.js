document.querySelector('.btn-edit').addEventListener('click', function(event) {
    const emailInput = document.getElementById('email');
    const errorMessage = document.getElementById('error-message');
    const submitButton = document.querySelector('.btn-edit');

    // Kiểm tra email có trống hay không
    if (emailInput.value.trim() === '') {
        event.preventDefault(); // Ngừng hành động mặc định
        errorMessage.textContent = 'Vui lòng nhập email khôi phục';
        errorMessage.style.display = 'block';
    } else {
        // Kiểm tra định dạng email hợp lệ
        const emailPattern = /^[a-zA-Z0-9]{5,}@[a-zA-Z0-9.]{5,}\.[a-zA-Z]{2,}$/i;
        if (!emailPattern.test(emailInput.value.trim())) {
            event.preventDefault(); // Ngừng hành động mặc định
            errorMessage.textContent = 'Email không đúng hoặc không tồn tại';
            errorMessage.style.display = 'block';
        } else {
            errorMessage.style.display = 'none'; // Ẩn thông báo lỗi

            // Thêm lớp 'loading' cho nút để kích hoạt hiệu ứng xoay và ẩn chữ
            submitButton.classList.add('loading');

            // Lưu email vào localStorage
            localStorage.setItem('resetEmail', emailInput.value.trim());

            // Giả sử gửi email và thực hiện hành động tiếp theo (thực tế có thể là gọi Ajax)
            setTimeout(() => {
                // Sau khi xử lý xong, xóa lớp loading và chuyển hướng
                submitButton.classList.remove('loading'); // Xóa hiệu ứng loading

                // Chuyển hướng sang trang xác thực OTP
                window.location.href = 'verify-reset-passwd';  // Bạn có thể thay bằng URL của servlet của mình
            }, 2000); // Giả lập việc xử lý trong 2 giây (thay bằng thực tế xử lý gửi mã OTP)
        }
    }
});

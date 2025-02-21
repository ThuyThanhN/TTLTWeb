document.querySelector('.btn-edit').addEventListener('click', function(event) {
    const emailInput = document.getElementById('email');
    const errorMessage = document.getElementById('error-message');
    
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

            // Lưu email vào localStorage
            localStorage.setItem('resetEmail', emailInput.value.trim());

            // Chuyển hướng sang trang verify-reset-passwd.html
            window.location.href = 'verify-reset-passwd.html';
        }
    }
});

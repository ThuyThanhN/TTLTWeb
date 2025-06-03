document.getElementById('resend-otp').addEventListener('click', function() {
    var button = this;

    // Đổi chữ thành "Đã Gửi"
    button.textContent = "Đã Gửi";

    // Vô hiệu hóa nút trong 60 giây
    button.disabled = true;

    var countdown = 60; // Số giây đếm ngược (60 giây)

    // Thực hiện đếm ngược
    var countdownInterval = setInterval(function() {
        countdown--;
        button.textContent = " (" + countdown + "s)"; // Cập nhật văn bản nút với thời gian còn lại

        // Khi đếm ngược đến 0, phục hồi lại trạng thái ban đầu
        if (countdown <= 0) {
            clearInterval(countdownInterval); // Dừng đếm ngược
            button.textContent = "Lấy Mã"; // Đổi lại chữ "Lấy Mã"
            button.disabled = false; // Kích hoạt lại nút
        }
    }, 1000); // Cập nhật mỗi giây (1000ms)
});

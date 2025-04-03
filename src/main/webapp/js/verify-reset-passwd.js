// Biến toàn cục để quản lý số lần nhập sai và thời gian khóa
let failedAttempts = 0;
let lockTime = 0;
const lockDuration = 60 * 1000; // Thời gian khóa 1 phút (60 giây)

// Kiểm tra xem có đang trong thời gian khóa không
function checkLock() {
    const currentTime = new Date().getTime();

    if (failedAttempts >= 3) {
        if (currentTime - lockTime < lockDuration) {
            const timeLeft = Math.ceil((lockDuration - (currentTime - lockTime)) / 1000);
            document.getElementById('lock-message').style.display = 'block';
            document.getElementById('lock-message').textContent = `Bạn đã nhập sai 3 lần. Vui lòng đợi ${timeLeft} giây để thử lại.`;
            return false; // Không cho phép nhập OTP nếu đang bị khóa
        } else {
            // Nếu hết thời gian khóa, reset lại và cho phép nhập lại
            failedAttempts = 0;
            lockTime = 0;
            document.getElementById('lock-message').style.display = 'none'; // Ẩn thông báo khóa
        }
    }
    return true; // Cho phép nhập OTP nếu không bị khóa
}
document.getElementById('resend-otp').addEventListener('click', function() {
    var button = this;

    // Đổi chữ thành "Đã Gửi"
    button.textContent = "Đã Gửi";

    // Vô hiệu hóa nút trong 60 giây
    button.disabled = true;

    // Sau 60 giây, phục hồi lại trạng thái ban đầu
    setTimeout(function() {
        button.textContent = "Lấy Mã"; // Đổi lại chữ "Lấy Mã"
        button.disabled = false; // Kích hoạt lại nút
    }, 60000); // 60000ms = 60 giây
});


// Kiểm tra OTP khi người dùng gửi form
document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();

    // Kiểm tra nếu đang bị khóa
    if (!checkLock()) return;

    const otp = document.getElementById('otp').value.trim();

    // Giả sử mã OTP đúng là '123456' (thay đổi theo mã OTP thực tế)
    const correctOtp = '123456';

    if (otp === correctOtp) {
        // Nếu đúng, reset số lần nhập sai
        failedAttempts = 0;
        alert('OTP đúng! Bạn đã xác thực thành công.');

        // Chuyển hướng người dùng đến trang updatePasswd sau khi OTP đúng
        window.location.href = 'updatePasswd'; // Thay 'updatePasswd' bằng URL đầy đủ nếu cần
    } else {
        // Nếu sai, tăng số lần nhập sai và lưu thời gian khóa
        failedAttempts++;

        if (failedAttempts >= 3) {
            lockTime = new Date().getTime(); // Lưu thời gian khóa khi nhập sai 3 lần
        }

        alert('OTP không đúng! Bạn còn ' + (3 - failedAttempts) + ' lần thử.');
    }
});

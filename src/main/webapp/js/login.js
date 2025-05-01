document.addEventListener('DOMContentLoaded', function () {
    // Đảm bảo modal chỉ được đóng khi các phần tử tồn tại
    const modal = document.querySelector('.modal');
    const okButton = document.getElementById('ok-modal');
    const closeButton = document.getElementById('close-modal');

    // Đóng modal khi nhấn nút OK
    if (okButton) {
        okButton.addEventListener('click', function() {
            modal.style.display = 'none';  // Ẩn modal khi nhấn OK
        });
    }

    // Đóng modal khi nhấn nút "X"
    if (closeButton) {
        closeButton.addEventListener('click', function() {
            modal.style.display = 'none';  // Ẩn modal khi nhấn "X"
        });
    }
});

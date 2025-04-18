// Tắt modal khi nhấn nút OK
document.querySelector('.btn-primary').addEventListener('click', function() {
    var modal = new bootstrap.Modal(document.querySelector('.modal'));
    modal.hide(); // Đóng modal khi nhấn OK
});

// Tắt modal khi nhấn nút "X" trên header modal
document.querySelector('.btn-close').addEventListener('click', function() {
    var modal = new bootstrap.Modal(document.querySelector('.modal'));
    modal.hide(); // Đóng modal khi nhấn "X"
});

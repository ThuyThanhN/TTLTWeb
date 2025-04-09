// Đảm bảo mã được thực thi sau khi DOM đã được tải xong
document.addEventListener('DOMContentLoaded', function () {
    // Lắng nghe sự kiện click trên nút chỉnh sửa
    const editButton = document.getElementById('edit-btn');
    const personalInfo = document.getElementById('personal-info');
    const editForm = document.getElementById('edit-form');

    if (editButton && personalInfo && editForm) {
        // Thêm sự kiện click để ẩn personal info và hiển thị edit form
        editButton.addEventListener('click', function () {
            personalInfo.style.display = 'none';
            editForm.style.display = 'block';
        });
    }
});

// Lấy email từ localStorage
const email = localStorage.getItem('resetEmail');

if (email) {
    // Hiển thị email đã được ẩn
    const maskedEmail = maskEmail(email);
    document.getElementById('masked-email').textContent = maskedEmail;
} else {
    alert('Không tìm thấy email! Vui lòng quay lại trang trước.');
    window.location.href = 'reset-password.html'; // Quay lại trang nhập email
}

// Hàm ẩn email (hiển thị dạng ph******37@gm********)
function maskEmail(email) {
    const [name, domain] = email.split('@');
    const maskedName = name.slice(0, 2) + '*'.repeat(name.length - 2); // Ẩn tên
    const [domainName, domainExt] = domain.split('.');
    const maskedDomain = domainName.slice(0, 2) + '*'.repeat(domainName.length - 2); // Ẩn phần domain
    return `${maskedName}@${maskedDomain}.${domainExt}`;
}



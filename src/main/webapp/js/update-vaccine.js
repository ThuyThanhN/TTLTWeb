const imagePreview = document.getElementById('image-preview');
const uploadInput = document.getElementById('upload');

function addImage(src) {
    imagePreview.innerHTML = '';
    const imgContainer = document.createElement('div');
    imgContainer.classList.add('img-container');

    const img = document.createElement('img');
    img.src = src;
    img.classList.add('img-fluid');
    img.style.maxWidth = '100px';

    // Icon X dung xoa anh da chon
    const deleteBtn = document.createElement('span');
    deleteBtn.classList.add('delete-btn');
    deleteBtn.innerHTML = `<i class="fa-solid fa-x"></i>`;

    deleteBtn.addEventListener('click', function () {
        imgContainer.remove();
        uploadInput.value = '';
    });

    imgContainer.appendChild(img);
    imgContainer.appendChild(deleteBtn);
    imagePreview.appendChild(imgContainer);
}

// Neu da co anh => hien thi
if (existingImageUrl) {
    addImage(existingImageUrl.startsWith("http") ? existingImageUrl : contextPath + "/" + existingImageUrl);
}

// Chon 1 anh moi
uploadInput.addEventListener('change', function (event) {
    const files = event.target.files;
    if (files.length > 0) {
        const reader = new FileReader();
        reader.onload = function (e) {
            addImage(e.target.result);
        };
        reader.readAsDataURL(files[0]);
    }
});

const editors = document.querySelectorAll('.ckeditor');

editors.forEach(editor => {
    ClassicEditor
        .create(editor)
        .catch(error => {
            console.error(error);
        });
});

let navItems = document.querySelectorAll('.nav-item');
let contents = document.querySelectorAll('.content');

navItems.forEach(navItem => {
    navItem.addEventListener('click', () => {

        // Bo active
        navItems.forEach(item => item.classList.remove('active'));

        // An noi dung
        contents.forEach(content => content.classList.add('d-none'));

        // Them active vao
        navItem.classList.add('active');

        // Hien thi noi dung tuong ung
        const target = document.querySelector(navItem.getAttribute('data-target'));
        if (target) {
            target.classList.remove('d-none');
        }
    });
});

$(document).ready(function () {
    // Xu ly chuc nang cap nhat vac xin
    $("#updateVaccine").submit(function (event) {
        event.preventDefault();

        let formData = new FormData(this);

        $.ajax({
            url: "/provide_vaccine_services_war/admin/updateVaccine",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    window.location.href = "/provide_vaccine_services_war/admin/table-data-vacxin";
                } else {
                    alert("Loi: " + response.message);
                }
            },
            error: function (xhr) {
                if (xhr.status === 403) {
                    const res = JSON.parse(xhr.responseText);
                    Swal.fire({
                        icon: 'warning',
                        title: 'Cảnh báo',
                        text: res.message || "Không có quyền thực hiện chức năng này!",
                        confirmButtonText: 'OK'
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Lỗi hệ thống!',
                        text: 'Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau.',
                        confirmButtonText: 'Đóng'
                    });
                }
            }
        });
    });
});


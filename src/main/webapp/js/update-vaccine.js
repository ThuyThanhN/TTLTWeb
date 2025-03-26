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
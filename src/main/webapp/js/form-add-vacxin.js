document.getElementById('upload').addEventListener('change', function(event) {
    const imagePreview = document.getElementById('image-preview');
    imagePreview.innerHTML = ''; // Clear any previous images
    const files = event.target.files;

    if (files.length > 0) {
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();

            reader.onload = function(e) {
                // Create a container for the image and delete button
                const imgContainer = document.createElement('div');
                imgContainer.classList.add('img-container');

                const img = document.createElement('img');
                img.src = e.target.result;
                img.classList.add('img-fluid');
                img.style.maxWidth = '100px';

                // Create a delete button
                const deleteBtn = document.createElement('span');
                deleteBtn.classList.add('delete-btn');
                deleteBtn.innerHTML = `<i class="fa-solid fa-x"></i>`;

                // Add event to delete the image
                deleteBtn.addEventListener('click', function() {
                    imgContainer.remove(); // Remove the image container
                });

                // Append the image and the delete button to the container
                imgContainer.appendChild(img);
                imgContainer.appendChild(deleteBtn);

                // Append the container to the preview area
                imagePreview.appendChild(imgContainer);
            };

            reader.readAsDataURL(file); // Read file as DataURL to display the image
        }
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
        // Bỏ active khỏi tất cả nav-item
        navItems.forEach(item => item.classList.remove('active'));

        // Ẩn tất cả nội dung
        contents.forEach(content => content.classList.add('d-none'));

        // Kích hoạt nav-item hiện tại
        navItem.classList.add('active');

        // Hiển thị nội dung tương ứng
        const target = document.querySelector(navItem.getAttribute('data-target'));
        if (target) {
            target.classList.remove('d-none');
        }
    });
});
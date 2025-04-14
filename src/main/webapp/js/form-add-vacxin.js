document.getElementById('upload').addEventListener('change', function (event) {
    const imagePreview = document.getElementById('image-preview');
    imagePreview.innerHTML = ''; // Clear any previous images
    const files = event.target.files;

    if (files.length > 0) {
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();

            reader.onload = function (e) {
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
                deleteBtn.addEventListener('click', function () {
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


$(document).ready(function () {
    function generateVaccineRowHtml(vaccineId, response) {
        return `
    <tr data-id="${vaccineId}">
        <td>${vaccineId}</td>
        <td>${response.name}</td>
        <td>
           <img src="${response.imageUrl}" alt="Vaccine Image" width="150" height="50">          
        </td>
        <td>${response.stockQuantity}</td>
        <td>
            <span class="status light-green color-green">${response.status}</span>
        </td>
        <td><f:formatNumber value="${response.price}" type="number" pattern="#,##0" />đ</td>
        <td>
            <a href="updateVaccine?id=${vaccineId}" 
               class="text-decoration-none edit-btn">
               <img src="../image/edit.png" alt="Sửa" width="22" height="22">
            </a>
            <a href="#" 
               class="text-decoration-none delete-btn" 
               data-bs-toggle="modal" 
               data-bs-target="#deleteVaccine" 
               data-id="${vaccineId}" 
               data-name="${response.name}">
               <img src="../image/bin.png" alt="Xóa" width="24" height="24">
            </a>
        </td>
    </tr>
`;
    }

    // Xu ly them vac xin
    function handleAddVaccine() {
        $("#addVaccine").submit(function (event) {
            event.preventDefault();

            let formData = new FormData(this);

            $.ajax({
                url: "/provide_vaccine_services_war/admin/addVaccine",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {
                        console.log("ID mới:", response.id);

                        // xoa noi dung input sau khi gui thanh cong
                        $("#addVaccine")[0].reset();

                        let newRowHtml = generateVaccineRowHtml(response.id, {
                            name: formData.get("vaccineName"),
                            imageUrl: "/uploads/" + formData.get("file").name,
                            stockQuantity: formData.get("quantityVaccine"),
                            status: formData.get("status"),
                            price: formData.get("price")
                        });

                        $("#vaccine").DataTable().row.add($(newRowHtml)).draw(false);

                        // chuyen huong den trang hien danh sach vac xin
                        window.location.href = "/provide_vaccine_services_war/admin/table-data-vacxin";
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
    }

    handleAddVaccine()
});



function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

document.querySelectorAll("[data-validate]").forEach(input => {
    input.addEventListener("input", function () {
        let isInvalid = /^[\s\d]/.test(this.value);
        this.classList.toggle("is-invalid", isInvalid);
        this.nextElementSibling.style.display = isInvalid ? "block" : "none";
    });
});

$(document).ready(function () {
    // bo focus khoi cac phan tu trong modal tranh loi aria-hidden
    $(document).on("click", "[data-bs-dismiss='modal']", function () {
        $(this).closest(".modal").find("button, input, textarea, select").blur();
    });

    // Ham khoi tao DataTable
    function initializeDataTable(selector) {
        let table = $(selector).DataTable({
            pagingType: "numbers",
            pageLength: 5,
            language: {
                emptyTable: "Không có dữ liệu",
                info: "Hiển thị _START_ đến _TOTAL_ mục",
                infoEmpty: "Hiển thị 0 đến 0 của 0 mục",
                infoFiltered: "(được lọc từ _MAX_ mục)",
                lengthMenu: "Hiển thị _MENU_ mục",
                loadingRecords: "Đang tải...",
                processing: "Đang xử lý...",
                search: "Tìm kiếm:",
                zeroRecords: "Không tìm thấy dữ liệu phù hợp"
            },
            buttons: [
                {
                    extend: "print",
                    title: "Danh sách Gói Vắc Xin",
                    exportOptions: {columns: [0, 1, 2]}
                },
                {
                    extend: "pdfHtml5",
                    title: "Danh sách Gói Vắc Xin",
                    exportOptions: {columns: [0, 1, 2]},
                    customize: function (doc) {
                        doc.content[1].table.widths = ["auto", "*", "auto"];
                    }
                },
                {
                    extend: "excelHtml5",
                    title: "Danh sách Gói Vắc Xin",
                    exportOptions: {columns: [0, 1, 2]}
                }
            ]
        });

        $("#print").on("click", function () {
            table.button(0).trigger();
        });

        $("#exportPDF").on("click", function () {
            table.button(1).trigger();
        });

        $("#exportExcel").on("click", function () {
            table.button(2).trigger();
        });

        return table;
    }

    const packageTable = initializeDataTable('#package');

    function generatePackageRowHtml(packageId, response) {
        return `
        <tr data-id="${packageId}">
            <td>${packageId}</td>
            <td>${response.name}</td>
            <<td>${response.totalPrice}đ</td>
            <td>
                <a href="#" 
                   class="text-decoration-none edit-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#editModal-${packageId}">
                   <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                </a>
                <a href="#" 
                   class="text-decoration-none delete-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#deleteStaff" 
                   data-id="${packageId}" 
                   data-name="${response.name}">
                   <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                </a>
            </td>
        </tr>
    `;
    }

    $("#addPMappingForm").submit(function (event) {
        event.preventDefault();

        const modal = document.querySelector('#addModal');
        updateTotalPrice(modal); // tinh lai tong gia goi
        const totalPriceStr = modal.querySelector('.totalPriceDisplay').value.replace('đ', '').replace(/\./g, '').replace(/,/g, '');
        const totalPrice = parseFloat(totalPriceStr);

        let name = $("#package-select").val();
        let age = $("#age-select").val();
        let description = $("#description-packag").val();

        let vaccineIds = [];
        let dosages = [];


        $(".list-packages .selected-vaccine").each(function () {
            let id = $(this).attr('data-id');
            let dosage = parseInt($(this).find('.dosage').val()) || 1

            vaccineIds.push(id);
            dosages.push(dosage);
        });

        console.log("Selected vaccine IDs:", vaccineIds);
        console.log("Dosages:", dosages);

        $.ajax({
            url: "/provide_vaccine_services_war/admin/addPMapping",
            type: "POST",
            data: {
                "package-name": name,
                "age-select": age,
                "description-name": description,
                "vaccineId": vaccineIds,
                "dosage": dosages,
                "totalPrice": totalPrice
            },
            traditional: true,
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    $("#addModal").find("button, input, textarea, select").blur();
                    $("#addModal").modal("hide");
                    $("#addPMappingForm")[0].reset();
                    $("#selected-vaccines").empty();
                    let responseData = {
                        name: name,
                        totalPrice: totalPrice
                    };
                    console.log("Response: ", responseData);
                    let newRowHtml = generatePackageRowHtml(response.id, responseData);
                    $("#package").DataTable().row.add($(newRowHtml)).draw(false);
                    setTimeout(() => location.reload(), 1000);
                } else {
                    alert("Có lỗi xảy ra: " + response.message);
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

    // Ham xu ly chuc nang cap nhat
    $(document).on("submit", ".editPackageForm", function (e) {
        e.preventDefault();

        const form = $(this);
        const modal = form.closest(".modal");
        const packageId = modal.find("input[name='id']").val();
        const name = modal.find("input[name='package-name']").val();
        const ageId = modal.find(".age-select").val();
        const description = modal.find(".description-name").val();

        let totalPriceStr = modal.find(".totalPriceDisplay").val()
            .replace('đ', '')
            .replace(/\./g, '')
            .replace(/,/g, '');
        let totalPrice = parseFloat(totalPriceStr);

        let vaccineIds = [];
        let dosages = [];

        $(".list-packages .selected-vaccine").each(function () {
            let id = $(this).attr('data-id');
            let dosage = parseInt($(this).find('.dosage').val()) || 1;

            vaccineIds.push(id);
            dosages.push(dosage);
        });

        $.ajax({
            url: `/provide_vaccine_services_war/admin/updatePackage`,
            type: "POST",
            traditional: true,
            dataType: "json",
            data: {
                id: packageId,
                "package-name": name,
                "ageId": ageId,
                "description-name": description,
                "totalPrice": totalPrice,
                "vaccineId": vaccineIds,
                "dosage": dosages
            },
            success: function (response) {
                if (response.status === "success") {
                    const bsModal = bootstrap.Modal.getInstance(modal[0]);
                    bsModal.hide();

                    let row = $(`#package tr[data-id='${packageId}']`);
                    row.find(".package-name").text(name);
                    row.find(".package-price").text(totalPrice.toLocaleString("vi-VN") + "đ");

                    $("#package").DataTable().row(row).invalidate().draw(false);
                } else {
                    alert("Cap nhat that bai!");
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

    function handleDeleteButton(modalId, removeUrlPrefix, table) {
        let deleteRow = null;
        let deleteId = null;

        $("#package").on("click", ".delete-btn", function (e) {
            e.preventDefault();

            deleteId = $(this).data("id");
            let itemName = $(this).data("name");
            deleteRow = table.row($(this).closest("tr"));

            let modalSelector = `#${modalId}`;
            document.querySelector(modalSelector).querySelector('.modal-body').textContent = `Bạn có chắc chắn muốn xóa ${itemName}?`;

            $(modalSelector).modal("show");
        });

        $("#confirmDelete").on("click", function () {
            if (!deleteId || !deleteRow) return;

            $.ajax({
                url: `./${removeUrlPrefix}`,
                type: "POST",
                data: {id: deleteId},
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {
                        deleteRow.remove().draw();
                        // xoa focus
                        $(`#${modalId}`).find("button, input, textarea, select").blur();
                        // an modal
                        const bsModal = bootstrap.Modal.getInstance(document.getElementById(modalId));
                        bsModal.hide();
                    } else {
                        alert("Xóa thất bại");
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

    handleDeleteButton('deleteModal', 'removePackage', packageTable);
});

// Lưu trữ số liều cho các vắc xin
const vaccineDosages = {};

function renderSelectedVaccines(modal) {
    if (!modal) return;

    const listPackages = modal.querySelector('.list-packages');
    const checkInput = modal.querySelectorAll('.vaccine-checkbox');
    const totalPriceDisplays = modal.querySelectorAll('.totalPriceDisplay'); // Lấy tất cả input tổng giá
    let totalPrice = 0; // Biến tổng giá
    const listVaccineDosageDb = modal.querySelectorAll('.vaccine-dosage');

    // lay du lieu tu db
    if (listVaccineDosageDb) {
        listVaccineDosageDb.forEach(input => {
            const id = input.getAttribute('data-id');
            const dosage = parseInt(input.getAttribute('data-value')) || 1;
            vaccineDosages[id] = dosage; // Lưu số liều
        });
    }
    // Lưu lại số liều hiện tại trước khi xóa danh sách
    listPackages.querySelectorAll('.selected-vaccine').forEach(div => {
        const id = div.getAttribute('data-id');
        const dosage = parseInt(div.querySelector('.dosage').value) || 1;
        vaccineDosages[id] = dosage; // Lưu số liều
    });
    // Xóa danh sách cũ
    listPackages.innerHTML = '';
    checkInput.forEach(input => {
        if (input.checked) {
            console.log("vaccineDosages", vaccineDosages);
            const name = input.dataset.name;
            const price = parseFloat(input.dataset.price); // Chuyển thành số
            const id = input.dataset.id;

            // Tạo dòng hiển thị
            let div = document.createElement('div');
            let spName = document.createElement('span');
            let spPrice = document.createElement('span');
            let spDosage = document.createElement('span');
            let remove = document.createElement('span');

            div.classList.add('selected-vaccine');
            spName.textContent = name;
            spPrice.classList.add('vaccine-price');
            spPrice.textContent = price + 'đ';

            // Khôi phục số liều đã lưu hoặc đặt mặc định là 1
            const savedDosage = vaccineDosages[id] || 1;
            console.log("save dosage:" + savedDosage);
            spDosage.innerHTML = `<input type="number" value="${savedDosage}" min="1" class="dosage" name="dosage" style="width: 50px;">`;
            // Gán giá trị cho input sau khi nó đã được tạo
            const dosageInput1 = spDosage.querySelector('.dosage'); // Lấy phần tử input đã tạo
            dosageInput1.value = savedDosage; // Gán giá trị đã lưu
            remove.innerHTML = `<i class="fa-solid fa-xmark"></i>`;

            div.setAttribute('data-id', id);
            div.appendChild(spName);
            div.appendChild(spPrice);
            div.appendChild(spDosage);
            div.appendChild(remove);

            // Xử lý sự kiện xóa
            remove.addEventListener('click', () => {
                input.checked = false; // Bỏ chọn checkbox
                div.remove();          // Xóa dòng khỏi danh sách
                delete vaccineDosages[id]; // Xóa thông tin số liều
                renderSelectedVaccines(modal); // Cập nhật lại danh sách
            });

            // Xử lý sự kiện thay đổi số lượng liều
            const dosageInput = spDosage.querySelector('.dosage');
            dosageInput.addEventListener('input', () => {
                const dosage = parseInt(dosageInput.value) || 1; // Kiểm tra giá trị nhập
                vaccineDosages[id] = dosage; // Cập nhật số liều trong đối tượng lưu trữ
                console.log("số liều: " + dosage);
                updateTotalPrice(modal); // Cập nhật lại tổng giá sau khi thay đổi số lượng
            });

            listPackages.appendChild(div);

            // Tính tổng giá (bao gồm số liều)
            totalPrice += price * savedDosage;
        }
    });

    // Cập nhật tất cả các input tổng giá trong modal
    totalPriceDisplays.forEach(display => {
        display.value = totalPrice + 'đ'; // Cập nhật giá trị trong input
    });
}

// Hàm cập nhật lại tổng giá
function updateTotalPrice(modal) {
    if (!modal) return;

    let totalPrice = 0;
    const selectedVaccines = modal.querySelectorAll('.selected-vaccine');
    selectedVaccines.forEach(div => {
        const priceText = div.querySelector('.vaccine-price').textContent.replace('đ', '').replace(/\./g, '').replace(/,/g, '');
        const price = parseFloat(priceText); // Chuyển đổi sang số sau khi loại bỏ dấu phân cách
        const dosage = parseInt(div.querySelector('.dosage').value) || 1; // Lấy số lượng liều, mặc định là 1
        totalPrice += price * dosage; // Tính tổng giá
    });
    const totalPriceDisplays = modal.querySelectorAll('.totalPriceDisplay');
    totalPriceDisplays.forEach(display => {
        display.value = totalPrice + 'đ';
    });
}

// Gắn sự kiện tự động sau khi DOM tải
document.querySelectorAll('.vaccine-checkbox').forEach(input => {
    input.addEventListener('click', (event) => {
        // fix lỗi arial-hidden
        event.currentTarget.blur();
        const modal = event.target.closest('.modal');
        renderSelectedVaccines(modal);
    });
});

// Khi mở modal sửa, hiển thị danh sách từ dữ liệu đã có
document.querySelectorAll('.modal').forEach(modal => {
    modal.addEventListener('shown.bs.modal', () => {
        renderSelectedVaccines(modal);
        updateTotalPrice(modal); // Cập nhật lại tổng giá ngay khi modal mở
    });
});


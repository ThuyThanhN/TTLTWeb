function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

$(document).ready(function () {
    // Hàm khởi tạo DataTable
    function initializeDataTable(selector) {
        $(selector).DataTable({
            "pagingType": "numbers",
            "pageLength": 5,
            "language": {
                "emptyTable": "Không có dữ liệu",
                "info": "Hiển thị _START_ đến _TOTAL_ mục",
                "infoEmpty": "Hiển thị 0 đến 0 của 0 mục",
                "infoFiltered": "(được lọc từ _MAX_ mục)",
                "lengthMenu": "Hiển thị _MENU_ mục",
                "loadingRecords": "Đang tải...",
                "processing": "Đang xử lý...",
                "search": "Tìm kiếm:",
                "zeroRecords": "Không tìm thấy dữ liệu phù hợp"
            }
        });
    }

    function handleDeleteButton(modalId, removeUrlPrefix) {
        $("#package").on("click", ".delete-btn", function (e) {
            e.currentTarget.blur();
            let removeUrl = `${removeUrlPrefix}`;
            let modalSelector = `${modalId}`;
            e.preventDefault();
            var itemId = $(this).data("id");
            var itemName = $(this).data("name");
            // alert(id);
            console.log("id-name", "vvnvnv" + modalSelector + itemId + itemName);
            var modal = document.getElementById(modalSelector);
            modal.querySelector('.modal-body').textContent = 'Bạn có chắc chắn muốn xóa ' + itemName + '?';

            // Cập nhật link nút xác nhận
            var confirmDeleteButton = modal.querySelector('#confirmDelete');
            confirmDeleteButton.setAttribute('href', removeUrl + '?id=' + itemId);
        });
    }

    initializeDataTable('#package');
    handleDeleteButton('deleteModal', 'removePackage');
});

// Lưu trữ số liều cho các vắc xin
const vaccineDosages = {};

function renderSelectedVaccines(modal) {
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
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.vaccine-checkbox').forEach(input => {
        input.addEventListener('click', (event) => {
            // fix lỗi arial-hidden
            event.currentTarget.blur();
            const modal = event.target.closest('.modal');
            renderSelectedVaccines(modal);
        });
    });
});

// Khi mở modal sửa, hiển thị danh sách từ dữ liệu đã có
document.querySelectorAll('.modal').forEach(modal => {
    modal.addEventListener('shown.bs.modal', () => {
        renderSelectedVaccines(modal);
        updateTotalPrice(modal); // Cập nhật lại tổng giá ngay khi modal mở
    });
});


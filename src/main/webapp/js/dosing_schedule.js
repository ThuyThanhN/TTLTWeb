const form = document.getElementById('myForm');
const submit = document.querySelector('#btn-submit');
const requiredFields = document.querySelectorAll('.required-field');
const phoneField = document.getElementById('contact_phone');
const genderOptions = document.querySelectorAll('input[name="gender"]');
const vaccineTypeOptions = document.querySelectorAll('input[name="vaccineType"]');
const errorGender = document.querySelector('.error-gender');
const errorVaccine = document.querySelector('.error-vaccine');
const commitmentCheckbox = document.getElementById('commitment');


submit.addEventListener('click', (event) => {
    event.preventDefault();
    let hasError = false;

    requiredFields.forEach(field => {
        if (!validateField(field)) hasError = true;
    });

    if (!validatePhone()) hasError = true;
    if (!validateGender()) hasError = true;
    if (!validateSelectedVaccines()) hasError = true;
    if (!validateTime()) hasError = true;
    if (!commitmentCheckbox.checked) hasError = true;

    if (!hasError) {
        form.submit();
    }
});

// Hàm kiểm tra các trường bắt buộc và validate
const validateField = (field) => {
    const errorElement = document.getElementById(`${field.id}-error`);
    const isValid = field.value.trim() !== '';
    errorElement.classList.toggle('hidden-field', isValid);
    return isValid;
};

// Kiểm tra định dạng số điện thoại
const validatePhone = () => {
    const value = phoneField.value.trim();
    const phoneError = document.querySelector('.error-phone');
    const phoneRegex = /^\d{10}$/;

    if (value === '') {
        phoneError.classList.add('hidden-field');
        return true;
    }

    phoneError.classList.toggle('hidden-field', phoneRegex.test(value));
    return phoneRegex.test(value);
};

// Kiểm tra chọn giới tính hay chưa
const validateGender = () => {
    const isSelected = document.querySelector('input[name="gender"]:checked') !== null;
    errorGender.classList.toggle('hidden-field', isSelected);
    return isSelected;
};

// Kiểm tra chọn vắc xin hay chưa
const validateSelectedVaccines = () => {
    const vaccineTypeSelected = document.querySelector('input[name="vaccineType"]:checked') !== null;
    errorVaccine.classList.toggle('hidden-field', vaccineTypeSelected);
    return vaccineTypeSelected;
};

// Khi nhập thì không hiện lỗi và ngược lại
requiredFields.forEach(field => {
    field.addEventListener('input', () => validateField(field));
});

genderOptions.forEach(option =>
    option.addEventListener('change', () => errorGender.classList.add('hidden-field'))
);

vaccineTypeOptions.forEach(option =>
    option.addEventListener('change', validateSelectedVaccines)
);

phoneField.addEventListener('input', validatePhone);

// Tìm các phần tử cần sử dụng
const timeOptions = document.querySelectorAll(".time-option");
const hiddenInput = document.getElementById("vaccination_time");
const timeButton = document.getElementById("timeButton");
const errorVaccineTime = document.getElementById("vaccination_time-error");

// Kiểm tra giá trị mặc định (nếu có) và hiển thị nó lên nút
const defaultTime = hiddenInput.value;
if (defaultTime) {
    timeButton.textContent = defaultTime; // Hiển thị giá trị mặc định trên nút
}

// Thêm sự kiện click cho từng lựa chọn giờ
timeOptions.forEach(option => {
    option.addEventListener("click", () => {
        // Cập nhật giá trị vào input ẩn
        hiddenInput.value = option.innerText.trim();  // Dùng innerText thay vì textContent

        // Cập nhật giá trị hiển thị trên nút
        timeButton.textContent = option.innerText.trim();  // Cập nhật giá trị hiển thị trên nút

        // Ẩn modal
        const timeModal = bootstrap.Modal.getInstance(document.getElementById("timeModal"));
        timeModal.hide();

        // Kiểm tra giờ tiêm
        validateTime();
    });
});

// Hàm kiểm tra giờ tiêm
const validateTime = () => {
    const isTimeSelected = hiddenInput.value.trim() !== "";
    errorVaccineTime.classList.toggle("hidden-field", isTimeSelected);
    return isTimeSelected;
};

// Hiển thị loại vắc xin gói và lẻ
const vaccineTypeButtons = document.querySelectorAll(".vaccine-type-btn");
const vaccineContentItems = document.querySelectorAll(".vaccine-type-content");

vaccineTypeButtons.forEach((type, index) => {
    type.addEventListener("change", () => {
        // Ẩn tất cả các vaccineContentItems và chỉ hiển thị mục hiện tại
        vaccineContentItems.forEach(item => item.classList.add('hidden-vx'));
        vaccineContentItems[index].classList.remove('hidden-vx');

        // Bỏ chọn tất cả các checkx button trong vaccine-type-content
        document.querySelectorAll('.vaccine-type-content input[type="checkbox"]').forEach(checkbox => {
            checkbox.checked = false;
        });
    });
});

// Gắn image down-arrow và arrow-up
const vaccineTypeItems = document.querySelectorAll(".vaccine-type-content-item");
vaccineTypeItems.forEach((item) => {
    item.addEventListener("click", () => {
        const vaccinePackageList = item.nextElementSibling;

        if (vaccinePackageList) {
            vaccinePackageList.classList.toggle("hidden-vx");

            const img = item.querySelector("img");
            if (img) {
                img.src = vaccinePackageList.classList.contains("hidden-vx") ? "image/down-arrow.png" : "image/arrow-up.png";
            }
        }
    });
});

// Xử lý sự kiện chọn radio button
const radioButtons = document.querySelectorAll('input[type="radio"]');
radioButtons.forEach(button => {
    button.addEventListener('change', function () {
        const group = this.closest('.btn-group');
        const labels = group.querySelectorAll('label');
        labels.forEach(label => {
            label.classList.remove('selected');
        });

        const selectedLabel = this.nextElementSibling;
        selectedLabel.classList.add('selected');
    });
});

const preferredDateInput = document.getElementById('preferred_date');

preferredDateInput.addEventListener('change', function () {
    const selectedDate = new Date(this.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0); // Xóa giờ phút giây để so sánh chính xác

    if (selectedDate < today) {
        Swal.fire({
            icon: 'warning',
            text: 'Ngày hẹn tiêm không được nhỏ hơn ngày hôm nay!',
            confirmButtonText: 'OK'
        });

        // Reset lại ô nhập ngày
        this.value = '';
    }
});


$(document).ready(function() {
    console.log($('.form-check-input[name="selectedpackagevaccine"]').length);

    // xử lý vaccine gói
    // tìm input có id có package
    $('.form-check-input[name="selectedPackages"]').each(function() {

        let isValid = $(this).data('valid');
        console.log(isValid);

        const checkbox = $(this);
        const packageDiv = checkbox.closest('.vaccine-package');

        let messageEl = checkbox.closest('.form-check').find('.out-of-stock-message');
        if (messageEl.length === 0) {
            messageEl = $('<div class="out-of-stock-message text-danger small mt-1"></div>')
                .appendTo(checkbox.closest('.form-check'));
        }

        if (!isValid) {
            checkbox.prop('disabled', true);
            packageDiv.addClass('disabled-package');
            messageEl.text('Hết hàng').show();
        } else {
            checkbox.prop('disabled', false);
            packageDiv.removeClass('disabled-package');
            messageEl.text('').hide();
        }
    });


    $('.form-check-input[name="selectedVaccines"]').each(function() {
        const quantity = parseInt($(this).data('quantity'), 10);
        const checkbox = $(this);
        const packageDiv = checkbox.closest('.vaccine-package');


        let messageEl = checkbox.closest('.form-check').find('.out-of-stock-message');
        if (messageEl.length === 0) {
            messageEl = $('<div class="out-of-stock-message text-danger small mt-1"></div>')
                .appendTo(checkbox.closest('.form-check'));
        }


        // nếu số lượng ít hơn 0 thì sẽ disable và làm mờ
        if (quantity <= 0) {
            checkbox.prop('disabled', true);
            packageDiv.addClass('disabled-package');
            messageEl.text('Hết hàng').show();
        } else {
            checkbox.prop('disabled', false);
            packageDiv.removeClass('disabled-package');
            messageEl.text('').hide();
        }
    });



});

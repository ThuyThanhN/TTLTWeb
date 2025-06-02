function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

document.querySelectorAll("[data-email]").forEach(emailInput => {
    emailInput.addEventListener("input", function () {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        const valid = emailPattern.test(this.value);
        emailInput.classList.toggle("is-invalid", !valid);
        this.nextElementSibling.style.display = !valid ? "block" : "none";
    });
});

document.querySelectorAll("[data-phone]").forEach(phoneInput => {
    phoneInput.addEventListener("input", function () {
        const valid = /^\d{10,11}$/.test(this.value);
        phoneInput.classList.toggle("is-invalid", !valid);
        this.nextElementSibling.style.display = !valid ? "block" : "none";
    });
});

document.querySelectorAll("[data-password]").forEach(pass => {
    pass.addEventListener("input", function () {
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        const valid = passwordPattern.test(this.value);
        pass.classList.toggle("is-invalid", !valid);
        this.nextElementSibling.style.display = !valid ? "block" : "none";
    });
});

$(document).ready(function () {
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
                    title: "Danh sách Nhân Viên",
                    exportOptions: {columns: [0, 1, 2, 3, 4, 5]}
                },
                {
                    extend: "pdfHtml5",
                    title: "Danh sách Nhân Viên",
                    exportOptions: {columns: [0, 1, 2, 3, 4, 5]},
                    customize: function (doc) {
                        doc.content[1].table.widths = ["auto", "*", "*", "*", "*", "*"];
                    }
                },
                {
                    extend: "excelHtml5",
                    title: "Danh sách Nhân Viên",
                    exportOptions: {columns: [0, 1, 2, 3, 4, 5]}
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

    function generateEditModalHtml(id, fullname, gender, ident, date, email, address, province, district, ward, provinceCode, districtCode, wardCode, phone, role, password, module) {
        return `
            <div class="modal fade" id="editStaff-${id}" data-bs-backdrop="static" 
                data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="editLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                       <div class="modal-header">
                            <h5 class="modal-title" id="editLabel">Cập nhật nhân viên</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                       </div>
                       <div class="modal-body">
                          <form class="editStaffForm" method="post">
                             <input type="hidden" name="id" value="${id}">
                             <div class="row">
                                  <div class="col-4">
                                      <div class="mb-3">
                                         <label for="fullname-${id}" class="form-label">Họ và tên</label>
                                         <input type="text" class="form-control" id="fullname-${id}" name="fullname" value="${fullname}" required>
                                      </div>
                                  </div>
                                  <div class="col-4">
                                      <div class="mb-3 position-relative">
                                          <label for="gender${id}" class="form-label">Giới tính</label>
                                          <select class="form-select" id="gender${id}" name="gender">
                                              <option value="" selected>--Chọn giới tính--</option>
                                              <option value="Nam" ${gender === 'Nam' ? 'selected' : ''}>Nam</option>
                                              <option value="Nữ" ${gender === 'Nữ' ? 'selected' : ''}>Nữ</option>
                                              <option value="Khác" ${gender === 'Khác' ? 'selected' : ''}>Khác</option>
                                          </select>
                                          <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                      </div>
                                  </div>
                                  <div class="col-4">
                                      <div class="mb-3">
                                          <label for="ident-${id}" class="form-label">Mã định danh </label>
                                          <input type="text" class="form-control" id="ident-${id}" name="ident" value="${ident}">
                                      </div>
                                  </div>
                                  <div class="col-4">
                                       <div class="mb-3">
                                           <label for="date-${id}" class="form-label">Ngày sinh </label> <br>
                                           <input type="date" class="form-control" name="date" id="date-${id}" value="${toInputDateFormat(date)}">
                                       </div>
                                  </div>
                                  <div class="col-4">
                                       <div class="mb-3">
                                            <label for="email-${id}" class="form-label">Email </label> <br>
                                            <input type="email" class="form-control" name="email" id="email-${id}" value="${email}">
                                       </div>
                                  </div>
                                  <div class="col-12">
                                       <div class="mb-3">
                                            <label for="address-${id}" class="form-label">Địa chỉ</label>
                                            <input type="text" class="form-control" id="address-${id}" value="${address}" name="address" maxlength="80" required>
                                       </div>
                                  </div>
                                  <div class="col-4">
                                      <div class="mb-3">
                                           <label class="form-label">Tỉnh thành</label>
                                            <div class="dropdown">
                                                <input type="text" placeholder="Chọn tỉnh thành" name="province"
                                                       value="${province}" class="form-control dropdown-toggle province-select"
                                                       id="province-select-${id}" data-code="${provinceCode}" data-bs-toggle="dropdown" aria-expanded="false">
                                                <i class="fa-solid fa-angle-down"></i>
                                                <ul class="dropdown-menu province-menu"></ul>
                                            </div>
                                      </div>
                                  </div>
                                  <div class="col-4">
                                       <div class="mb-3">
                                          <label class="form-label">Quận huyện</label>
                                          <div class="dropdown">
                                              <input type="text" placeholder="Chọn quận huyện" name="district"
                                                      value="${district}"
                                                      class="form-control dropdown-toggle district-select" id="district-select-${id}"
                                                      data-code="${districtCode}" data-bs-toggle="dropdown" aria-expanded="false">
                                              <ul class="dropdown-menu district-menu"></ul>
                                          </div>
                                       </div>
                                  </div>
                                  <div class="col-4">
                                        <label class="form-label">Phường xã</label>
                                        <div class="dropdown">
                                            <input type="text" placeholder="Chọn phường xã" name="ward"  value="${ward}"
                                                    class="form-control dropdown-toggle ward-select" id="ward-select-${id}"
                                                    data-code="${wardCode}" data-bs-toggle="dropdown" aria-expanded="false">
                                            <ul class="dropdown-menu ward-menu"></ul>
                                        </div>
                                  </div>
                                  <div class="col-3">
                                        <div class="mb-3">
                                             <label for="phone-${id}" class="form-label">Số điện thoại</label>
                                             <input type="tel" class="form-control" id="phone-${id}" name="phone" value="${phone}" required>
                                        </div>
                                  </div>
                                  <div class="col-3">
                                        <div class="mb-3 position-relative">
                                               <label for="role-${id}" class="form-label">Chức vụ</label>
                                               <select class="form-select" id="role-${id}" name="role" required>
                                                    <option value="" selected>--Chọn chức vụ--</option>
                                                    <option value="1" ${role == 1 ? 'selected' : ''}>Admin</option>
                                                    <option value="2" ${role == 2 ? 'selected' : ''}>Nhân viên</option>
                                               </select>
                                               <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                        </div>
                                  </div>
                                  <div class="col-3">
                                        <div class="mb-3 position-relative">
                                                <label for="module-${id}" class="form-label">Phân quyền:</label>
                                                <select class="form-select" id="module-${id}" name="module" required>
                                                     <option value="" selected>-- Chọn --</option>
                                                     <option value="none" ${module == 'none' ? 'selected' : ''}>Nhân viên</option>
                                                     <option value="staff" ${module == 'staff' ? 'selected' : ''}>Quản lý nhân viên</option>
                                                     <option value="customer" ${module == 'customer' ? 'selected' : ''}>Quản lý khách hàng</option>
                                                     <option value="order" ${module == 'order' ? 'selected' : ''}>Quản lý đơn hàng</option>
                                                     <option value="vaccine" ${module == 'vaccine' ? 'selected' : ''}>Quản lý vắc xin</option>
                                                     <option value="package" ${module == 'package' ? 'selected' : ''}>Quản lý gói vắc xin</option>
                                                     <option value="supplier" ${module == 'supplier' ? 'selected' : ''}>Quản lý nhà cung cấp</option>
                                                     <option value="center" ${module == 'center' ? 'selected' : ''}>Quản lý trung tâm</option>
                                                     <option value="log" ${module == 'log' ? 'selected' : ''}>Quản lý log</option>
                                                     <option value="transaction" ${module == 'transaction' ? 'selected' : ''}>Quản lý giao dịch</option>
                                                     <option value="warehouse" ${module == 'warehouse' ? 'selected' : ''}>Quản lý kho hàng</option>
                                                </select>
                                                <i class="fa-solid fa-angle-down position-absolute end-0 translate-middle" style="top: 72%"></i>
                                        </div>
                                  </div>
                                  <div class="col-3">
                                         <div class="mb-3">
                                                <label for="pass-${id}" class="form-label">Mật khẩu</label>
                                                <input type="password" class="form-control" id="pass-${id}" name="password" value="${password}" required>
                                         </div>
                                  </div>
                             </div>
                             <div class="modal-footer">
                                 <button type="submit" class="btn btn-save">Lưu lại</button>
                                 <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy bỏ</button>
                             </div>
                          </form>
                       </div>
                    </div>
                </div>
           </div>`;
    }

    function generateStaffRowHtml(staffId, response) {
        return `
        <tr data-id="${staffId}">
            <td>${staffId}</td>
            <td>${response.fullname}</td>
            <td>${response.address}, ${response.ward}, ${response.district}, ${response.province}</td>
            <td>${response.gender}</td>
            <td>${formatDate(response.date)}</td>
            <td>${response.phone}</td>
            <td>
                <a href="#" 
                   class="text-decoration-none edit-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#editStaff-${staffId}">
                   <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                </a>
                <a href="#" 
                   class="text-decoration-none delete-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#deleteStaff" 
                   data-id="${staffId}" 
                   data-name="${response.fullname}">
                   <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                </a>
            </td>
        </tr>
    `;
    }

    function formatDate(dateString) {
        if (!dateString) return "";
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // tháng bắt đầu từ 0
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
    }

    function toInputDateFormat(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        if (isNaN(date)) return '';
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    $("#addStaffForm").submit(function (event) {
        event.preventDefault();

        let fullname = $("#fullname").val();
        let gender = $("#gender").val();
        let ident = $("#ident").val();
        let date = $("#date").val();
        let email = $("#email").val();
        let address = $("#staff-address").val();
        let province = $("#province").val();
        let district = $("#district").val();
        let ward = $("#ward").val();
        let phone = $("#staff-phone").val();
        let role = parseInt($("#role").val());
        let module = $("#module").val();
        let password = $("#password").val();

        let provinceCode = $("#province").attr("data-code");
        let districtCode = $("#district").attr("data-code");
        let wardCode = $("#ward").attr("data-code");
        console.log({
            fullname, address, province, district, ward, phone, role
        });
        console.log("Module:", module);

        $.ajax({
            url: "/admin/addStaff",
            type: "POST",
            data: {
                "fullname": fullname,
                "gender": gender,
                "ident": ident,
                "date": date,
                "email": email,
                "address": address,
                "province": province,
                "district": district,
                "ward": ward,
                "phone": phone,
                "role": role,
                "module": module,
                "password": password
            },
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    console.log("ID moi:", response.id);
                    // Xóa focus các thành phần bên trong modal trước khi ẩn
                    $("#addModal").find("button, input, textarea, select").blur();
                    // an modal
                    $("#addModal").modal("hide");
                    // xoa nd input
                    $("#addStaffForm")[0].reset();

                    // them modal moi vao
                    $("body").append(generateEditModalHtml(
                        response.id, fullname, gender, ident, date, email, address,
                        province, district, ward, provinceCode, districtCode, wardCode,
                        phone, role, password, module
                    ));

                    let responseData = {
                        fullname: fullname,
                        address: address,
                        province: province,
                        district: district,
                        ward: ward,
                        date: date,
                        gender: gender,
                        phone: phone
                    };

                    console.log("Response: ", responseData);

                    let newRowHtml = generateStaffRowHtml(response.id, responseData);
                    $("#staff").DataTable().row.add($(newRowHtml)).draw(false);
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

    $(document).on("submit", ".editStaffForm", function (e) {
        e.preventDefault(); // ngăn hành động mặc định

        let modal = $(this).closest(".modal");
        let staffId = modal.find("input[name='id']").val();
        let fullname = modal.find("input[name='fullname']").val();
        let gender = modal.find("select[name='gender']").val();
        let ident = modal.find("input[name='ident']").val();
        let date = modal.find("input[name='date']").val();
        let email = modal.find("input[name='email']").val();
        let address = modal.find("input[name='address']").val();
        let province = modal.find("input[name='province']").val();
        let district = modal.find("input[name='district']").val();
        let ward = modal.find("input[name='ward']").val();
        let phone = modal.find("input[name='phone']").val();
        let role = modal.find("select[name='role']").val();
        let password = modal.find("input[name='password']").val();
        let module = modal.find("select[name='module']").val();

        let provinceCode = modal.find("input[name='province']").attr("data-code");
        let districtCode = modal.find("input[name='district']").attr("data-code");
        let wardCode = modal.find("input[name='ward']").attr("data-code");

        console.log("Module:", module);
        console.log("Date:", date);
        $.ajax({
            url: "/admin/updateStaff",
            type: "POST",
            data: {
                id: staffId,
                "fullname": fullname,
                "gender": gender,
                "ident": ident,
                "date": date,
                "email": email,
                "address": address,
                "province": province,
                "district": district,
                "ward": ward,
                "phone": phone,
                "role": role,
                "password": password,
                "module": module,
            },
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    // xoa modal cu
                    $(`#editStaff-${staffId}`).remove();
                    // them modal moi
                    $("body").append(generateEditModalHtml(staffId, response.fullname, response.gender, response.ident, response.date, response.email,
                        response.address, response.province, response.district, response.ward, response.provinceCode,
                        response.districtCode, response.wardCode, response.phone, response.role, response.password, response.module));
                    // cap nhat input
                    let newRowHtml = generateStaffRowHtml(staffId, response);
                    let table = $("#staff").DataTable();
                    let row = $(`#staff tr[data-id='${staffId}']`);
                    table.row(row).remove();
                    table.row.add($(newRowHtml)).draw(false);
                    modal.find("button, input, textarea, select").blur();
                    const bsModal = bootstrap.Modal.getInstance(modal[0]);
                    bsModal.hide();
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

    // bo focus khoi cac phan tu trong modal tranh loi aria-hidden
    $(document).on("click", "[data-bs-dismiss='modal']", function () {
        $(this).closest(".modal").find("button, input, textarea, select").blur();
    });

    // Ham xu ly chuc nang xoa
    function handleDeleteButton(modalId, removeUrlPrefix, table) {
        let deleteRow = null;
        let deleteId = null;

        $("#staff").on("click", ".delete-btn", function (e) {
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

    const staffTable = initializeDataTable("#staff");
    handleDeleteButton('deleteStaff', 'removeStaff', staffTable);
});
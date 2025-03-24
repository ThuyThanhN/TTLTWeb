// Xu ly ten trung tam khi co khoang trang + bat dau bang so
document.querySelectorAll("[data-validate]").forEach(input => {
    input.addEventListener("input", function () {
        let isInvalid = /^[\s\d]/.test(this.value);
        this.classList.toggle("is-invalid", isInvalid);
        this.nextElementSibling.style.display = isInvalid ? "block" : "none";
    });
});

// Xu ly so dien thoai gom 10 hoac 11 so
document.querySelectorAll("[data-phone]").forEach(phoneInput => {
    phoneInput.addEventListener("input", function () {
        const valid = /^\d{10,11}$/.test(this.value);
        phoneInput.classList.toggle("is-invalid", !valid);
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
                    title: "Danh sách Trung Tâm",
                    exportOptions: {columns: [0, 1, 2, 3]}
                },
                {
                    extend: "pdfHtml5",
                    title: "Danh sách Trung Tâm",
                    exportOptions: {columns: [0, 1, 2, 3]},
                    customize: function (doc) {
                        doc.content[1].table.widths = ["auto", "*", "*", "*"];
                    }
                },
                {
                    extend: "excelHtml5",
                    title: "Danh sách Trung Tâm",
                    exportOptions: {columns: [0, 1, 2, 3]}
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

    // modal chinh sua
    function generateEditModalHtml(id, name, address, province, district, ward, phone, provinceCode, districtCode, wardCode) {
        return `
            <div class="modal fade" id="editCenterModal-${id}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editCenterModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="editCenterModalLabel">Cập nhật trung tâm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form class="editCenterForm" method="post">
                      <input type="hidden" name="id" value="${id}">
                      <input type="hidden" class="province-code" name="province-code" value="${provinceCode}">
                      <input type="hidden" class="district-code" name="district-code" value="${districtCode}">
                      <input type="hidden" class="ward-code" name="ward-code" value="${wardCode}">
                      
                      <div class="row">
                        <div class="col-12">
                          <div class="mb-3">
                            <label for="center-name-${id}" class="form-label">Nhập tên trung tâm</label>
                            <input type="text" class="form-control" id="center-name-${id}" name="center-name" value="${name}" maxlength="80" required>
                          </div>
                        </div>
                        <div class="col-12">
                          <div class="mb-3">
                            <label for="center-address-${id}" class="form-label">Địa chỉ:</label>
                            <input type="text" class="form-control" id="center-address-${id}" name="center-address" value="${address}" maxlength="80" required>
                          </div>
                        </div>
                        <div class="col-6">
                           <div class="mb-3">
                               <label class="form-label">Tỉnh thành</label>
                               <div class="dropdown">
                                  <input type="text" placeholder="Chọn tỉnh thành" name="center-province"
                                               value="${province}"
                                               class="form-control dropdown-toggle province-select" id="province-select-${id}"
                                               data-code="${provinceCode}" data-bs-toggle="dropdown" aria-expanded="false">
                                  <ul class="dropdown-menu province-menu"></ul>
                               </div>
                           </div>
                        </div>
                        <div class="col-6">
                           <div class="mb-3">
                              <label class="form-label">Quận huyện</label>
                              <div class="dropdown">
                                  <input type="text" placeholder="Chọn quận huyện" name="center-district"
                                          value="${district}"
                                          class="form-control dropdown-toggle district-select" id="district-select-${id}"
                                          data-code="${districtCode}" data-bs-toggle="dropdown" aria-expanded="false">
                                  <ul class="dropdown-menu district-menu"></ul>
                              </div>
                           </div>
                        </div>
                        <div class="col-6">
                           <div class="mb-3">
                              <label class="form-label">Phường xã</label>
                              <div class="dropdown">
                                 <input type="text" placeholder="Chọn phường xã" name="center-ward"
                                        value="${ward}"
                                        class="form-control dropdown-toggle ward-select" id="ward-select-${id}"
                                        data-code="${wardCode}" data-bs-toggle="dropdown" aria-expanded="false">
                                 <ul class="dropdown-menu ward-menu"></ul>
                              </div>
                           </div>
                        </div>
                        <div class="col-6">
                          <div class="mb-3">
                            <label for="center-phone-${id}" class="form-label">Số điện thoại</label>
                            <input type="tel" class="form-control" id="center-phone-${id}" name="center-phone" value="${phone}" required>
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

    function generateCenterRowHtml(centerId, response) {
        return `
        <tr data-id="${centerId}">
            <td>${centerId}</td>
            <td>${response.name}</td>
            <td>${response.address}, ${response.ward}, ${response.district}, ${response.province}</td>
            <td>${response.phone}</td>
            <td>
                <a href="#" 
                   class="text-decoration-none edit-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#editCenterModal-${centerId}">
                   <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                </a>
                <a href="#" 
                   class="text-decoration-none delete-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#deleteCenter" 
                   data-id="${centerId}" 
                   data-name="${response.name}">
                   <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                </a>
            </td>
        </tr>
    `;
    }

    // Ham xu ly chuc nang them
    function handleAddCenter() {
        $("#addCenterForm").submit(function (event) {
            event.preventDefault();

            let name = $("#center-name").val();
            let address = $("#center-address").val();
            let province = $("#province").val();
            let district = $("#district").val();
            let ward = $("#ward").val();
            let phone = $("#center-phone").val();

            let provinceCode = $("#province").attr("data-code");
            let districtCode = $("#district").attr("data-code");
            let wardCode = $("#ward").attr("data-code");
            console.log({
                name, address, province, district, ward, phone
            });

            $.ajax({
                url: "/provide_vaccine_services_war/admin/addCenter", type: "POST", data: {
                    "center-name": name,
                    "center-address": address,
                    "center-province": province,
                    "center-district": district,
                    "center-ward": ward,
                    "center-phone": phone
                }, dataType: "json", success: function (response) {
                    if (response.status === "success") {
                        console.log("ID moi:", response.id);
                        // xoa focus truoc do
                        $("#addCenterModal").find("button, input, textarea, select").blur();
                        // an modal
                        $("#addCenterModal").modal("hide");
                        // xoa nd input
                        $("#center-name").val("");
                        $("#center-address").val("");
                        $("#center-province").val("");
                        $("#center-district").val("");
                        $("#center-ward").val("");
                        $("#center-phone").val("");

                        //them modal sua moi vao
                        $("body").append(generateEditModalHtml(response.id, name, address, province, district, ward, phone, provinceCode, districtCode, wardCode));

                        let responseData = {
                            name: name,
                            address: address,
                            province: province,
                            district: district,
                            ward: ward,
                            phone: phone
                        };

                        console.log("Response: ", responseData);

                        let newRowHtml = generateCenterRowHtml(response.id, responseData);
                        $("#center").DataTable().row.add($(newRowHtml)).draw(false);
                    }
                }, error: function () {
                    alert("Loi khi them trung tam!");
                }
            });
        });
    }

    // Ham xu ly chuc nang cap nhat
    function handleUpdateCenter() {
        $(document).on("submit", ".editCenterForm", function (e) {
            e.preventDefault(); // ngăn hành động mặc định

            let modal = $(this).closest(".modal");
            let centerId = modal.find("input[name='id']").val();
            let name = modal.find("input[name='center-name']").val();
            let address = modal.find("input[name='center-address']").val();
            let province = modal.find("input[name='center-province']").val();
            let district = modal.find("input[name='center-district']").val();
            let ward = modal.find("input[name='center-ward']").val();
            let phone = modal.find("input[name='center-phone']").val();

            console.log("ID lấy được từ modal:", centerId);

            $.ajax({
                url: "/provide_vaccine_services_war/admin/updateCenter", type: "POST", data: {
                    id: centerId,
                    "center-name": name,
                    "center-address": address,
                    "center-province": province,
                    "center-district": district,
                    "center-ward": ward,
                    "center-phone": phone
                }, dataType: "json", success: function (response) {
                    if (response.status === "success") {
                        // xoa modal cu
                        $(`#editCenterModal-${centerId}`).remove();
                        // them modal moi
                        $("body").append(generateEditModalHtml(centerId, response.name, response.address, response.province, response.district, response.ward, response.phone));
                        // cap nhat input
                        let newRowHtml = generateCenterRowHtml(centerId, response);
                        let table = $("#center").DataTable();
                        let row = $(`#center tr[data-id='${centerId}']`);
                        table.row(row).remove();
                        table.row.add($(newRowHtml)).draw(false);
                        modal.find("button, input, textarea, select").blur();
                        const bsModal = bootstrap.Modal.getInstance(modal[0]);
                        bsModal.hide();
                    }
                }, error: function (xhr) {
                    console.log("Lỗi: " + xhr.responseText);
                }
            });
        });
    }

    // Ham xu ly chuc nang xoa
    function handleDeleteButton(modalId, removeUrlPrefix, table) {
        let deleteRow = null;
        let deleteId = null;

        $("#center").on("click", ".delete-btn", function (e) {
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
                    alert("Lỗi: " + xhr.responseText);
                }
            });
        });
    }

    // bo focus khoi cac phan tu trong modal tranh loi aria-hidden
    $(document).on("click", "[data-bs-dismiss='modal']", function () {
        $(this).closest(".modal").find("button, input, textarea, select").blur();
    });

    const centerTable = initializeDataTable("#center");
    handleAddCenter();
    handleUpdateCenter();
    handleDeleteButton('deleteCenter', 'removeCenter', centerTable);
});
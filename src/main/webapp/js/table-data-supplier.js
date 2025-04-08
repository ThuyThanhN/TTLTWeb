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
                    title: "Danh sách Nhà Cung Cấp",
                    exportOptions: {columns: [0, 1, 2]}
                },
                {
                    extend: "pdfHtml5",
                    title: "Danh sách Nhà Cung Cấp",
                    exportOptions: {columns: [0, 1, 2]},
                    customize: function (doc) {
                        doc.content[1].table.widths = ["auto", "*", "*"];
                    }
                },
                {
                    extend: "excelHtml5",
                    title: "Danh sách Nhà Cung Cấp",
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

    function generateEditModalHtml(id, name, country) {
        return `
            <div class="modal fade" id="editSupplierModal-${id}" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Chỉnh sửa nhà cung cấp</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form class="editSupplierForm">
                                <input type="hidden" name="id" value="${id}">
                                <div class="mb-3">
                                    <label class="form-label">Tên nhà cung cấp</label>
                                    <input type="text" class="form-control" name="supplier-name" value="${name}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Nước sản xuất</label>
                                    <input type="text" class="form-control" name="supplier-country" value="${country}">
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-save">Lưu</button>
                                    <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>`;
    }

    function generateSupplierRowHtml(supplierId, response) {
        return `
        <tr data-id="${supplierId}">
            <td>${supplierId}</td>
            <td>${response.name}</td>
            <td>${response.country}</td>
            <td>
                <a href="#" 
                   class="text-decoration-none edit-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#editSupplierModal-${supplierId}">
                   <img src="../image/edit.png" alt="Sửa" width="22" height="22">
                </a>
                <a href="#" 
                   class="text-decoration-none delete-btn" 
                   data-bs-toggle="modal" 
                   data-bs-target="#deleteSupplier" 
                   data-id="${supplierId}" 
                   data-name="${response.name}">
                   <img src="../image/bin.png" alt="Xóa" width="24" height="24">
                </a>
            </td>
        </tr>
    `;
    }

    // Ham xu ly chuc nang them
    function handleAddSupplier() {
        $("#addSupplierForm").submit(function (event) {
            event.preventDefault();

            let name = $("#supplier-name").val();
            let country = $("#supplier-country").val();

            $.ajax({
                url: "/provide_vaccine_services_war/admin/addSupplier",
                type: "POST",
                data: {
                    "supplier-name": name,
                    "supplier-country": country
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
                        $("#supplier-name").val("");
                        $("#supplier-country").val("");

                        // them modal moi vao
                        $("body").append(generateEditModalHtml(response.id, name, country));

                        let responseData = {
                            name: name,
                            country: country
                        };

                        console.log("Response: ", responseData);

                        let newRowHtml = generateSupplierRowHtml(response.id, responseData);
                        $("#supplier").DataTable().row.add($(newRowHtml)).draw(false);
                    }
                },
                error: function () {
                    alert("Loi khi them nha cung cap!");
                }
            });
        });
    }

    // Ham xu ly chuc nang cap nhat
    function handleUpdateSupplier() {
        $(document).on("submit", ".editSupplierForm", function (e) {
            e.preventDefault(); // ngăn hành động mặc định

            let modal = $(this).closest(".modal");
            let supplierId = modal.find("input[name='id']").val();
            let name = modal.find("input[name='supplier-name']").val();
            let country = modal.find("input[name='supplier-country']").val();

            $.ajax({
                url: "/provide_vaccine_services_war/admin/updateSupplier",
                type: "POST",
                data: {
                    id: supplierId,
                    "supplier-name": name,
                    "supplier-country": country
                },
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {
                        // xoa modal cu
                        $(`#editSupplierModal-${supplierId}`).remove();
                        // them modal moi
                        $("body").append(generateEditModalHtml(supplierId, response.name, response.country));
                        // cap nhat input
                        let newRowHtml = generateSupplierRowHtml(supplierId, response);
                        let table = $("#supplier").DataTable();
                        let row = $(`#supplier tr[data-id='${supplierId}']`);
                        table.row(row).remove();
                        table.row.add($(newRowHtml)).draw(false);
                        modal.find("button, input, textarea, select").blur();
                        const bsModal = bootstrap.Modal.getInstance(modal[0]);
                        bsModal.hide();
                    }
                },
                error: function (xhr) {
                    console.log("Lỗi: " + xhr.responseText);
                }
            });
        });
    }

    // Ham xu ly chuc nang xoa
    function handleDeleteButton(modalId, removeUrlPrefix, table) {
        let deleteRow = null;
        let deleteId = null;

        $("#supplier").on("click", ".delete-btn", function (e) {
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

    const supplierTable = initializeDataTable("#supplier");
    handleAddSupplier();
    handleUpdateSupplier()
    handleDeleteButton('deleteSupplier', 'removeSupplier', supplierTable);
});

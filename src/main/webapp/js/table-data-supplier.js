function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

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
                    exportOptions: { columns: [0, 1, 2] }
                },
                {
                    extend: "pdfHtml5",
                    title: "Danh sách Nhà Cung Cấp",
                    exportOptions: { columns: [0, 1, 2] },
                    customize: function (doc) {
                        doc.content[1].table.widths = ["auto", "*", "*"];
                    }
                },
                {
                    extend: "excelHtml5",
                    title: "Danh sách Nhà Cung Cấp",
                    exportOptions: { columns: [0, 1, 2] }
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
                data: { "supplier-name": name, "supplier-country": country },
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {
                        console.log("ID moi:", response.id);

                        // An modal
                        $("#addModal").modal("hide");

                        // Xoa noi dung input
                        $("#supplier-name").val("");
                        $("#supplier-country").val("");

                        // Them dong moi vao DataTable
                        let newRow = [
                            response.id,
                            name,
                            country,
                            `<a href="updateSupplier?id=${response.id}" class="text-decoration-none edit-btn" data-bs-toggle="modal" data-bs-target="#editSupplierModal-${response.id}">
                                <img src="../image/edit.png" alt="Sua" width="22" height="22">
                            </a>
                            <a href="#" class="text-decoration-none delete-btn" data-bs-toggle="modal" data-bs-target="#deleteSupplier" data-id="${response.id}" data-name="${name}">
                                <img src="../image/bin.png" alt="Xoa" width="24" height="24">
                            </a>`
                        ];

                        console.log("Du lieu them vao DataTable:", newRow);
                        $("#supplier").DataTable().row.add(newRow).draw(false);
                    }
                },
                error: function () {
                    alert("Loi khi them nha cung cap!");
                }
            });
        });
    }

    function handleUpdateSupplier() {
        $(document).on("submit", ".editSupplierForm", function (e) {
            e.preventDefault(); // ngan hanh dong mac dinh

            let modal = $(this).closest(".modal");
            let supplierId = modal.find("input[name='id']").val();
            let name = modal.find("input[name='supplier-name']").val();
            let country = modal.find("input[name='supplier-country']").val();

            if (!supplierId || !name || !country) {
                alert("Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            $.ajax({
                url: "/provide_vaccine_services_war/admin/updateSupplier",
                type: "POST",
                data: { id: supplierId, "supplier-name": name, "supplier-country": country },
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {

                        // tim id cua nha cung cap tuong ung
                        let row = $(`#supplier tr:has(td:contains('${supplierId}'))`);
                        row.find("td:eq(1)").text(response.name);
                        row.find("td:eq(2)").text(response.country);

                        // an modal
                        modal.modal("hide");
                    } else {
                        alert("Cập nhật thất bại: " + response.message);
                    }
                },
                error: function (xhr) {
                    console.log("Có lỗi xảy ra: " + xhr.responseText);
                }
            });
        });
    }

    function handleDeleteButton(modalId, removeUrlPrefix) {
        let deleteId = null;

        $("#supplier").on("click", ".delete-btn", function (e) {
            e.preventDefault();
            e.currentTarget.blur(); // Fix lỗi aria-hidden

            let modalSelector = `#${modalId}`;
            deleteId = $(this).data("id");
            let itemName = $(this).data("name");

            console.log("ID - Tên:", "data-supplier " + modalSelector + " " + deleteId + " " + itemName);

            let modal = document.querySelector(modalSelector);
            modal.querySelector('.modal-body').textContent = `Bạn có chắc chắn muốn xóa ${itemName}?`;

            // hien modal
            $(modalSelector).modal("show");
        });

        // Xử lý xác nhận xóa bằng AJAX
        $("#confirmDelete").on("click", function () {
            if (!deleteId) return;

            $.ajax({
                url: `./${removeUrlPrefix}`,
                type: "POST",
                data: { id: deleteId },
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {
                        // xoa nha cung cap tuong ung qua id
                        $(`#supplier tr:has(td:contains('${deleteId}'))`).remove();
                        $(`#${modalId}`).modal("hide"); // an modal
                    }
                },
                error: function (xhr) {
                    alert("Loi : " + xhr.responseText);
                }
            });
        });
    }

    initializeDataTable('#supplier');
    handleAddSupplier();
    handleUpdateSupplier()
    handleDeleteButton('deleteSupplier', 'removeSupplier');
});

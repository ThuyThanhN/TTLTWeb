function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

$(document).ready(function () {
    // Ham khoi tao DataTable
    function initializeDataTable(selector) {
        $(selector).DataTable({
            "pagingType": "numbers",
            "pageLength": 5,
            "language": {
                "emptyTable": "Khong co du lieu",
                "info": "Hien thi _START_ den _TOTAL_ muc",
                "infoEmpty": "Hien thi 0 den 0 cua 0 muc",
                "infoFiltered": "(duoc loc tu _MAX_ muc)",
                "lengthMenu": "Hien thi _MENU_ muc",
                "loadingRecords": "Dang tai...",
                "processing": "Dang xu ly...",
                "search": "Tim kiem:",
                "zeroRecords": "Khong tim thay du lieu phu hop"
            }
        });
    }

    // Ham xu ly chuc nang xoa
    function handleDeleteButton(modalId, removeUrlPrefix) {
        $("#supplier").on("click", ".delete-btn", function (e) {
            e.preventDefault();
            e.currentTarget.blur(); // Fix loi aria-hidden

            let removeUrl = `./${removeUrlPrefix}`;
            let modalSelector = `#${modalId}`;
            let itemId = $(this).data("id");
            let itemName = $(this).data("name");

            console.log("ID - Ten:", "data-supplier " + modalSelector + " " + itemId + " " + itemName);

            let modal = document.querySelector(modalSelector);
            modal.querySelector('.modal-body').textContent = `Ban co chac chan muon xoa ${itemName}?`;

            // Cap nhat link nut xac nhan xoa
            modal.querySelector('#confirmDelete').setAttribute('href', removeUrl + '?id=' + itemId);
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

    initializeDataTable('#supplier');
    handleDeleteButton('deleteSupplier', 'removeSupplier');
    handleAddSupplier();
});

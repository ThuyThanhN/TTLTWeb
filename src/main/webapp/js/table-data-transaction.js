$(document).ready(function() {

    $(document).on('click', '.edit-btn', function () {
        const transactionId = $(this).data('id');
        const modal = new bootstrap.Modal(document.getElementById('editTransaction-' + transactionId));
        modal.show();
    });

    // khi bấm nút import thì input cũng được click
    $('#importExcel').click(function() {
        $('#excelFileInput').click();
    });

    // khi file input được nhập thì gửi method POST tới importExcelServlet
    $('#excelFileInput').change(function() {
        let file = this.files[0];
        if (file) {
            let formData = new FormData();
            formData.append('excelFile', file);

            $.ajax({
                url: '/admin/ImportExcelServlet',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(res) {
                    console.log(res.data);
                    alert('Thành công');
                },
                error: function() {
                    alert('Lỗi');
                }
            });
        }
    });





    //  chỉnh sửa
    $(document).on("submit", ".editTransactionForm", function (e) {
        e.preventDefault(); // ngăn hành động mặc định

        let modal = $(this).closest(".modal");
        let transactionId = modal.find("input[name='id']").val();
        let vaccineId = modal.find("select[name='vaccineId']").val();
        let type = modal.find("select[name='type']").val();
        let quantity = modal.find("input[name='quantity']").val();
        let expiryDate = modal.find("input[name='expiry_date']").val();


        $.ajax({
            url: "/provide_vaccine_services_war/admin/updateTransaction",  // URL thay đổi theo route của updateTransaction
            type: "POST",
            data: {
                id: transactionId,
                vaccineId: vaccineId,
                type: type,
                quantity: quantity,
                expiry_date: expiryDate,
            },
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    // Ẩn modal
                    $(`#editTransaction-${transactionId}`).modal('hide');

                    // Cập nhật lại bảng
                    const row = $(`tr[data-id='${transactionId}']`);
                    row.find("td:eq(2)").text(type);
                    row.find("td:eq(3)").text(quantity);
                    row.find("td:eq(5)").text(expiryDate);

                    Swal.fire({
                        icon: 'success',
                        title: 'Cập nhật thành công!',
                        showConfirmButton: false,
                        timer: 1500
                    });
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


    // xuất thành pdf
    $("#exportPDF").on("click", function () {
        $.ajax({
            url: "/admin/exportTransaction",
            type: "GET",
            dataType: "json",
            success: function (data) {


                let docDefinition = {
                    content: [
                        {text: "Danh sách Giao dịch", style: "header"},
                        {
                            table: {
                                headerRows: 1,
                                widths: ["5%", "15%", "10%", "10%", "15%", "15%", "15%"],
                                body: [
                                    ["ID", "Tên vắc xin", "Loại", "Số lượng", "Ngày tạo", "Hạn dùng", "Id Người thực hiện"],
                                    ...data.map(t => [
                                        t.id,
                                        t.vaccineName,
                                        t.type,
                                        t.quantity,
                                        t.date,
                                        t.expiry_date || "Không có",
                                        t.user_id
                                    ])
                                ]
                            }
                        }
                    ],
                    styles: {
                        header: {fontSize: 16, bold: true, alignment: "center", margin: [0, 10, 0, 10]}
                    }
                };
                pdfMake.createPdf(docDefinition).download("Danh_sach_GiaoDich.pdf");
            },
            error: function () {
                alert("Lỗi tải dữ liệu giao dịch!");
            }
        });
    });


    // xuất thành file excel
    $("#exportExcel").on("click", function () {
        $.ajax({
            url: "/admin/exportTransaction",
            type: "GET",
            dataType: "json",
            success: function (data) {
                let worksheetData = [
                    ["ID", "Tên vắc xin", "Loại", "Số lượng", "Ngày tạo", "Hạn dùng", "Id Người thực hiện"]
                ];

                data.forEach(t => {
                    worksheetData.push([
                        t.id,
                        t.vaccineName,
                        t.type,
                        t.quantity,
                        t.date,
                        t.expiry_date || "Không có",
                        t.user_id
                    ]);
                });

                let wb = XLSX.utils.book_new();
                let ws = XLSX.utils.aoa_to_sheet(worksheetData);
                XLSX.utils.book_append_sheet(wb, ws, "Danh sách Giao dịch");
                XLSX.writeFile(wb, "Danh_sach_GiaoDich.xlsx");
            },
            error: function () {
                alert("Lỗi tải dữ liệu giao dịch!");
            }
        });
    });


    // Xử lý nút xóa giao dịch
    function handleDeleteButton(modalId, removeUrlPrefix, table) {
        let deleteRow = null;
        let deleteId = null;

        console.log("1" + deleteId);
        console.log("1" + deleteRow);

        $("#transaction").on("click", ".delete-transaction-btn", function (e) {
            e.preventDefault();

            deleteId = $(this).data("id");
            let itemName = $(this).data("name");
            deleteRow = table.row($(this).closest("tr"));

            $(`#${modalId} .modal-body`).text(`Bạn có chắc chắn muốn xóa giao dịch: ${itemName}?`);
            $(`#${modalId}`).modal("show");
        });

        $("#confirmDeleteTransaction").on("click", function (e) {
            e.preventDefault();

            console.log(deleteId);
            console.log(deleteRow);


            if (!deleteId || !deleteRow) return;

            $.ajax({
                url: `./${removeUrlPrefix}`,
                type: "POST",
                data: {id: deleteId},
                dataType: "json",
                success: function (response) {
                    if (response.status === "success") {
                        deleteRow.remove().draw();
                        const bsModal = bootstrap.Modal.getInstance(document.getElementById(modalId));
                        console.log(bsModal)
                        bsModal.hide();
                    } else {
                        alert("Xóa thất bại");
                    }
                },
                error: function (xhr) {
                    if (xhr.status === 403) {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Cảnh báo',
                            text: "Không có quyền thực hiện chức năng này!",
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


    };
    // gọi phương thức
    const transactionTable = initializeDataTable('#transaction');
    handleDeleteButton('deleteTransaction', 'removeTransaction', transactionTable);
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
        }
    });

    return table;
}

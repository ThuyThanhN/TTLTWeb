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

});

function generateEditTransactionModalHtml(transactionId, vaccineId, type, quantity, expiryDate, userId) {
    return `
        <div class="modal fade" id="editTransaction-${transactionId}" data-bs-backdrop="static" 
             data-bs-keyboard="false" tabindex="-1" aria-labelledby="editLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editLabel">Cập nhật giao dịch ${transactionId}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form class="editTransactionForm" method="post">
                            <input type="hidden" name="id" value="${transactionId}">
                            <div class="row">
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="vaccineId-${transactionId}" class="form-label">Vaccine</label>
                                        <select name="vaccineId" class="form-control" id="vaccineId-${transactionId}" required>
                                            <option value="${vaccineId}" selected>Vaccine ${vaccineId}</option>
                                            <!-- Option list of vaccines -->
                                        </select>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="type-${transactionId}" class="form-label">Loại giao dịch</label>
                                        <select name="type" class="form-select" id="type-${transactionId}" required>
                                            <option value="Nhập kho" ${type === 'Nhập kho' ? 'selected' : ''}>Nhập kho</option>
                                            <option value="Xuất kho" ${type === 'Xuất kho' ? 'selected' : ''}>Xuất kho</option>
                                            <!-- Các loại giao dịch khác -->
                                        </select>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="quantity-${transactionId}" class="form-label">Số lượng</label>
                                        <input type="number" name="quantity" class="form-control" id="quantity-${transactionId}" value="${quantity}" required>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="expiryDate-${transactionId}" class="form-label">Ngày hết hạn</label>
                                        <input type="date" name="expiryDate" class="form-control" id="expiryDate-${transactionId}" value="${expiryDate}" required>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mb-3">
                                        <label for="userId-${transactionId}" class="form-label">Người dùng</label>
                                        <input type="text" name="userId" class="form-control" id="userId-${transactionId}" value="${userId}" disabled>
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


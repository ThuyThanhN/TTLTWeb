function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

$(document).ready(function () {
    // xuat pdf
    $("#exportPDF").on("click", function () {
        $.ajax({
            url: "/provide_vaccine_services_war/admin/exportVaccine", // API lấy danh sách vaccine
            type: "GET",
            dataType: "json",
            success: function (data) {
                let docDefinition = {
                    content: [
                        {text: "Danh sách Vắc Xin", style: "header"},
                        {
                            table: {
                                headerRows: 1,
                                widths: ["5%", "20%", "15%", "15%", "15%", "30%"], // Định dạng cột
                                body: [
                                    ["ID", "Tên", "Nước sản xuất", "Trạng thái", "Giá (VNĐ)", "Mô tả"], // Tiêu đề bảng
                                    ...data.map(v => [
                                        v.id,
                                        v.name,
                                        v.countryOfOrigin, // Kiểm tra giá trị null
                                        v.status,
                                        v.price.toLocaleString("vi-VN") + " đ",
                                        v.description
                                    ])
                                ]
                            }
                        }
                    ],
                    styles: {
                        header: {fontSize: 14, bold: true, alignment: "center", margin: [0, 10, 0, 10]},
                        tableHeader: {bold: true, fontSize: 12, color: "white", fillColor: "#4CAF50"}
                    }
                };
                pdfMake.createPdf(docDefinition).download("Danh_sach_VacXin.pdf");
            },
            error: function () {
                alert("Loi du lieu!");
            }
        });
    });

    // xuat excel
    $("#exportExcel").on("click", function () {
        $.ajax({
            url: "/provide_vaccine_services_war/admin/exportVaccine",
            type: "GET",
            dataType: "json",
            success: function (data) {
                let vaccineArray = Object.keys(data).map(key => ({
                    id: key,
                    ...data[key]
                }));

                let worksheetData = [
                    ["ID", "Tên", "Nước sản xuất", "Trạng thái", "Giá (VNĐ)", "Mô tả"]
                ];

                vaccineArray.forEach(v => {
                    worksheetData.push([
                        v.id,
                        v.name,
                        v.countryOfOrigin || "Không có dữ liệu",
                        v.status,
                        v.price.toLocaleString("vi-VN") + " đ",
                        v.description
                    ]);
                });

                let wb = XLSX.utils.book_new();
                let ws = XLSX.utils.aoa_to_sheet(worksheetData);
                XLSX.utils.book_append_sheet(wb, ws, "Danh sách Vaccine");

                XLSX.writeFile(wb, "Danh_sach_VacXin.xlsx");
            },
            error: function () {
                alert("Loi du lieu!");
            }
        });
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

    // Ham xu ly chuc nang xoa
    function handleDeleteButton(modalId, removeUrlPrefix, table) {
        let deleteRow = null;
        let deleteId = null;

        $("#vaccine").on("click", ".delete-btn", function (e) {
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

    const vaccineTable = initializeDataTable('#vaccine');
    handleDeleteButton('deleteVaccine', 'removeVaccine', vaccineTable);
});



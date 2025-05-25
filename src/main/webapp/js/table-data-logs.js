$(document).ready(function () {
    let logsTable = $("#logsTable").DataTable({
        pagingType: "numbers",
        pageLength: 10,
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
                title: "Danh sách Logs",
                exportOptions: { columns: [0, 1, 2, 3, 4, 5] }
            },
            {
                extend: "pdfHtml5",
                title: "Danh sách Logs",
                exportOptions: { columns: [0, 1, 2, 3, 4, 5] }
            },
            {
                extend: "excelHtml5",
                title: "Danh sách Logs",
                exportOptions: { columns: [0, 1, 2, 3, 4, 5] }
            }
        ]
    });

    $("#print").on("click", function () {
        logsTable.button(0).trigger();
    });
    $("#exportPDF").on("click", function () {
        logsTable.button(1).trigger();
    });
    $("#exportExcel").on("click", function () {
        logsTable.button(2).trigger();
    });
});

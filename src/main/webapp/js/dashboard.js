function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

$(document).ready(function () {
    // Hàm khởi tạo DataTable
    function initializeDataTable(selector) {
        $(selector).DataTable({
            "pagingType": "numbers",
            "pageLength": 5,
            "language": {
                "emptyTable": "Không có dữ liệu",
                "info": "Hiển thị _START_ đến _TOTAL_ mục",
                "infoEmpty": "Hiển thị 0 đến 0 của 0 mục",
                "infoFiltered": "(được lọc từ _MAX_ mục)",
                "lengthMenu": "Hiển thị _MENU_ mục",
                "loadingRecords": "Đang tải...",
                "processing": "Đang xử lý...",
                "search": "Tìm kiếm:",
                "zeroRecords": "Không tìm thấy dữ liệu phù hợp"
            }
        });
    }

    initializeDataTable('#admin');
});



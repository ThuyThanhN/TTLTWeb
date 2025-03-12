
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

    initializeDataTable('#center');

    function handleDeleteButton ( modalId, removeUrlPrefix) {
        $("#center").on("click", ".delete-btn", function (e) {
            e.currentTarget.blur();
            let removeUrl = `./${removeUrlPrefix}`;
            let modalSelector = `${modalId}`;
            e.preventDefault();
            var itemId = $(this).data("id");
            var itemName = $(this).data("name");
            // alert(id);
            console.log("id-name","admin-center"+modalSelector+ itemId + itemName);
            var modal = document.getElementById(modalSelector);
            modal.querySelector('.modal-body').textContent = 'Bạn có chắc chắn muốn xóa ' + itemName + '?';

            // Cập nhật link nút xác nhận
            var confirmDeleteButton = modal.querySelector('#confirmDelete');
            console.log("removeurl",removeUrl);
            confirmDeleteButton.setAttribute('href', removeUrl + '?id=' + itemId);

        });
    }

    handleDeleteButton( 'deleteCenter', 'removeCenter');
});



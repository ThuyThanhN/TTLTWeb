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
    initializeDataTable('#user');

    $(".status").change(function () {
        var id = $(this).data("id");
        var status = $(this).val();

        $.ajax({
            url: "/provide_vaccine_services_war/admin/dashboard",
            type: "POST",
            data: {
                id: id,
                status: status
            },
            success: function (response) {
                console.log("Cap nhat trang thai thanh cong", response);
            },
            error: function (xhr) {
            }
        });
    });

});

const statusSelects = document.querySelectorAll('.status');

statusSelects.forEach(select => {
    if (select.value === "1") {
        select.classList.add('active');
    } else {
        select.classList.add('inactive');
    }

    select.addEventListener('change', function () {
        const selectedValue = this.value;

        this.classList.remove('active', 'inactive');

        if (selectedValue === "1") {
            this.classList.add('active');
        } else {
            this.classList.add('inactive');
        }
    });
});



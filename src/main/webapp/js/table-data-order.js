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

    initializeDataTable('#orderDetail');
    initializeDataTable('#order');

    $(".view-detail").click(function () {
        var orderId = $(this).data("id");
        console.log(orderId)
        $.ajax({
            url: "/provide_vaccine_services_war/admin/displayDetailOrder",
            type: "GET",
            data: { id: orderId },
            dataType: "json",
            success: function (data) {
                if (data.length === 0) {
                    $("#modalBody").html("<p class='text-danger'>Không tìm thấy chi tiết đơn hàng!</p>");
                    return;
                }

                var html = "";
                data.forEach(function (od) {
                    console.log("Dữ liệu nhận về:", data);
                    const formattedPrice = new Intl.NumberFormat('vi-VN').format(od.total_price);

                    html += "<div class='row g-0'>";
                    html += "    <div class='col-12 detail-bottom'>";
                    html += "        <h3>Đơn hàng #" + od.order_id + "</h3>";
                    html += "        <p><strong>Loại vắc xin: </strong>" + od.vaccine_or_package_names + "</p>";
                    html += "        <p><strong>Tổng tiền: </strong>" + formattedPrice + "đ</p>";
                    html += "    </div>";
                    html += "    <div class='col-12 detail-top'>";
                    html += "        <p><strong>Tên người liên hệ: </strong>" + od.contact_name + "</p>";
                    html += "        <p><strong>Mối quan hệ với người tiêm: </strong>" + od.relationship + "</p>";
                    html += "        <p><strong>Số điện thoại: </strong>" + od.phone + "</p>";
                    html += "    </div>";
                    html += "</div>";
                });

                $("#modalBody").html(html);
                $("#displayDetail").modal("show");
            },
            error: function () {
                $("#modalBody").html("<p class='text-danger'>Lỗi tải dữ liệu!</p>");
            }
        });
    });
});

function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("show");
}

const statusSelects = document.querySelectorAll('.status');

statusSelects.forEach(select => {
    if (select.value === 'Đã duyệt') {
        select.classList.add('active');
    } else {
        select.classList.add('inactive');
    }

    select.addEventListener('change', function() {
        const selectedValue = this.value;

        this.classList.remove('active', 'inactive');

        if (selectedValue === 'Đã duyệt') {
            this.classList.add('active');
        } else {
            this.classList.add('inactive');
        }
    });
});


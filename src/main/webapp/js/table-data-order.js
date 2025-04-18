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

    $(".status").change(function () {
        var orderId = $(this).data("order-id");
        var status = $(this).val();

        $.ajax({
            url: "/provide_vaccine_services_war/admin/updateOrderStatus",
            type: "POST",
            data: {
                order_id: orderId,
                status: status
            },
            success: function (response) {
               console.log("Cap nhat trang thai thanh cong", response);
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

    // xuat pdf
    $("#exportPDF").on("click", function () {
        $.ajax({
            url: "/provide_vaccine_services_war/admin/exportOrder", // API lấy danh sách đơn hàng
            type: "GET",
            dataType: "json",
            success: function (data) {
                let tableBody = [];

                // Thêm tiêu đề cột
                tableBody.push([
                    {text: "ID", style: "tableHeader"},
                    {text: "Tên bệnh nhân", style: "tableHeader"},
                    {text: "Tên liên hệ", style: "tableHeader"},
                    {text: "Quan hệ", style: "tableHeader"},
                    {text: "SĐT", style: "tableHeader"},
                    {text: "Trung tâm", style: "tableHeader"},
                    {text: "Ngày hẹn", style: "tableHeader"},
                    {text: "Giờ hẹn", style: "tableHeader"},
                    {text: "Tổng tiền", style: "tableHeader"},
                    {text: "Vắc xin", style: "tableHeader"}
                ]);

                // Thêm dữ liệu
                data.forEach(function (order) {
                    const formattedPrice = new Intl.NumberFormat("vi-VN").format(order.total_price);
                    tableBody.push([
                        order.order_id,
                        order.patient_name,
                        order.contact_name,
                        order.relationship,
                        order.phone,
                        order.center_name,
                        order.appointment_date,
                        order.appointment_time,
                        formattedPrice,
                        { text: order.vaccine_or_package_names, noWrap: false, alignment: "left" }
                    ]);
                });

                let docDefinition = {
                    content: [
                        {text: "Danh sách Đơn hàng", style: "header"},
                        {
                            table: {
                                headerRows: 1,
                                widths: ['5%', '12%', '10%', '10%', '11%', '12%', '10%', '10%', '10%', '10%'],
                                body: tableBody
                            }
                        }
                    ],
                    styles: {
                        header: {fontSize: 16, bold: true, alignment: "center", margin: [0, 10, 0, 10]},
                        tableHeader: {bold: true, fontSize: 12, color: "white", fillColor: "#4CAF50"}
                    },
                    pageOrientation: "landscape"
                };

                pdfMake.createPdf(docDefinition).download("Danh_sach_DonHang.pdf");
            },
            error: function () {
                alert("Lỗi dữ liệu!");
            }
        });
    });

    $("#exportExcel").on("click", function () {
        $.ajax({
            url: "/provide_vaccine_services_war/admin/exportOrder",
            type: "GET",
            dataType: "json",
            success: function (data) {
                let excelData = [];

                // Thêm tiêu đề cột
                excelData.push([
                    "ID", "Tên bệnh nhân", "Tên liên hệ", "Quan hệ", "SĐT",
                    "Trung tâm", "Ngày hẹn", "Giờ hẹn", "Tổng tiền", "Vắc xin"
                ]);

                // Thêm dữ liệu
                data.forEach(function (order) {
                    const formattedPrice = new Intl.NumberFormat("vi-VN").format(order.total_price);
                    excelData.push([
                        order.order_id,
                        order.patient_name,
                        order.contact_name,
                        order.relationship,
                        order.phone,
                        order.center_name,
                        order.appointment_date,
                        order.appointment_time,
                        formattedPrice,
                        order.vaccine_or_package_names
                    ]);
                });


                let ws = XLSX.utils.aoa_to_sheet(excelData);
                let wb = XLSX.utils.book_new();
                XLSX.utils.book_append_sheet(wb, ws, "Danh sách Đơn hàng");
                XLSX.writeFile(wb, "Danh_sach_DonHang.xlsx");
            },
            error: function () {
                alert("Loi!");
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


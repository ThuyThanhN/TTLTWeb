$(document).ready(function () {
    // Xử lý submit form thêm transaction
    $("#addTransaction").submit(function (event) {
        event.preventDefault(); // Ngăn form submit mặc định

        let formData = new FormData(this);

        $.ajax({
            url: "/provide_vaccine_services_war/admin/AddTransaction", // Servlet xử lý POST
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                // Có thể thêm xử lý nếu backend trả JSON hoặc redirect
                Swal.fire({
                    icon: 'success',
                    title: 'Thêm giao dịch thành công!',
                    confirmButtonText: 'OK'
                }).then(() => {
                    window.location.href = "/provide_vaccine_services_war/admin/table-data-vacxin";
                });
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
});

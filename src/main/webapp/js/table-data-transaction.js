$(document).ready(function() {
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
                url: '/provide_vaccine_services_war/admin/ImportExcelServlet',
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
});

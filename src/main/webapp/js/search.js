$(document).ready(function () {
    $("#searchBtn").click(function () {
        searchVaccine();
    });

    $("#searchQuery").keypress(function (event) {
        if (event.which === 13) { // Nhấn Enter sẽ gọi searchVaccine()
            searchVaccine();
        }
    });
});

function searchVaccine() {
    const query = $("#searchQuery").val().trim();

    $.ajax({
        url: "/provide_vaccine_services_war/vaccine-information",
        type: "GET",
        data: {action: "search", query: query},
        success: function (response) {

            // nếu không tìm thấy sản phẩm trả về
            if (response.length === 0) {
                $("#vaccine-list").html("<p>Không tìm thấy kết quả.</p>");
                return;
            }

            //set lại danh sách trang


            // Xóa nội dung hiện tại trong thẻ div chứa danh sách sản phẩm
            $("#vaccine-list").empty();
            // Cập nhật lại danh sách vaccine vào thẻ div
            response.forEach(v => {
                $("#vaccine-list").append(`
                    <div class="col-12 col-md-4 mb-3">
                        <div class="vx_item">
                            <a href="detail_vaccines?id=${v.id}">
                                <img src="${v.imageUrl}" alt="">
                                <div class="vaccine_name" title="${v.name}">${v.name}</div>
                            </a>
                            <div class="vaccine-content">${v.description}</div>
                        </div>
                    </div>
                `);
            });
        },
        error:function () {
            alert("lỗi tìm sản phẩm");
        }
    });
}
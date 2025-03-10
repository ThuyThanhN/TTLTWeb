$(document).ready(function () {
    $("#searchBtn").click(function () {
        searchVaccine(); // Gọi function tìm kiếm
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
            // Xóa nội dung hiện tại
            $("#vaccine-list").empty();

            // Cập nhật danh sách vaccine
            response.forEach(vaccine => {
                const vaccineItem = `
                    <div class="col-12 col-md-4 mb-3">
                        <div class="vx_item">
                            <a href="detail_vaccines?id=${vaccine.id}">
                                <img src="${vaccine.imageUrl}" alt="">
                                <div class="vaccine_name" title="${vaccine.name}">${vaccine.name}</div>
                            </a>
                            <div class="vaccine-content">${vaccine.description}</div>
                        </div>
                    </div>
                `;
                $(".row").append(vaccineItem);
            });
        },
        error:function () {
            alert("lỗi tìm sản phẩm");
        }
    });
}
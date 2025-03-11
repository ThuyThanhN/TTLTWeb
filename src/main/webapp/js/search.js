let currentSearchPage = 1;

// khi vừa load trang set các sự kiện
$(document).ready(function () {

    // khi click nút tìm kiếm
    $("#searchBtn").click(() => searchVaccine(currentSearchPage));


    // khi nhấn enter
    $("#searchQuery").keypress(function (event) {
        if (event.which === 13) { // Nhấn Enter sẽ gọi searchVaccine()
            event.preventDefault(); // ngăn reload trang
            searchVaccine(currentSearchPage);
        }
    });

    //chạy lần đầu sẽ gọi để load dữ liệu
    searchVaccine(currentSearchPage);
});


// tìm theo từ khoá và số trang
function searchVaccine(page) {
    const query = $("#searchQuery").val().trim();

    $.ajax({
        url: "/provide_vaccine_services_war/vaccine-information",
        type: "GET",
        data: {action: "search", query: query, page: page},
        success: function (response) {

            // nếu không tìm thấy sản phẩm trả về
            if (response.length === 0) {
                $("#vaccine-list").html("<p>Không tìm thấy kết quả.</p>");
                return;
            }

            currentSearchPage = response.pageNumber;

            //set lại danh sách trang
            updatePagination(response.totalPages);

            // Xóa nội dung hiện tại trong thẻ div chứa danh sách sản phẩm
            $("#vaccine-list").empty();

            const vaccinesList = response.vaccines;
            vaccinesList.forEach(v => {
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
            // Cuộn lên đầu danh sách với hiệu ứng mượt
            $('html, body').animate({
                scrollTop: $("#searchQuery").offset().top
            }, 500);
        },
        error: function () {
            alert("lỗi tìm sản phẩm");
        }
    });
}

// update số trang
function updatePagination(totalPages) {
    let paginationHtml = "";

    if (currentSearchPage > 1) {
        paginationHtml += `<li class="page-item"><button class="page-link" onclick="searchVaccine(${currentSearchPage - 1})"><i class="fa-solid fa-arrow-left"></i></button></li>`;
    }

    for (let i = 1; i <= totalPages; i++) {
        paginationHtml += `<li class="page-item ${i === currentSearchPage ? 'active' : ''}"><button class="page-link" onclick="searchVaccine(${i})">${i}</button></li>`;
    }

    if (currentSearchPage < totalPages) {
        paginationHtml += `<li class="page-item"><button class="page-link" onclick="searchVaccine(${currentSearchPage + 1})"><i class="fa-solid fa-arrow-right"></i></button></li>`;
    }

    $("#pagination").html(paginationHtml);
}

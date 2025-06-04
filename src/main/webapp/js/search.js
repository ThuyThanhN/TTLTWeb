let currentSearchPage = 1;
let age = false;
let disease = false;

document.getElementById("searchQuery").addEventListener("input", function () {
    document.querySelector(".autocomplete-container").hidden = false;
    autoComplete();
});

// khi vừa load trang set các sự kiện
$(document).ready(function () {

    // khi click nút tìm kiếm
    $("#searchBtn").click(() => {
        searchVaccine(1);
        currentSearchPage = 1;
    });


    // khi nhấn enter
    $("#searchQuery").keypress((event) => {
        if (event.which === 13) { // Nhấn Enter sẽ gọi searchVaccine()
            event.preventDefault(); // ngăn reload trang
            searchVaccine(1);
            currentSearchPage = 1;
        }
    });

    //chạy lần đầu sẽ gọi để load dữ liệu
    searchVaccine(currentSearchPage);
});

//tìm kiếm
function ageFilter() {
    age = !age;
    const ageButton = document.getElementById("ageButton");
    const diseaseButton = document.getElementById("diseaseButton");
    if (age) {
        ageButton.classList.toggle("active");
        diseaseButton.classList.remove("active");
    } else {
        ageButton.classList.remove("active");
    }
    searchVaccine(1);
}

function diseaseFilter() {
    disease = !disease;
    const ageButton = document.getElementById("ageButton");
    const diseaseButton = document.getElementById("diseaseButton");
    if (disease) {
        diseaseButton.classList.toggle("active");
        ageButton.classList.remove("active");
    } else {
        diseaseButton.classList.remove("active");
    }
    searchVaccine(1);
}

// tìm theo từ khoá và số trang
function searchVaccine(page) {
    const query = $("#searchQuery").val().trim();

    $.ajax({
        url: "/vaccine-information",
        type: "GET",
        data: {action: "search", query: query, page: page, age: age, disease: disease},
        success: (response) => {
            showVaccines(response);
        },
        error: function () {
            alert("lỗi tìm sản phẩm");
        }
    });
}


// hiển thị vaccines
function showVaccines(response) {
// nếu không tìm thấy sản phẩm trả về
    if (response.vaccines === undefined) {
        $("#vaccine-list").append();
        return;
    }

    currentSearchPage = response.pageNumber;
    //set lại danh sách trang
    updatePagination(response.totalPages);
    const vaccinesList = response.vaccines;

    // Xóa nội dung hiện tại trong thẻ div chứa danh sách sản phẩm
    $("#vaccine-list").empty();
    if (vaccinesList.length == 0) {
        $("#vaccine-list").append(`<div class="vx_item">
                    <div class="mt-3 alert text-center text-primary" role="alert">
                    <strong>Không tìm thấy kết quả.</strong> Vui lòng thử lại với từ khóa khác!
               </div>
               </div>
        `);
    }
    vaccinesList.forEach(v => {
        $("#vaccine-list").append(`
                    <div class="col-12 col-md-4 mb-3">
                        <div class="vx_item">
                            <a href="detail_vaccines?id=${v.id}">
                                <img src="${v.imageUrl}" alt="${v.name}" loading="lazy" decoding="async">
                                <div class="vaccine_name" title="${v.name}">${v.name}</div>
                            </a>
                            <div class="vaccine-content">${v.description}</div>
                        </div>
                    </div>
                `);
    });
    // Cuộn lên đầu danh sách với hiệu ứng
    $('html, body').animate({
        scrollTop: $("#searchQuery").offset().top
    }, 500);
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

// đề xuất từ khoá
function autoComplete() {
    const query = $("#searchQuery").val().trim();
    console.log("query: " + query);

    $.ajax({
        url: "/vaccine-information",
        type: "GET",
        data: {action: "autoComplete", query: query},
        success: (response) => {
            console.log(response.suggestions);
            let listHtml = "";
            listHtml += `<div class="autocomplete-item"> ${query} </div>`;
            response.suggestions.forEach(item => {
                listHtml += `<div class="autocomplete-item">${item}</div>`;
            });
            $("#autocomplete-list").html(listHtml);
        },
        error: function () {
            alert("lỗi tìm sản phẩm");
        }
    });


    // Click vào item trong autocomplete
    $(document).on("click", ".autocomplete-item", function () {
        $("#searchQuery").val($(this).text());
        $("#autocomplete-list").html("");
    });


    // Ẩn danh sách khi click ra ngoài
    $(document).click(function (event) {
        if (!$(event.target).closest("#autocomplete-list, #searchQuery").length) {
            $("#autocomplete-list").html("");
        }

        document.querySelector(".autocomplete-container").hidden = true;

    });

}

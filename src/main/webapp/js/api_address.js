$(document).ready(function () {
    // Lấy danh sách tỉnh thành
    $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
        if (data_tinh.error === 0) {
            const provinceMenu = $(".province-menu");
            const provinces = data_tinh.data; // Lưu danh sách tỉnh vào biến

            provinces.forEach(function (province) {
                provinceMenu.append(`<li><a class="dropdown-item" data-id="${province.id}" href="#">${province.full_name}</a></li>`);
            });

            $(".province-menu .dropdown-item").click(function (e) {
                e.preventDefault(); // Ngăn hành vi mặc định
                const selectedProvince = $(this).text();
                const provinceId = $(this).data("id");

                $(".province-select").val(selectedProvince);
                $(".district-select").val("").removeClass("hidden"); // Hiển thị dropdown quận huyện
                $(".ward-select").val("").addClass("hidden"); // Ẩn dropdown phường xã
                $(".district-menu").empty(); // Xóa danh sách quận huyện cũ
                $(".ward-menu").empty(); // Xóa danh sách phường xã cũ

                // Lấy danh sách quận huyện
                $.getJSON(`https://esgoo.net/api-tinhthanh/2/${provinceId}.htm`, function (data_quan) {
                    if (data_quan.error === 0) {
                        const districtMenu = $(".district-menu");
                        const districts = data_quan.data; // Lưu danh sách quận huyện vào biến

                        if (districts.length > 0) {
                            districts.forEach(function (district) {
                                districtMenu.append(`<li><a class="dropdown-item" data-id="${district.id}" href="#">${district.full_name}</a></li>`);
                            });

                            $(".district-select").dropdown('toggle');

                            $(".district-menu .dropdown-item").click(function (e) {
                                e.preventDefault(); // Ngăn hành vi mặc định
                                const selectedDistrict = $(this).text();
                                const districtId = $(this).data("id");

                                $(".district-select").val(selectedDistrict);
                                $(".ward-select").val("").removeClass("hidden"); // Hiển thị dropdown phường xã
                                $(".ward-menu").empty(); // Xóa danh sách phường xã cũ

                                $(".district-select").dropdown('toggle');
                                $.getJSON(`https://esgoo.net/api-tinhthanh/3/${districtId}.htm`, function (data_phuong) {
                                    if (data_phuong.error === 0) {
                                        const wardMenu = $(".ward-menu");
                                        const wards = data_phuong.data; // Lưu danh sách phường xã vào biến

                                        if (wards.length > 0) {
                                            wards.forEach(function (ward) {
                                                wardMenu.append(`<li><a class="dropdown-item" data-id="${ward.id}" href="#">${ward.full_name}</a></li>`);
                                            });

                                            $(".ward-select").dropdown('toggle');
                                            $(".ward-menu .dropdown-item").click(function (e) {
                                                e.preventDefault(); // Ngăn hành vi mặc định
                                                const selectedWard = $(this).text();
                                                $(".ward-select").val(selectedWard);
                                                $(".ward-select").dropdown('toggle');
                                            });
                                        }
                                    }
                                });
                            });
                        }
                    }
                });
            });
        }
    })
});

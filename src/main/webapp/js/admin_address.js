$(document).ready(function () {
    function loadDistricts(provinceId, modal, selectedDistrictName = "", callback) {
        const districtMenu = modal.find(".district-menu");
        const districtSelect = modal.find(".district-select");
        const wardMenu = modal.find(".ward-menu");
        const wardSelect = modal.find(".ward-select");

        districtMenu.empty();
        districtSelect.attr("data-code", "");
        wardMenu.empty();
        wardSelect.attr("data-code", "");

        if (!provinceId) return;

        $.getJSON(`https://esgoo.net/api-tinhthanh/2/${provinceId}.htm`, function (res) {
            if (res.error === 0) {
                res.data.forEach(function (district) {
                    districtMenu.append(
                        `<li><a class="dropdown-item" data-id="${district.id}" href="#">${district.full_name}</a></li>`
                    );
                });

                if (selectedDistrictName) {
                    const matchedDistrict = res.data.find(d => d.full_name.trim() === selectedDistrictName.trim());
                    if (matchedDistrict) {
                        districtSelect.attr("data-code", matchedDistrict.id);
                        if (typeof callback === "function") callback(matchedDistrict.id);
                    }
                }
            }
        });
    }

    function loadWards(districtId, modal, selectedWardName = "") {
        const wardMenu = modal.find(".ward-menu");
        const wardSelect = modal.find(".ward-select");

        wardMenu.empty();
        wardSelect.attr("data-code", "");

        if (!districtId) return;

        $.getJSON(`https://esgoo.net/api-tinhthanh/3/${districtId}.htm`, function (res) {
            if (res.error === 0) {
                res.data.forEach(function (ward) {
                    wardMenu.append(
                        `<li><a class="dropdown-item" data-id="${ward.id}" href="#">${ward.full_name}</a></li>`
                    );
                });

                // Gán data-code phường nếu có
                if (selectedWardName) {
                    const matchedWard = res.data.find(w => w.full_name.trim() === selectedWardName.trim());
                    if (matchedWard) {
                        wardSelect.attr("data-code", matchedWard.id);
                    }
                }
            }
        });
    }

    $("body").on("show.bs.modal", ".modal", function () {
        const modal = $(this);
        const provinceName = modal.find(".province-select").val();
        const districtName = modal.find(".district-select").val();
        const wardName = modal.find(".ward-select").val();

        // load tinh (neu chua co danh sach)
        if (modal.find(".province-menu li").length === 0) {
            $.getJSON("https://esgoo.net/api-tinhthanh/1/0.htm", function (res) {
                if (res.error === 0) {
                    res.data.forEach(function (province) {
                        modal.find(".province-menu").append(
                            `<li><a class="dropdown-item" data-id="${province.id}" href="#">${province.full_name}</a></li>`
                        );
                    });

                    if (provinceName) {
                        const matchedProvince = res.data.find(p => p.full_name.trim() === provinceName.trim());
                        if (matchedProvince) {
                            modal.find(".province-select").attr("data-code", matchedProvince.id);
                            loadDistricts(matchedProvince.id, modal, districtName, function (districtId) {
                                loadWards(districtId, modal, wardName);
                            });
                        }
                    }
                }
            });
        } else {
            // Neu co tinh, thi load quan huyen, phuong xa
            if (provinceName) {
                const matchedProvince = modal.find(".province-menu li a").toArray().map(a => ({
                    id: $(a).data("id"),
                    name: $(a).text().trim()
                })).find(p => p.name === provinceName);

                if (matchedProvince) {
                    modal.find(".province-select").attr("data-code", matchedProvince.id);
                    loadDistricts(matchedProvince.id, modal, districtName, function (districtId) {
                        loadWards(districtId, modal, wardName);
                    });
                }
            }
        }
    });

    // xu ly chon tinh
    $(document).on("click", ".province-menu .dropdown-item", function (e) {
        e.preventDefault();
        const modal = $(this).closest(".modal");
        const provinceName = $(this).text();
        const provinceId = $(this).data("id");
        modal.find(".province-select").val(provinceName).attr("data-code", provinceId);
        modal.find(".province-code").val(provinceId);
        modal.find(".district-select").val("").attr("data-code", "");
        modal.find(".ward-select").val("").attr("data-code", "");
        modal.find(".district-menu").empty();
        modal.find(".ward-menu").empty();
        loadDistricts(provinceId, modal);
    });

    // xu ly quan huyen de load neu chua co
    $(document).on("click", ".district-select", function () {
        const modal = $(this).closest(".modal");
        const provinceId = modal.find(".province-select").attr("data-code");
        if (provinceId && modal.find(".district-menu li").length === 0) {
            loadDistricts(provinceId, modal);
        }
    });

    $(document).on("click", ".district-menu .dropdown-item", function (e) {
        e.preventDefault();
        const modal = $(this).closest(".modal");
        const districtName = $(this).text();
        const districtId = $(this).data("id");
        modal.find(".district-select").val(districtName).attr("data-code", districtId);
        modal.find(".district-code").val(districtId);
        modal.find(".ward-select").val("").attr("data-code", "");
        modal.find(".ward-menu").empty();
        loadWards(districtId, modal);
    });

    // xu ly phuong xa de load neu chua co
    $(document).on("click", ".ward-select", function () {
        const modal = $(this).closest(".modal");
        const districtId = modal.find(".district-select").attr("data-code");
        if (districtId && modal.find(".ward-menu li").length === 0) {
            loadWards(districtId, modal);
        }
    });

    $(document).on("click", ".ward-menu .dropdown-item", function (e) {
        e.preventDefault();
        const modal = $(this).closest(".modal");
        const wardName = $(this).text();
        const wardId = $(this).data("id");
        modal.find(".ward-select").val(wardName).attr("data-code", wardId);
        modal.find(".ward-code").val(wardId);
    });
});

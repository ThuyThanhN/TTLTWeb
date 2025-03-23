
const navList = document.querySelectorAll(".navbar-nav  .nav-item");

$(document).ready( () => {
    // thêm sự kiện cho nav bar và navbar-item
    navList.forEach(item => item.addEventListener("click", handleNavClick));
})


function handleNavClick(event) {

    // Xóa class "active" khỏi tất cả các mục
    navList.forEach(item => item.classList.remove("active"));

    // Thêm class "active" vào mục được nhấn
    event.currentTarget.classList.add("active");

}

function logActiveItems() {
    const activeItems = [...navList];

    console.log("Active items:", activeItems.length ? activeItems : "None");
}

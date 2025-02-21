const deleteModal = document.getElementById("deleteCart");
const confirmDeleteButton = document.getElementById("confirmDelete");

deleteModal.addEventListener("show.bs.modal", function (event) {
    const triggerLink = event.relatedTarget;
    const deleteUrl = triggerLink.getAttribute("data-url");
    const cartId = triggerLink.getAttribute("data-id");

    confirmDeleteButton.setAttribute("href", deleteUrl);

    const modalBody = deleteModal.querySelector(".modal-body");
    modalBody.textContent = 'Bạn có chắc chắn muốn xóa lịch tiêm ' + cartId + '?';
});
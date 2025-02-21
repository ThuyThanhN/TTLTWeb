const listItems = document.querySelectorAll('.list-group-item');

listItems.forEach(item => {
    item.addEventListener('click', () => {
        // gia tri cua target
        const target = item.dataset.target;

        // Xoa active
        listItems.forEach(listItem => listItem.classList.remove('active'));

        // Them active voi target tuong ungs
        document.querySelectorAll(`.list-group-item[data-target="${target}"]`).forEach(activeItem => {
            activeItem.classList.add('active');
        });
    });
});



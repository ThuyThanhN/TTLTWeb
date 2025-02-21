let backtop = document.querySelector('#backtop');
backtop.addEventListener('click', () => {
    window.scrollTo({
        top: 0,
        left: 0
    });
})

$(document).ready(function() {
    const multipleItemCarousel = document.querySelector('#carouselExampleControls1');
    const carouselInner = $('#carouselExampleControls1 .carousel-inner');
    const prevControl = $('#carouselExampleControls1 .carousel-control-prev');
    const nextControl = $('#carouselExampleControls1 .carousel-control-next');
    const carouselItems = $('#carouselExampleControls1 .carousel-item');

    if (window.matchMedia("(min-width: 576px)").matches) {
        const carousel = new bootstrap.Carousel(multipleItemCarousel, {
            interval: false
        });

        var carouselWidth = carouselInner[0].scrollWidth; // Sử dụng biến carouselInner
        var cardWidth = carouselItems.width(); // Sử dụng biến carouselItems
        var scrollPosition = 0;

        function checkControls() {
            scrollPosition <= 0 ? prevControl.hide() : prevControl.show();
            scrollPosition >= (carouselWidth - cardWidth * 4) ? nextControl.hide() : nextControl.show();
        }

        nextControl.on('click', function() {
            if (scrollPosition < (carouselWidth - cardWidth * 4)) {
                scrollPosition += cardWidth;
                carouselInner.animate({ scrollLeft: scrollPosition }, 600);
                checkControls();
            }
        });

        prevControl.on('click', function() {
            if (scrollPosition > 0) {
                scrollPosition -= cardWidth;
                carouselInner.animate({ scrollLeft: scrollPosition }, 600);
                checkControls();
            }
        });
    } else {
        $(multipleItemCarousel).addClass('slide');
        prevControl.show();
        nextControl.show();
    }
});




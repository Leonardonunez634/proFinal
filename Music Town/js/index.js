const d = document;


/*** <====== MENU DE HAMBURGESA =======> ***/
const $navToggle = d.querySelector(".nav-toggle");
const $navMenu = d.querySelector(".nav-menu");

$navToggle.addEventListener("click", () => {
    $navMenu.classList.toggle("nav-menu_visible");
    $navToggle.classList.toggle("is-active");

    if($navMenu.classList.contains("nav-menu_visible")){
        $navToggle.setAttribute("aria-label","Cerrar menú");
    } else {
        $navToggle.setAttribute("aria-label","Abrir menú");
    }
});

d.addEventListener("click", e => {
    if(e.target.matches(".nav-menu-item a")) {
        $navMenu.classList.remove("nav-menu_visible");
        $navToggle.classList.remove("is-active");
    }
});

/*** <====== CARRUSEL =======> ***/
const $nextBtn = d.querySelector(".slider-btns .next"),
    $prevBtn = d.querySelector(".slider-btns .prev"),
    $slides = d.querySelectorAll(".slider-slide");

let i = 0;

const auto = () => {
    setInterval(() => {
        $slides[i].classList.remove("active");
        i++;
        
        if(i >= $slides.length) i = 0;

        $slides[i].classList.add("active");
    }, 6000);
}

auto();

d.addEventListener("click", e => {
    if(e.target === $prevBtn) {
        e.preventDefault();
        $slides[i].classList.remove("active");
        i--;
        
        if(i < 0) i = $slides.length - 1;

        $slides[i].classList.add("active");
    }

    if(e.target === $nextBtn) {
        e.preventDefault();
        $slides[i].classList.remove("active");
        i++;
        
        if(i >= $slides.length) i = 0;

        $slides[i].classList.add("active");
    }
});


/*** <====== MENU DE HAMBURGESA =======> ***/
const $navToggle = document.querySelector(".nav-toggle");
const $navMenu = document.querySelector(".nav-menu");

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


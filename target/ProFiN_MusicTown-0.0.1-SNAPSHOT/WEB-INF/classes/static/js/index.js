
/*** <====== CARRUSEL =======> ***/
const $nextBtn = document.querySelector(".slider-btns .next"),
    $prevBtn = document.querySelector(".slider-btns .prev"),
    $slides = document.querySelectorAll(".slider-slide");

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

document.addEventListener("click", e => {
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

/*** <====== BOTON PARA CERRAR LAS ALERTAS =======> 
const botonAlerta = document.querySelector(".contenedor-boton"); //busco al elemento Contenedor boton (x Clase)

botonAlerta.addEventListener("click" , evt => {
    let alertaContenedor = botonAlerta.parentElement;//aca se selecciona al elemento padre de botonAlerta
    let textoContenedor = botonAlerta.previousElementSibling;
    
    botonAlerta.style.display = "none";
    textoContenedor.style.display = "none";//aca hago que tanto el texto como el boton desaparezcan
    alertaContenedor.style.display="none";//aca se le asigna al elemento padre html de botonAlerta un alto de 0px 
}); ***/


const $menuBtn = document.getElementById("mobile-menu");
const $cerrarBtn = document.getElementById("cerrar-menu");
const $sidebar = document.querySelector(".sidebar");

if($menuBtn) {
    $menuBtn.addEventListener("click", () => {
        $sidebar.classList.add('mostrar');
    });
}

if($cerrarBtn) {
    $cerrarBtn.addEventListener('click', () => {
        $sidebar.classList.add('ocultar');
        
        setTimeout(() => {
            $sidebar.classList.remove('mostrar');
            $sidebar.classList.remove('ocultar');
        }, 500);
    })
}


// Elimina la clase de mostrar en un tamaño de tablet y mayores
const anchoPantalla = document.body.clientWidth;

window.addEventListener('resize', () => {
    const anchoPantalla = document.body.clientWidth;
    if(anchoPantalla >= 768) {
        $sidebar.classList.remove('mostrar');
    }
});
/*** <====== VALIDACIONES DE FORMULARIO =======> ***/
function contactFormValidations() {  
    const $form = document.querySelector(".contact-form"),
        $inputs = document.querySelectorAll(".contact-form [required]"); // capturar los inputs con el atributo required

    // Agregar un span debajo de cada input
    $inputs.forEach(input => {
        const $span = document.createElement("SPAN"); 
        $span.id = input.name;
        $span.textContent = input.title;
        $span.classList.add("contact-form-error", "none");
        input.insertAdjacentElement("afterend", $span);
    });

    document.addEventListener("keyup" , e => {
        if(e.target.matches(".contact-form [required]")) {
            let $input = e.target,
            pattern = $input.pattern || $input.dataset.pattern;
            let inpuName = document.getElementById($input.name);
 
            if(pattern && $input.value !== "") {
                let regex = new RegExp(pattern);
                return !regex.exec($input.value)
                    ? inpuName.classList.add("is-active") // $input.name id del span
                    : inpuName.classList.remove("is-active") 
            }

            if(!pattern) {
                return $input.value === ""
                    ? document.getElementById($input.name).classList.add("is-active") // $input.name id del span
                    : document.getElementById($input.name).classList.remove("is-active") 
            }
        }
    });
}

document.addEventListener("DOMContentLoaded", e => {
    contactFormValidations();
});

document.addEventListener("DOMContentLoaded", e => {
    const includeHTML = (el, url) => {
        const xhr = new XMLHttpRequest();

        xhr.addEventListener("readystatechange", e => {
          if (xhr.readyState !== 4) return;
  
          if (xhr.status >= 200 && xhr.status < 300) {
            el.outerHTML = xhr.responseText; // outerHTML reemplaza el codigo a diferencia de innerHTML
          } else {
            let message = xhr.statusText || "Error al cargar el archivo, verifique que esté haciendo la peticion en http o https";
            el.outerHTML = `<div><p>Error ${xhr.status}: ${message}</p></div>`;
          }
        });

        xhr.open("GET", url);
        xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
        xhr.send();
    };

    document
        .querySelectorAll("[data-include]")
        .forEach(el => includeHTML(el, el.getAttribute("data-include")));
});
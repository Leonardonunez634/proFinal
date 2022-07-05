/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicTown.ver1.controladores;

import MusicTown.ver1.Entidades.Producto;
import MusicTown.ver1.repositorio.ProductoRepositorio;
import MusicTown.ver1.servicio.CarritoDeComprasServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuarioLog")
public class ControlCompras {
    /*Poner todos los metodos que necesitamos que el usuario este logeado*/
    @Autowired
    private CarritoDeComprasServicio carritoServi;

    @Autowired
    private ProductoRepositorio repoProducto;

    @GetMapping("/listarProductosCompra")
    public String listarProductosCompra(ModelMap modelo, @RequestParam String IdProducto, HttpSession session, @RequestParam Integer cantidadDeProductos) {
        Producto producto = repoProducto.buscarPorId(IdProducto);

        if ((List<Producto>) session.getAttribute("listaProducto") == null) {
            List<Producto> listaProductoCompra = new ArrayList();
            listaProductoCompra.add(producto);
            session.setAttribute("listaProducto", listaProductoCompra);
            //Aquí se llamaría al metodo realizarCompra??
            //Corroborar si los datos que se pasasn por parametro es correcto
        } else {
            List<Producto> listaProductoCompra = (List<Producto>) session.getAttribute("listaProducto");
            listaProductoCompra.add(producto);
            session.setAttribute("listaProducto", listaProductoCompra);
            //Aquí se llamaría al metodo realizarCompra??
        }

        return "redirect:/producto/catalogo";
    }

    @GetMapping("/mostrarCompra")
    public String mostrarCompra(ModelMap modelo, HttpSession session) {

        modelo.addAttribute("idLista", (List<String>) session.getAttribute("idLista"));

        return "carritov2.html";
    }
    
}

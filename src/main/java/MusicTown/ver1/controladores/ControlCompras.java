package MusicTown.ver1.controladores;

import MusicTown.ver1.Entidades.Producto;
import MusicTown.ver1.Entidades.Usuario;
import MusicTown.ver1.errores.ErrorServicio;
import MusicTown.ver1.repositorio.ProductoRepositorio;
import MusicTown.ver1.servicio.CarritoDeComprasServicio;
import MusicTown.ver1.servicio.ProductoServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private ProductoServicio servisProducto;

    @GetMapping("/listarProductosCompra/{IdProducto}")
    public String listarProductosCompra(ModelMap modelo, @PathVariable String IdProducto, HttpSession session /*@RequestParam Integer cantidadDeProductos*/) {
        Producto idPro = repoProducto.buscarPorId(IdProducto);
        System.out.println("producto encontrado");

        if ((List<Producto>) session.getAttribute("listaProductoId") == null) {
            List<Producto> listaProductoCompra = new ArrayList();
            listaProductoCompra.add(idPro);
            System.out.println("producto agregado");
            session.setAttribute("listaProductoId", listaProductoCompra);
        } else {
            List<Producto> listaProductoCompra = (List<Producto>) session.getAttribute("listaProductoId");
            listaProductoCompra.add(idPro);
            session.setAttribute("listaProductoId", listaProductoCompra);
        }

        return "redirect:/producto/catalogo";
    }

    @GetMapping("/mostrarCompra")
    public String mostrarCompra(ModelMap modelo, HttpSession session) {

        List<Producto> listaProductoCompra = (List<Producto>) session.getAttribute("listaProductoId");

        modelo.addAttribute("listaProducto", servisProducto.buscarProd(listaProductoCompra));
        try {
            modelo.addAttribute("precioFinal", carritoServi.calcularCompra(servisProducto.buscarProd(listaProductoCompra), 1));

        } catch (ErrorServicio ex) {
            System.out.println(ex);;
        }

        return "/pages/carritov2.html";
    }
    @PreAuthorize ("hasRole('USUARIOREGISTRADO')")
    @PostMapping("/confirmarCompra")
    public String confirmarCompra(ModelMap modelo, HttpSession session, @RequestParam("cantidadDeProductos") int cantidadProducto) {

        List<Producto> listaDeProductos = servisProducto.buscarProd((List<Producto>) session.getAttribute("listaProductoId"));
        Usuario usuarioCompra = (Usuario) session.getAttribute("datosUsuario");
        try {
            carritoServi.confirmarCompra(listaDeProductos, usuarioCompra, 1);
            for (Producto aux : listaDeProductos) {
                servisProducto.ControlStock(aux , (cantidadProducto * (-1)));
            }

            session.setAttribute("listaProductoId", null);
            modelo.addAttribute("exito", "Todo ok");
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
        }

        return "redirect:/producto/catalogo";
    }

}

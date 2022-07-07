package MusicTown.ver1.controladores;

import MusicTown.ver1.errores.ErrorServicio;
import MusicTown.ver1.Entidades.Usuario;
import MusicTown.ver1.servicio.UsuarioServicios;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Usuario")
public class controladorUsuario {

    @Autowired
    private UsuarioServicios usuarioServicios;

    

    @GetMapping("/ModificarUsuario")
    public String editarUsuario(HttpServletRequest request,
            ModelMap modelo) {
        HttpSession sesionUsuario = request.getSession();
        Usuario us = (Usuario) sesionUsuario.getAttribute("datosUsuario");
        try {
            modelo.addAttribute("usuarioMostrar", usuarioServicios.buscarPorId(us.getId()));
            return "auth/perfil.html";
        } catch (Exception e) {
            modelo.addAttribute("error", e);
            return "redirect:/iniciarSesion";
        }

    }

    @PostMapping("/actualizar-perfil")
    public String modificar(RedirectAttributes modelo, 
            HttpServletRequest request, 
            @RequestParam (value="nombre" , required = false) String nombre, 
            @RequestParam (value="email" , required = false)String mail, 
            @RequestParam (value="clave" , required = false) String clave, 
            @RequestParam (value="clave1" , required = false) String clave1) {
        HttpSession sesionUsuario = request.getSession();
        Usuario us = (Usuario) sesionUsuario.getAttribute("datosUsuario");
        try {
            //usuario = usuarioServicios.buscarPorId(idUsuario);
            System.out.println("nombre = " + nombre + " mail = " + mail + "clave = " + clave);
            System.out.println("HOLA ENTRANDO A MODIFICAR");
            usuarioServicios.modificar(us, nombre, mail, clave, clave1);
            return "redirect:/Usuario/ModificarUsuario";
        } catch (ErrorServicio ex) {
            modelo.addAttribute("error", ex.getMessage());
            return "redirect:/Usuario/ModificarUsuario";
        }
    }

}

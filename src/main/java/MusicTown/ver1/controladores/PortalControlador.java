package MusicTown.ver1.controladores;

import MusicTown.ver1.MailSender.MailNotificaciones;
import MusicTown.ver1.errores.ErrorServicio;
import MusicTown.ver1.servicio.ProductoServicio;
import MusicTown.ver1.servicio.UsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/")
public class PortalControlador {
    
    /*Conecc con servis*/
    @Autowired
    ProductoServicio servisProduc;
    @Autowired
    UsuarioServicios servisUsuario;
    @Autowired
    MailNotificaciones mailNotis;

    @GetMapping("/inicio")
    public String index() {
        return "index.html";
    }

    @GetMapping("/iniciarSesion")
    public String iniciarSesion(ModelMap insertar ,
            @RequestParam (value="error" , required=false) String error) {
        if (error != null){
            insertar.addAttribute("error" , "Usuario y/o Contraseña incorrectos"); 
            return "auth/login.html";
        }else{
            return "auth/login.html";
        }
        
    }

@GetMapping("/registrar")
    public String crearUsuario() {
        return "auth/registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo,
            RedirectAttributes redirect,
            @RequestParam String nombre,
            @RequestParam String clave,
            @RequestParam String clave1,
            @RequestParam String mail) {

        try {
            servisUsuario.registrar(nombre, mail, clave, clave1);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("mail", mail);
            return "auth/registro.html";
        }
        redirect.addFlashAttribute("exito", "El usuario fue registrado con exito");
        return "redirect:/iniciarSesion";
    }
    
    @PostMapping("/enviarMail")
    public String enviarMail(RedirectAttributes notificacion,
            @RequestParam("nombre") String nombreUsuario, 
            @RequestParam("email") String emailUsuario, 
            @RequestParam("asuntoMail") String asunMail,
            @RequestParam("contenidoMensaje") String contMensaje){
        try {
            mailNotis.mailsender("musictownservice@gmail.com", asunMail, contMensaje + " Remitente = " + nombreUsuario + " email = " + emailUsuario); //enviamos un mail a nuestro mail con el asunto del cliente
            mailNotis.mailsender(emailUsuario, "Recibimos tu consulta con el Asunto : "+asunMail, "Gracias por comunicarte con MusicTown, en la brevedad estaremos en contacto con vos. Atte : Equipo Music");
            //Enviamos un mail de confirmacion al usuario
            notificacion.addFlashAttribute("exito" , "mail enviado con exito");
        } catch (Exception e) {
            
            notificacion.addFlashAttribute("error" , "Error en el envio : " + e);
        }
            
        return"redirect:/";
    }
}

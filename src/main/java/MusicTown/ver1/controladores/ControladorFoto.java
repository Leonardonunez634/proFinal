
package MusicTown.ver1.controladores;

import MusicTown.ver1.servicio.FotoServicio;
import MusicTown.ver1.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class ControladorFoto {
    /*LLamadas a servis*/
    @Autowired
    private FotoServicio servisFoto;
    @Autowired
    private ProductoServicio servisProducto;
    
    @GetMapping("/mostrarFoto/{id}")
    public ResponseEntity<byte[]> mostrarFoto(@PathVariable("id") String fotoId ,
                              ModelMap modelo){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>( servisFoto.buscarFotoPorID(fotoId).getContenido(), headers , HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        
   }
}

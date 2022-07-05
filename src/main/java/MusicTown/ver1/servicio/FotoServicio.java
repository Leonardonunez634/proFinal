
package MusicTown.ver1.servicio;

import MusicTown.ver1.Entidades.Foto;
import MusicTown.ver1.errores.ErrorServicio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import MusicTown.ver1.repositorio.FotoRepositorio;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;
    @Transactional
    public Collection<Foto> guardar(Collection<MultipartFile> archivos) throws ErrorServicio {
        Collection<Foto> nuevasFotos = new ArrayList<>();
        if (archivos != null) {
            for (MultipartFile archivo : archivos) {
                try {
                    Foto foto = new Foto();
                    foto.setMime(archivo.getContentType());
                    foto.setNombre(archivo.getName());
                    foto.setContenido(archivo.getBytes());
                    fotoRepositorio.save(foto);
                    
                    nuevasFotos.add(foto);
                 
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            return nuevasFotos;
        }
        return null;
    }
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
     return null;   
    }
    
    /*Agregado Nuevo*/
    public void EliminarFoto(String IDfotoEliminar){
        Optional<Foto> fotoAntigua = fotoRepositorio.findById(IDfotoEliminar);
        if(fotoAntigua.isPresent()){            
            try {
                               
                fotoRepositorio.deleteById(IDfotoEliminar);
            } catch (Exception e) {
                System.out.println(e);
            }       
        }
    }
    
    /*Agregado Nuevo*/
    public Foto buscarFotoPorID(String IDfoto)throws ErrorServicio{
        Optional<Foto> foto = fotoRepositorio.findById(IDfoto);
        if(foto.isPresent()){
            Foto fotoReturn = foto.get();
            return fotoReturn;
        }else{
            throw new ErrorServicio("no se encontro el Producto");
        }
    }
    
    /*Servicio destinado a recolectar los MULTIPARTFILES*/
    public Collection<byte[]> buscarMultipartFiles(Collection<Foto> coleccionFotos){
        Collection<byte[]> archivos = new ArrayList();
        for(Foto foto : coleccionFotos){
            archivos.add(foto.getContenido());
        }
        return archivos;
    }
}

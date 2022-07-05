
package MusicTown.ver1.servicio;

import MusicTown.ver1.Entidades.Marca;
import MusicTown.ver1.errores.ErrorServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import MusicTown.ver1.repositorio.MarcaRepositorio;
import org.springframework.stereotype.Service;


@Service
public class MarcaServicio {
    @Autowired
    private MarcaRepositorio MarcaRepo;

    //========>> Inicio de servicio <<========
    
    /* --- Se crea una nueva Entidad Marca y se persiste --- */
    @Transactional 
    public void nuevaMarca(String nombreMarca) throws Exception{
        try {
            Marca NuevaEditorial = new Marca(nombreMarca);
            validarDatos(NuevaEditorial);
            Optional<Marca> opEdit = MarcaRepo.buscarPorNombre(nombreMarca); //Se busca una marca igual antes de persistir
            if (opEdit.isPresent()) {
                System.out.println("YA EXISTE UNA EDITORIAL CON ESE NOMBRE - ECONOMISA MEMORIA");
            } else {
                MarcaRepo.save(NuevaEditorial);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }
    /* --- Se Recupera una Entidad Marca de la Base de Datos, se modifica el attr "AltaMarca" y se persiste --- */
    @Transactional(rollbackFor = {Exception.class})
    public void desactivarMarca(String idMarca) throws ErrorServicio{
        Optional<Marca> marcaEditar = MarcaRepo.findById(idMarca);
        
        if(marcaEditar.isPresent()){
            Marca marcaEditarFinal = marcaEditar.get();
            marcaEditarFinal.setAltaMarca(false);
            MarcaRepo.save(marcaEditarFinal); 
        }else{
            throw new ErrorServicio("No se encontro esa Marca");
        } 
    }
    /* --- Se recuperan datos de la vista (Los parametros) y se modifican a una Entidad Marca Existente --- */
    @Transactional(rollbackFor = {Exception.class})
    public void editarMarca(String idMarca , 
                                String nombreActualizar) throws ErrorServicio{
        
        
        Optional<Marca> marcaEditar = MarcaRepo.findById(idMarca);
        
        if(marcaEditar.isPresent()){
            try {
                Marca marcaParaEditar = marcaEditar.get();
                marcaParaEditar.setNombreMarca(nombreActualizar);
                validarDatos(marcaParaEditar);
                MarcaRepo.save(marcaParaEditar);
            } catch (Exception e) {
                System.out.println(e);
                throw new ErrorServicio("Error al validar la marca");
            }  
        }
        //Ya que el id = al Id que recuperamos desde la pagina
        // JPA remplaza los datos (Ya existe en la BB.DD)
    }
    /* --- Se buscan todas las editoriales activas y se las devuelve para mostrar en la vista --- */
    @Transactional(readOnly = true)
    public List<Marca> mostrarTodasLasEditoriales() throws Exception{
        try {
               List<Marca> listaReturn = MarcaRepo.mostrarMarcasActivas(); //medoto con Where
               return listaReturn;
        } catch (Exception e) {
            System.out.println(e);
            throw new ErrorServicio("No se encontraron marcas");
        }
    }
    
    public Marca traerUnaMarcaPorNombre(String nombreMarca){
        try {
            Optional<Marca> marcaOP = MarcaRepo.buscarPorNombre(nombreMarca);
            if (marcaOP.isPresent()) {
                Marca marcaReturn = marcaOP.get();
                return marcaReturn;
            } else {
                System.out.println("No se encontro la marca");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    /* --- Metodo interno para corroborar la carga de datos --- */
    private void validarDatos(Marca marca) throws Exception {
        if (marca.getNombreMarca().trim().isEmpty() || marca.getNombreMarca().length() < 3) { //Con este metodo corroboro que no exista un "  " como autor y que no sea menor a 3
            if (marca.getNombreMarca().trim().isEmpty()) {
                throw new ErrorServicio("El campo no puede ser nulo");
            } else {
                throw new ErrorServicio("La Marca debe tener 3 o mas Caracteres");
            }
        }
    }
}

package MusicTown.ver1.servicio;


import MusicTown.ver1.Entidades.Foto;
import MusicTown.ver1.Entidades.Marca;
import MusicTown.ver1.Entidades.Producto;
import MusicTown.ver1.Entidades.Usuario;
import MusicTown.ver1.errores.ErrorServicio;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import MusicTown.ver1.repositorio.ProductoRepositorio;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServicio {
    //Llamados A repositorios
    @Autowired
    ProductoRepositorio repoProducto;
    @Autowired
    MarcaServicio marcaServis;
    @Autowired
    FotoServicio fotoServis;
    /* METODO PARA CREAR UN NUEVO PRODUCTO*/
    @Transactional(rollbackFor = {Exception.class})
    public void crearNuevoProducto(String NombreProducto, Collection<MultipartFile> fotos, 
                                   int CantidadProducto , float PrecioProducto ,
                                   String marcaProducto, Usuario usuario) throws ErrorServicio{
        
       Producto nuevoProducto = validarProducto(NombreProducto, fotos, CantidadProducto, PrecioProducto, marcaProducto, usuario);
       
       repoProducto.save(nuevoProducto);
    }
    /* Metodo que recopila todo Y actualiza los datos */
    @Transactional
    public void editarProducto(String NombreProducto, Collection<MultipartFile> fotos , 
                                   int CantidadProducto , float PrecioProducto ,
                                   Marca marcaProducto , String IdProducto) throws ErrorServicio{
        Optional<Producto> productoEditar = repoProducto.findById(IdProducto);
        
        if(productoEditar.isPresent()){
            Producto productoEditarFinal = productoEditar.get();
            
            productoEditarFinal.setNombreProducto(NombreProducto);
            productoEditarFinal.setPrecioProducto(PrecioProducto);
            productoEditarFinal.setStockProducto(CantidadProducto);
            productoEditarFinal.setMarcaProducto(marcaProducto);
            
            Collection<Foto> fotosNuevas = new ArrayList();
            if(!(fotos.isEmpty())){
               fotosNuevas = fotoServis.guardar(fotos);
            }
            productoEditarFinal.setFotoProducto(fotosNuevas);
            
            repoProducto.save(productoEditarFinal);
        }else{
            throw new ErrorServicio("No se encontro el producto a editar, vuelva a intentarlo");
        }
    }
    
    // * Dar de baja cualquier producto
    @Transactional
    public void DarDeBajaProducto(String IDproductoBaja){//agregar funcion para dar de baja producto sin stock
        Optional<Producto> productoBaja = repoProducto.findById(IDproductoBaja);
        if(productoBaja.isPresent()){
            Producto productoBajaFinal = productoBaja.get();
            productoBajaFinal.setAlta(false);
            repoProducto.save(productoBajaFinal);
        }  
    }
    
    /* Control de Stock = con este metodo se pueden agregar o restar Stock
    (Tambien busca ser un SUMAR RESTAR cuando algun cliente compre 1 o + productos)*/
    @Transactional
    public void ControlStock(String IdProductoStock , int Cantidad){
       Optional<Producto> Producto = repoProducto.findById(IdProductoStock);
        if(Producto.isPresent()){
            Producto ProductoStock = Producto.get();
            ProductoStock.setStockProducto(ProductoStock.getStockProducto() + Cantidad);
            /* Si el usuario compro o se quieren sacar productos uno tiene que Pasrle por parametro un numero Negativo
            multiplicarlo x-1 */
        }  
    }
    /*========== Buscar un producto x ID*/
    public Producto buscarUnProductoXID(String idProducto) throws ErrorServicio{
        Optional <Producto> productoOp = repoProducto.findById(idProducto);
        if(productoOp.isPresent()){
            Producto productoReturn = productoOp.get();
            return productoReturn;
        }else{
            throw new ErrorServicio("no se encontro el Producto");
        }
    }
    
    //=================== Buscar todos los productos ======
    public List<Producto> buscarTodosProdutos(){
        List<Producto> productosList = new ArrayList<>();
        productosList = repoProducto.findAll();
        
        return productosList;
    }
    //======== Validar productos 
    private Producto validarProducto(String NombreProducto, Collection<MultipartFile> fotos , 
                                   int CantidadProducto , float PrecioProducto ,
                                   String marcaProducto, Usuario usuario) throws ErrorServicio{
        
        if (NombreProducto == null || NombreProducto.trim().isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        
        if (CantidadProducto <= 0 ) {
            throw new ErrorServicio("La cantidad ingresada tiene que ser Superior o igual a 1");
        }
        if (PrecioProducto <= 0 ) {
            throw new ErrorServicio("Tenes que ingresar un precio");
        }
        if (marcaProducto == null || marcaProducto.trim().isEmpty()) {
            throw new ErrorServicio("La marca no se ingreso");
        }else{
            try {
                marcaServis.nuevaMarca(marcaProducto);
            } catch (Exception e) {
                System.out.println(e);
            }
        } 
        
        /*Agregado Nuevo*/
        if(fotos == null){
            Producto productoValidado = new Producto(NombreProducto, CantidadProducto, PrecioProducto, marcaServis.traerUnaMarcaPorNombre(marcaProducto), usuario);
            return productoValidado;
        }else{
            Collection<Foto> coleccFotos = new ArrayList();
            try {    
                coleccFotos = fotoServis.guardar(fotos);
                Producto productoValidado = new Producto(NombreProducto, coleccFotos, CantidadProducto, PrecioProducto, marcaServis.traerUnaMarcaPorNombre(marcaProducto), usuario);
                return productoValidado;
            } catch (Exception e) {
                System.out.println(e + " Se a finalizado la ejecucion");
                return null;
            }
            
        }
    }
}

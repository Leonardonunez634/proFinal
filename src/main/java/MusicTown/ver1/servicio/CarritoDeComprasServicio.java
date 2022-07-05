/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicTown.ver1.servicio;
import MusicTown.ver1.Entidades.CarritoDeCompras;
import MusicTown.ver1.Entidades.Producto;
import MusicTown.ver1.Entidades.Usuario;
import MusicTown.ver1.errores.ErrorServicio;
import MusicTown.ver1.repositorio.CarritoDeComprasRepositorio;
import MusicTown.ver1.repositorio.ProductoRepositorio;
import MusicTown.ver1.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoDeComprasServicio {

    @Autowired
    private CarritoDeComprasRepositorio repoCarrito;

    @Autowired
    private UsuarioRepositorio repoUsuario;

    @Autowired
    private ProductoRepositorio repoProducto;


    @Transactional(propagation = Propagation.NESTED)
    public void realizarCompra2(String IdProducto, String NombreProducto, String idUsuarioComprador, Integer cantidadDeProductos) throws ErrorServicio {


        CarritoDeCompras carrito = new CarritoDeCompras();
        //Se registra fecha y hora de la compra
        carrito.setFechaDeCompra(new Date());

        //Se verifica si el comprador esta registrado y se registra en la compra
        Optional<Usuario> respuesta = repoUsuario.findById(idUsuarioComprador);
        if (respuesta.isPresent()) {
//            Usuario usuarioComprador = respuesta.get(); Probar esto si no llega a funcionar lo de abajo
            carrito.setUsuarioComprador(respuesta.get());
        } else {
            throw new ErrorServicio("Usted no está registrado.");
        }

        Optional<Producto> respuesta2 = repoProducto.findById(IdProducto);
        if (respuesta2.isPresent()) {
            Producto p = new Producto();
            //Se valida que haya la cantidad de productos disponibles en stock
            if (p.getStockProducto() >= cantidadDeProductos) {
//            p.setIdProducto(IdProducto);
//            p.setNombreProducto(NombreProducto);
//            carrito.setProductosEnCarrito((List<Producto>) p);

                carrito.setCantidadDeProductos(cantidadDeProductos);
                carrito.setProductosEnCarrito((List<Producto>) respuesta2.get());
                calcularCompra(cantidadDeProductos);
            } else {
                throw new ErrorServicio("La cantidad de productos solicitada no está disponible en stock.");
            }
        } else {
            throw new ErrorServicio("El producto no está registrado en nuestra base de datos.");
        }
        repoCarrito.save(carrito);
    }

    //Metodo que confima la compra y guarda los datos de la compra en la BD.
    @Transactional(propagation = Propagation.NESTED) //Pasarle un String idUsuario
    public Float confirmarCompra(List<Producto> listaProductoCompra, String idUsuario, Integer cantidadDeProductos) throws ErrorServicio {

        CarritoDeCompras carrito = new CarritoDeCompras();
        List<Producto> productoAgregado = new ArrayList();

        //Se registra fecha y hora de la compra
        carrito.setFechaDeCompra(new Date());

        Optional<Usuario> respuesta = repoUsuario.findById(idUsuario); //Esta hecho así para guardar el id del usuario en la compra que realizó.
        if (respuesta.isPresent()) {
//            Usuario usuarioComprador = respuesta.get(); Probar esto si no llega a funcionar lo de abajo
            carrito.setUsuarioComprador(respuesta.get());
        }
//        } else {
//            throw new ErrorServicio("Usted no está registrado.");
//        }

        //Un fore para cada producto que trae la lista y se setean en la lista del carrito
        for (Producto aux : listaProductoCompra) {
            productoAgregado.add(aux);
        }

        carrito.setProductosEnCarrito(productoAgregado);
        calcularCompra(cantidadDeProductos);

        //Esto ya no serviría
//        Optional<Producto> respuesta2 = repoProducto.findById(IdProducto);
//        if (respuesta2.isPresent()) {
//            Producto p = new Producto();
//            //Se valida que haya la cantidad de productos disponibles en stock
//            if (p.getStockProducto() >= cantidadDeProductos) {  //Esto debería ir en el controlador?
////            p.setIdProducto(IdProducto);
////            p.setNombreProducto(NombreProducto);
////            carrito.setProductosEnCarrito((List<Producto>) p);
//
//                carrito.setCantidadDeProductos(cantidadDeProductos);
//                carrito.setProductosEnCarrito((List<Producto>) respuesta2.get());
//                calcularCompra(IdProducto, cantidadDeProductos);
//
//            } else {
//                throw new ErrorServicio("La cantidad de productos solicitada no está disponible en stock.");
//            }
//
//        } else {
//            throw new ErrorServicio("El producto no está registrado en nuestra base de datos.");
//        }
        repoCarrito.save(carrito);

        //Debería ser void y que no retorne nada?? Y que se llame al importeTotal desde el controlador??
        return carrito.getImporteTotal();
    }

    /*Metodo para calcular la compra y registrar los datos de la compra parcialmente. No se guardan en la BD. Se devuelve el total parcial de la compra, 
      es decir el importe hasta el momento, antes de aceptar la compra, por las dudas que quiera sacar, agregar algun producto, o cancelar la compra.*/
    @Transactional(propagation = Propagation.NESTED) //Pasarle un String idUsuario
    public Float realizarCompra(List<Producto> listaProductoCompra, Integer cantidadDeProductos) throws ErrorServicio {

        CarritoDeCompras carrito = new CarritoDeCompras();
        List<Producto> productoAgregado = new ArrayList();

        //Un fore para cada producto que trae la lista
        for (Producto aux : listaProductoCompra) {

            Optional<Producto> respuesta2 = repoProducto.findById(aux.toString());
            //Se valida el id del producto
            if (respuesta2.isPresent()) { //Estas validaciones estan bien?? O deberían ir en el controlador??
                Producto p = new Producto();
                if (p.getStockProducto() >= cantidadDeProductos) {

                    p = respuesta2.get();
                    productoAgregado.add(p);
                } else {
                    throw new ErrorServicio("La cantidad de productos solicitada no está disponible en stock.");
                }
            } else {
                throw new ErrorServicio("El producto no está registrado en nuestra base de datos.");
            }
        }

        carrito.setProductosEnCarrito(productoAgregado);
        calcularCompra(cantidadDeProductos);

        //Se retorna el total que lleva en la compra hasta el momento
        return carrito.getImporteTotal();
    }

    //Debo pasarle por parametro la lista también o está bien llamada de esta forma??
    //Metodo que calcula la suma de los precios de los productos.
    public void calcularCompra(Integer cantidadDeProductos) throws ErrorServicio {
        Producto producto = new Producto();
        CarritoDeCompras productoComprado = new CarritoDeCompras();

        float total = 0;

        for (Producto aux : productoComprado.getProductosEnCarrito()) {

            total = total + (producto.getPrecioProducto() * cantidadDeProductos);
        }

        productoComprado.setImporteTotal(total);
    }

    //Metodo para mostrar todas las compras realizadas
    @Transactional(readOnly = true)
    public List<CarritoDeCompras> mostrarTodasCompras() {
        return repoCarrito.findAll();
    }

    //Metodo para buscar las compras realizadas por un mismo usuario.
    @Transactional(readOnly = true)
    public List<CarritoDeCompras> buscarComprasPorUsuario(String id) throws ErrorServicio {
        List<CarritoDeCompras> busquedaComprador = repoCarrito.buscarComprasPorUsuario(id);

        //Se valida que la lista que devuelve no este vacía.
        if (busquedaComprador != null) {
            return busquedaComprador;
        } else {
            throw new ErrorServicio("No hay compras realizadas por este usuario.");
        }
    }


    //Metodo para buscar una compra específica a traves del id
    @Transactional(readOnly = true)
    public CarritoDeCompras buscarPorId(String id) throws ErrorServicio {

        CarritoDeCompras compra = repoCarrito.buscarPorId(id);

        if (compra != null) {
            return compra;
        } else {
            throw new ErrorServicio("No existe una compra con ese id.");
        }
    }

    //Metodos para devolver la lista de productos que se agregaron al carrito. Ver cual de los dos funciona
//    public List<CarritoDeCompras> listarCarrito() {
//        CarritoDeCompras traeLista = new CarritoDeCompras();
//        List<CarritoDeCompras> lista = new ArrayList();
//        
//        lista.add((CarritoDeCompras) traeLista.getProductosEnCarrito());
//        return lista;
//    }
    //Se le pasa la lista de productos agregados al carrito
//    public void listarCarrito(List<String> idLista) {  //Este metodo esta incluido en realizarCompra2... Entonces no serviría
//
//        List<Producto> productoAgregado = new ArrayList();
//        CarritoDeCompras listaProductos = new CarritoDeCompras();
//
//        //Un fore para cada producto que trae la lista
//        for (String aux : idLista) {
//            Optional<Producto> respuesta = repoProducto.findById(aux);
//
//            //Se valida el id del producto
//            if (respuesta.isPresent()) {
//                Producto p = new Producto();
//                p = respuesta.get();
//                productoAgregado.add(p);
//            }
//
//        }
//
//        //Se setean los productos en la BD para que queden registrados
//        listaProductos.setProductosEnCarrito(productoAgregado);
//    }
}
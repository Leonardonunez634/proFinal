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

    //Metodo que confima la compra y guarda los datos de la compra en la BD.
    @Transactional(propagation = Propagation.NESTED) //Pasarle un String idUsuario
    public Float confirmarCompra(List<Producto> listaProductoCompra, Usuario usuario, Integer cantidadDeProductos) throws ErrorServicio {

        CarritoDeCompras carrito = new CarritoDeCompras();

        carrito.setFechaDeCompra(new Date());

        carrito.setUsuarioComprador(usuario);

        carrito.setProductosEnCarrito(listaProductoCompra);
        carrito.setImporteTotal(calcularCompra(listaProductoCompra, cantidadDeProductos));

        repoCarrito.save(carrito);

        return carrito.getImporteTotal();
    }

    public Float calcularCompra(List<Producto> productoComprado, Integer cantidadDeProductos) throws ErrorServicio {

        float total = 0;

        for (Producto aux : productoComprado) {
            total = total + (aux.getPrecioProducto());
        }

        return total;
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
}
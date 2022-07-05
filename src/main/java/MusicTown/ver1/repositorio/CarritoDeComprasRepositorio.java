package MusicTown.ver1.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import MusicTown.ver1.Entidades.CarritoDeCompras;

@Repository
public interface CarritoDeComprasRepositorio extends JpaRepository<CarritoDeCompras, String> {
    
    @Query("SELECT c FROM CarritoDeCompras c WHERE c.idCarrito = :id ")
    public CarritoDeCompras buscarPorId(@Param("id") String idCarrito);
    
    @Query("SELECT c FROM CarritoDeCompras c WHERE c.usuarioComprador.id = :id")
    public List<CarritoDeCompras> buscarComprasPorUsuario(@Param("id") String id);
}


package MusicTown.ver1.repositorio;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import MusicTown.ver1.Entidades.Marca;


public interface MarcaRepositorio extends JpaRepository<Marca, String>{
    @Query("SELECT m FROM Marca m WHERE m.NombreMarca = :nombre")
    public Optional<Marca> buscarPorNombre(@Param("nombre") String nombreMarca);
    
    @Query("SELECT m FROM Marca m WHERE m.AltaMarca = true")
    public List<Marca> mostrarMarcasActivas();
}

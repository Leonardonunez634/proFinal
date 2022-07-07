
package MusicTown.ver1.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import MusicTown.ver1.Entidades.Usuario;
import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
    public Usuario buscarPorMail(@Param("mail")String mail);
    
    @Query("SELECT u FROM Usuario u") /*Se le puede agregar una consulta WHERE para buscar Alta y baja pero son las 5am y ni en pedo*/
    public List<Usuario> buscarUsuariosActivos();
}



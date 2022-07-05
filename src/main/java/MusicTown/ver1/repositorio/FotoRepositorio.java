
package MusicTown.ver1.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import MusicTown.ver1.Entidades.Foto;

public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
}
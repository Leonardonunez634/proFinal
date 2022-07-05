
package MusicTown.ver1.Entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Marca implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String IDMarca;
    
    private String NombreMarca;
    private boolean AltaMarca;
    
    /*=======> Constructores <=========*/

    public Marca() {
    }
    //Este constructor es para crear una marca
    public Marca(String NombreMarca) {
        this.NombreMarca = NombreMarca;
        this.AltaMarca=true;
    }
    //Este constructor es por si necesitamos tener todos los datos
    public Marca(String IDMarca, String NombreMarca, boolean AltaMarca) {
        this.IDMarca = IDMarca;
        this.NombreMarca = NombreMarca;
        this.AltaMarca = AltaMarca;
    }
    /*=======> Getters y Setters <=========*/

    public String getIDMarca() {
        return IDMarca;
    }

    public void setIDMarca(String IDMarca) {
        this.IDMarca = IDMarca;
    }

    public String getNombreMarca() {
        return NombreMarca;
    }

    public void setNombreMarca(String NombreMarca) {
        this.NombreMarca = NombreMarca;
    }

    public boolean isAltaMarca() {
        return AltaMarca;
    }

    public void setAltaMarca(boolean AltaMarca) {
        this.AltaMarca = AltaMarca;
    }
    
    /*=======> To String <=========*/

    @Override
    public String toString() {
        return "Marca:" + "IDMarca=>" + IDMarca + ", NombreMarca=>" + NombreMarca + ", AltaMarca=>" + AltaMarca;
    }
    
    
    
}

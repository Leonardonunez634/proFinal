package MusicTown.ver1.Entidades;


import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/* Entidad que se va a usar para ingresar Instrumentos o Accesorios*/
@Entity
public class Producto implements Serializable {
    //=======> Atributos <========
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String IdProducto;
    private String NombreProducto;
    @OneToMany
    private Collection<Foto> FotoProducto;
    private int StockProducto;
    private float PrecioProducto;
    private Marca MarcaProducto;
    private boolean Alta;
    //Un usuario puede tener varios productos para vender
    @ManyToOne
    private Usuario usuario;
    
    //=======> Constructores <========

    public Producto() {
    }
    /*Este constructor es para Crear Producto sin FOTO (Capaz lo borremos porque sin foto quien te va a comprar algo)*/
    public Producto(String NombreProducto, int StockProducto, float PrecioProducto, Marca MarcaProducto, Usuario usuario) {
        this.NombreProducto = NombreProducto;
        this.StockProducto = StockProducto;
        this.PrecioProducto = PrecioProducto;
        this.MarcaProducto = MarcaProducto;
        this.Alta=true;
        this.usuario = usuario;
    }
    /*Crear producto CON FOTO*/
    public Producto(String NombreProducto, Collection<Foto> FotoProducto, int StockProducto, float PrecioProducto, Marca MarcaProducto, Usuario usuario) {
        this.NombreProducto = NombreProducto;
        this.FotoProducto = FotoProducto;
        this.StockProducto = StockProducto;
        this.PrecioProducto = PrecioProducto;
        this.MarcaProducto = MarcaProducto;
        this.Alta=true;
        this.usuario = usuario;
    }
    
    /*Constructor para recopilar info*/

    public Producto(String IdProducto, String NombreProducto, Collection<Foto> FotoProducto, int StockProducto, float PrecioProducto, Marca MarcaProducto, boolean Alta, Usuario usuario) {
        this.IdProducto = IdProducto;
        this.NombreProducto = NombreProducto;
        this.FotoProducto = FotoProducto;
        this.StockProducto = StockProducto;
        this.PrecioProducto = PrecioProducto;
        this.MarcaProducto = MarcaProducto;
        this.Alta = Alta;
        this.usuario = usuario;
    }
    
    //=======> Getters Y Setters <===========

    public String getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(String IdProducto) {
        this.IdProducto = IdProducto;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }

    public Collection<Foto> getFotoProducto() {
        return FotoProducto;
    }

    public void setFotoProducto(Collection<Foto> fotoProducto) {
        this.FotoProducto = fotoProducto;
    }

    public int getStockProducto() {
        return StockProducto;
    }

    public void setStockProducto(int StockProducto) {
        this.StockProducto = StockProducto;
    }

    public float getPrecioProducto() {
        return PrecioProducto;
    }

    public void setPrecioProducto(float PrecioProducto) {
        this.PrecioProducto = PrecioProducto;
    }

    public Marca getMarcaProducto() {
        return MarcaProducto;
    }

    public void setMarcaProducto(Marca MarcaProducto) {
        this.MarcaProducto = MarcaProducto;
    }

    public boolean isAlta() {
        return Alta;
    }

    public void setAlta(boolean Alta) {
        this.Alta = Alta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    //========>ToString<========

    @Override
    public String toString() {
        return "Producto{" + "IdProducto=" + IdProducto + ", NombreProducto=" + NombreProducto + ", idFotoProducto=" + FotoProducto + ", StockProducto=" + StockProducto + ", PrecioProducto=" + PrecioProducto + ", MarcaProducto=" + MarcaProducto + ", Alta=" + Alta + ", usuario=" + usuario + '}';
    }
}

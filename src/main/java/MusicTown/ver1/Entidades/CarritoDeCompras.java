package MusicTown.ver1.Entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class CarritoDeCompras {
    
    //======> Atributos <====
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idCarrito;
    private Integer cantidadDeProductos;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCompra;
    
    //Importe total de los productos comprados
    private Float importeTotal;
    
    //Pueden haber varios productos en una sola compra
    @OneToMany
    private List<Producto> productosEnCarrito;
    //Un usuario que compra puede realizar varias compras
    @ManyToOne
    private Usuario usuarioComprador;
    //Se registra horario y fecha de la compra
   

    public CarritoDeCompras() {
    }

    public CarritoDeCompras(String idCarrito, Integer cantidadDeProductos, List<Producto> productosEnCarrito, Usuario usuarioComprador, Date fechaDeCompra, Float importeTotal) {
        this.idCarrito = idCarrito;
        this.cantidadDeProductos = cantidadDeProductos;
        this.productosEnCarrito = productosEnCarrito;
        this.usuarioComprador = usuarioComprador;
        this.fechaDeCompra = fechaDeCompra;
        this.importeTotal = importeTotal;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Integer getCantidadDeProductos() {
        return cantidadDeProductos;
    }

    public void setCantidadDeProductos(Integer cantidadDeProductos) {
        this.cantidadDeProductos = cantidadDeProductos;
    }

    public List<Producto> getProductosEnCarrito() {
        return productosEnCarrito;
    }

    public void setProductosEnCarrito(List<Producto> productosEnCarrito) {
        this.productosEnCarrito = productosEnCarrito;
    }

    public Usuario getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(Usuario usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }

    public Date getFechaDeCompra() {
        return fechaDeCompra;
    }

    public void setFechaDeCompra(Date fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    public Float getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Float importeTotal) {
        this.importeTotal = importeTotal;
    }
}

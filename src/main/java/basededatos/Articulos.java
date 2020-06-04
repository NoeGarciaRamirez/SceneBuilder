/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author noegr
 */
@Entity
@Table(name = "ARTICULOS")
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a"),
    @NamedQuery(name = "Articulos.findById", query = "SELECT a FROM Articulos a WHERE a.id = :id"),
    @NamedQuery(name = "Articulos.findByNombre", query = "SELECT a FROM Articulos a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Articulos.findByStock", query = "SELECT a FROM Articulos a WHERE a.stock = :stock"),
    @NamedQuery(name = "Articulos.findByDescripcion", query = "SELECT a FROM Articulos a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Articulos.findByStockMin", query = "SELECT a FROM Articulos a WHERE a.stockMin = :stockMin"),
    @NamedQuery(name = "Articulos.findByStockMax", query = "SELECT a FROM Articulos a WHERE a.stockMax = :stockMax"),
    @NamedQuery(name = "Articulos.findByPeriferico", query = "SELECT a FROM Articulos a WHERE a.periferico = :periferico"),
    @NamedQuery(name = "Articulos.findByPrecio", query = "SELECT a FROM Articulos a WHERE a.precio = :precio"),
    @NamedQuery(name = "Articulos.findBySegundaMano", query = "SELECT a FROM Articulos a WHERE a.segundaMano = :segundaMano"),
    @NamedQuery(name = "Articulos.findByFotoDelArticulo", query = "SELECT a FROM Articulos a WHERE a.fotoDelArticulo = :fotoDelArticulo")})
public class Articulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "STOCK")
    private short stock;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "STOCK_MIN")
    private String stockMin;
    @Column(name = "STOCK_MAX")
    private String stockMax;
    @Column(name = "PERIFERICO")
    private Boolean periferico;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "SEGUNDA_MANO")
    private Boolean segundaMano;
    @Column(name = "FOTO_DEL_ARTICULO")
    private String fotoDelArticulo;
    @JoinColumn(name = "MARCA", referencedColumnName = "ID")
    @ManyToOne
    private Marcas marca;

    
    
    public Articulos() {
        
    }

    public Articulos(Integer id) {
        this.id = id;
    }

    public Articulos(Integer id, String nombre, short stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getStock() {
        return stock;
    }

    public void setStock(short stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStockMin() {
        return stockMin;
    }

    public void setStockMin(String stockMin) {
        this.stockMin = stockMin;
    }

    public String getStockMax() {
        return stockMax;
    }

    public void setStockMax(String stockMax) {
        this.stockMax = stockMax;
    }

    public Boolean getPeriferico() {
        return periferico;
    }

    public void setPeriferico(Boolean periferico) {
        this.periferico = periferico;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getSegundaMano() {
        return segundaMano;
    }

    public void setSegundaMano(Boolean segundaMano) {
        this.segundaMano = segundaMano;
    }

    public String getFotoDelArticulo() {
        return fotoDelArticulo;
    }

    public void setFotoDelArticulo(String fotoDelArticulo) {
        this.fotoDelArticulo = fotoDelArticulo;
    }

    public Marcas getMarca() {
        return marca;
    }

    public void setMarca(Marcas marca) {
        this.marca = marca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Articulos[ id=" + id + " ]";
    }
    
}

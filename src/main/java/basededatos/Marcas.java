/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author noegr
 */
@Entity
@Table(name = "MARCAS")
@NamedQueries({
    @NamedQuery(name = "Marcas.findAll", query = "SELECT m FROM Marcas m"),
    @NamedQuery(name = "Marcas.findById", query = "SELECT m FROM Marcas m WHERE m.id = :id"),
    @NamedQuery(name = "Marcas.findByCodigo", query = "SELECT m FROM Marcas m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "Marcas.findByTipoDeMarca", query = "SELECT m FROM Marcas m WHERE m.tipoDeMarca = :tipoDeMarca"),
    @NamedQuery(name = "Marcas.findByNombreMarca", query = "SELECT m FROM Marcas m WHERE m.nombreMarca = :nombreMarca")})
public class Marcas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "TIPO_DE_MARCA")
    private String tipoDeMarca;
    @Column(name = "NOMBRE_MARCA")
    private String nombreMarca;
    @OneToMany(mappedBy = "marca")
    private Collection<Articulos> articulosCollection;

    public Marcas() {
    }

    public Marcas(Integer id) {
        this.id = id;
    }

    public Marcas(Integer id, String tipoDeMarca) {
        this.id = id;
        this.tipoDeMarca = tipoDeMarca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoDeMarca() {
        return tipoDeMarca;
    }

    public void setTipoDeMarca(String tipoDeMarca) {
        this.tipoDeMarca = tipoDeMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public Collection<Articulos> getArticulosCollection() {
        return articulosCollection;
    }

    public void setArticulosCollection(Collection<Articulos> articulosCollection) {
        this.articulosCollection = articulosCollection;
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
        if (!(object instanceof Marcas)) {
            return false;
        }
        Marcas other = (Marcas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "basededatos.Marcas[ id=" + id + " ]";
    }
    
}

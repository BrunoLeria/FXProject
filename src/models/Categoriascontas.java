/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "categoriascontas", catalog = "db_fluxo", schema = "")
@NamedQueries({
    @NamedQuery(name = "Categoriascontas.findAll", query = "SELECT c FROM Categoriascontas c"),
    @NamedQuery(name = "Categoriascontas.findByCtcCodigo", query = "SELECT c FROM Categoriascontas c WHERE c.ctcCodigo = :ctcCodigo"),
    @NamedQuery(name = "Categoriascontas.findByCtcDescricao", query = "SELECT c FROM Categoriascontas c WHERE c.ctcDescricao = :ctcDescricao"),
    @NamedQuery(name = "Categoriascontas.findByCtcPositva", query = "SELECT c FROM Categoriascontas c WHERE c.ctcPositva = :ctcPositva")})
public class Categoriascontas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ctc_codigo")
    private Integer ctcCodigo;
    @Basic(optional = false)
    @Column(name = "ctc_descricao")
    private String ctcDescricao;
    @Basic(optional = false)
    @Column(name = "ctc_positva")
    private boolean ctcPositva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sbcFkCtcCodigo")
    private Collection<Subcategorias> subcategoriasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flcFkCtcCodigo")
    private Collection<Fluxocaixa> fluxocaixaCollection;

    public Categoriascontas() {
    }

    public Categoriascontas(Integer ctcCodigo) {
        this.ctcCodigo = ctcCodigo;
    }

    public Categoriascontas(Integer ctcCodigo, String ctcDescricao, boolean ctcPositva) {
        this.ctcCodigo = ctcCodigo;
        this.ctcDescricao = ctcDescricao;
        this.ctcPositva = ctcPositva;
    }

    public Integer getCtcCodigo() {
        return ctcCodigo;
    }

    public void setCtcCodigo(Integer ctcCodigo) {
        this.ctcCodigo = ctcCodigo;
    }

    public String getCtcDescricao() {
        return ctcDescricao;
    }

    public void setCtcDescricao(String ctcDescricao) {
        this.ctcDescricao = ctcDescricao;
    }

    public boolean getCtcPositva() {
        return ctcPositva;
    }

    public void setCtcPositva(boolean ctcPositva) {
        this.ctcPositva = ctcPositva;
    }

    public Collection<Subcategorias> getSubcategoriasCollection() {
        return subcategoriasCollection;
    }

    public void setSubcategoriasCollection(Collection<Subcategorias> subcategoriasCollection) {
        this.subcategoriasCollection = subcategoriasCollection;
    }

    public Collection<Fluxocaixa> getFluxocaixaCollection() {
        return fluxocaixaCollection;
    }

    public void setFluxocaixaCollection(Collection<Fluxocaixa> fluxocaixaCollection) {
        this.fluxocaixaCollection = fluxocaixaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctcCodigo != null ? ctcCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriascontas)) {
            return false;
        }
        Categoriascontas other = (Categoriascontas) object;
        if ((this.ctcCodigo == null && other.ctcCodigo != null) || (this.ctcCodigo != null && !this.ctcCodigo.equals(other.ctcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.Categoriascontas[ ctcCodigo=" + ctcCodigo + " ]";
    }
    
}

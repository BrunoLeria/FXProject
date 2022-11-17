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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "subcategorias", catalog = "db_fluxo", schema = "")
@NamedQueries({
    @NamedQuery(name = "Subcategorias.findAll", query = "SELECT s FROM Subcategorias s"),
    @NamedQuery(name = "Subcategorias.findBySbcCodigo", query = "SELECT s FROM Subcategorias s WHERE s.sbcCodigo = :sbcCodigo"),
    @NamedQuery(name = "Subcategorias.findBySbcDescricao", query = "SELECT s FROM Subcategorias s WHERE s.sbcDescricao = :sbcDescricao")})
public class Subcategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sbc_codigo")
    private Integer sbcCodigo;
    @Basic(optional = false)
    @Column(name = "sbc_descricao")
    private int sbcDescricao;
    @JoinColumn(name = "sbc_fk_ctc_codigo", referencedColumnName = "ctc_codigo")
    @ManyToOne(optional = false)
    private Categoriascontas sbcFkCtcCodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flcFkSbcCodigo")
    private Collection<Fluxocaixa> fluxocaixaCollection;

    public Subcategorias() {
    }

    public Subcategorias(Integer sbcCodigo) {
        this.sbcCodigo = sbcCodigo;
    }

    public Subcategorias(Integer sbcCodigo, int sbcDescricao) {
        this.sbcCodigo = sbcCodigo;
        this.sbcDescricao = sbcDescricao;
    }

    public Integer getSbcCodigo() {
        return sbcCodigo;
    }

    public void setSbcCodigo(Integer sbcCodigo) {
        this.sbcCodigo = sbcCodigo;
    }

    public int getSbcDescricao() {
        return sbcDescricao;
    }

    public void setSbcDescricao(int sbcDescricao) {
        this.sbcDescricao = sbcDescricao;
    }

    public Categoriascontas getSbcFkCtcCodigo() {
        return sbcFkCtcCodigo;
    }

    public void setSbcFkCtcCodigo(Categoriascontas sbcFkCtcCodigo) {
        this.sbcFkCtcCodigo = sbcFkCtcCodigo;
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
        hash += (sbcCodigo != null ? sbcCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subcategorias)) {
            return false;
        }
        Subcategorias other = (Subcategorias) object;
        if ((this.sbcCodigo == null && other.sbcCodigo != null) || (this.sbcCodigo != null && !this.sbcCodigo.equals(other.sbcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controllers.Subcategorias[ sbcCodigo=" + sbcCodigo + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "fluxocaixa")
@NamedQueries({
    @NamedQuery(name = "Fluxocaixa.findAll", query = "SELECT f FROM Fluxocaixa f"),
    @NamedQuery(name = "Fluxocaixa.findByFlcCodigo", query = "SELECT f FROM Fluxocaixa f WHERE f.flcCodigo = :flcCodigo"),
    @NamedQuery(name = "Fluxocaixa.findByFlcDescricao", query = "SELECT f FROM Fluxocaixa f WHERE f.flcDescricao = :flcDescricao"),
    @NamedQuery(name = "Fluxocaixa.findByFlcDataOcorrencia", query = "SELECT f FROM Fluxocaixa f WHERE f.flcDataOcorrencia = :flcDataOcorrencia"),
    @NamedQuery(name = "Fluxocaixa.findByFlcValor", query = "SELECT f FROM Fluxocaixa f WHERE f.flcValor = :flcValor"),
    @NamedQuery(name = "Fluxocaixa.findByFlcFormaPagamento", query = "SELECT f FROM Fluxocaixa f WHERE f.flcFormaPagamento = :flcFormaPagamento")})
public class Fluxocaixa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "flc_codigo")
    private Integer flcCodigo;
    @Basic(optional = false)
    @Column(name = "flc_descricao")
    private String flcDescricao;
    @Basic(optional = false)
    @Column(name = "flc_data_ocorrencia")
    @Temporal(TemporalType.DATE)
    private Date flcDataOcorrencia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "flc_valor")
    private BigDecimal flcValor;
    @Basic(optional = false)
    @Column(name = "flc_forma_pagamento")
    private int flcFormaPagamento;
    @JoinColumn(name = "flc_fk_ctc_codigo", referencedColumnName = "ctc_codigo")
    @ManyToOne(optional = false)
    private Categoriascontas flcFkCtcCodigo;
    @JoinColumn(name = "flc_fk_sbc_codigo", referencedColumnName = "sbc_codigo")
    @ManyToOne
    private Subcategorias flcFkSbcCodigo;

    public Fluxocaixa() {
    }

    public Fluxocaixa(Integer flcCodigo) {
        this.flcCodigo = flcCodigo;
    }

    public Fluxocaixa(Integer flcCodigo, String flcDescricao, Date flcDataOcorrencia, BigDecimal flcValor, int flcFormaPagamento, Categoriascontas flcFkCtcCodigo, Subcategorias flcFkSbcCodigo) {
        this.flcCodigo = flcCodigo;
        this.flcDescricao = flcDescricao;
        this.flcDataOcorrencia = flcDataOcorrencia;
        this.flcValor = flcValor;
        this.flcFormaPagamento = flcFormaPagamento;
        this.flcFkCtcCodigo = flcFkCtcCodigo;
        this.flcFkSbcCodigo = flcFkSbcCodigo;
    }


    public Integer getFlcCodigo() {
        return flcCodigo;
    }

    public void setFlcCodigo(Integer flcCodigo) {
        this.flcCodigo = flcCodigo;
    }

    public String getFlcDescricao() {
        return flcDescricao;
    }

    public void setFlcDescricao(String flcDescricao) {
        this.flcDescricao = flcDescricao;
    }

    public Date getFlcDataOcorrencia() {
        return flcDataOcorrencia;
    }

    public void setFlcDataOcorrencia(Date flcDataOcorrencia) {
        this.flcDataOcorrencia = flcDataOcorrencia;
    }

    public BigDecimal getFlcValor() {
        return flcValor;
    }

    public void setFlcValor(BigDecimal flcValor) {
        this.flcValor = flcValor;
    }

    public int getFlcFormaPagamento() {
        return flcFormaPagamento;
    }

    public void setFlcFormaPagamento(int flcFormaPagamento) {
        this.flcFormaPagamento = flcFormaPagamento;
    }

    public Categoriascontas getFlcFkCtcCodigo() {
        return flcFkCtcCodigo;
    }

    public void setFlcFkCtcCodigo(Categoriascontas flcFkCtcCodigo) {
        this.flcFkCtcCodigo = flcFkCtcCodigo;
    }

    public Subcategorias getFlcFkSbcCodigo() {
        return flcFkSbcCodigo;
    }

    public void setFlcFkSbcCodigo(Subcategorias flcFkSbcCodigo) {
        this.flcFkSbcCodigo = flcFkSbcCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flcCodigo != null ? flcCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fluxocaixa)) {
            return false;
        }
        Fluxocaixa other = (Fluxocaixa) object;
        if ((this.flcCodigo == null && other.flcCodigo != null) || (this.flcCodigo != null && !this.flcCodigo.equals(other.flcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        return "models.Fluxocaixa[ flcCodigo=" + flcCodigo + ", flcDescricao=" + flcDescricao + ", flcDataOcorrencia=" + flcDataOcorrencia + ", flcValor=" + flcValor + ", flcFkCtcCodigo=" + flcFkCtcCodigo.toString() + ", flcFkSbcCodigo=" + flcFkSbcCodigo.toString() + ", flcFormaPagamento=" + flcFormaPagamento + "]";
    }
    
}

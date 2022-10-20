/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class FluxoCaixa {
    private Integer flc_codigo;
    private String flc_descricao;
    private Date flc_data_ocorrencia;
    private Double flc_valor;
    private Integer flc_forma_pagamento;
    private Integer flc_fk_ctc_codigo;
    private Integer flc_fk_sbc_codigo;

    public FluxoCaixa(Integer flc_codigo, String flc_descricao, Date flc_data_ocorrencia, Double flc_valor, Integer flc_forma_pagamento, Integer flc_fk_ctc_codigo, Integer flc_fk_sbc_codigo) {
        this.flc_codigo = flc_codigo;
        this.flc_descricao = flc_descricao;
        this.flc_data_ocorrencia = flc_data_ocorrencia;
        this.flc_valor = flc_valor;
        this.flc_forma_pagamento = flc_forma_pagamento;
        this.flc_fk_ctc_codigo = flc_fk_ctc_codigo;
        this.flc_fk_sbc_codigo = flc_fk_sbc_codigo;
    }

    public Integer getFlc_codigo() {
        return flc_codigo;
    }

    public void setFlc_codigo(Integer flc_codigo) {
        this.flc_codigo = flc_codigo;
    }

    public String getFlc_descricao() {
        return flc_descricao;
    }

    public void setFlc_descricao(String flc_descricao) {
        this.flc_descricao = flc_descricao;
    }

    public Date getFlc_data_ocorrencia() {
        return flc_data_ocorrencia;
    }

    public void setFlc_data_ocorrencia(Date flc_data_ocorrencia) {
        this.flc_data_ocorrencia = flc_data_ocorrencia;
    }

    public Double getFlc_valor() {
        return flc_valor;
    }

    public void setFlc_valor(Double flc_valor) {
        this.flc_valor = flc_valor;
    }

    public Integer getFlc_forma_pagamento() {
        return flc_forma_pagamento;
    }

    public void setFlc_forma_pagamento(Integer flc_forma_pagamento) {
        this.flc_forma_pagamento = flc_forma_pagamento;
    }

    public Integer getFlc_fk_ctc_codigo() {
        return flc_fk_ctc_codigo;
    }

    public void setFlc_fk_ctc_codigo(Integer flc_fk_ctc_codigo) {
        this.flc_fk_ctc_codigo = flc_fk_ctc_codigo;
    }

    public Integer getFlc_fk_sbc_codigo() {
        return flc_fk_sbc_codigo;
    }

    public void setFlc_fk_sbc_codigo(Integer flc_fk_sbc_codigo) {
        this.flc_fk_sbc_codigo = flc_fk_sbc_codigo;
    }
    
    
    
    
}

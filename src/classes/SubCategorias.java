/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Bruno
 */
public class SubCategorias {
    private Integer sbc_codigo;
    private String sbc_descricao;
    private Integer sbc_fk_ctc_codigo;

    public SubCategorias(Integer sbc_codigo, String sbc_descricao, Integer sbc_fk_ctc_codigo) {
        this.sbc_codigo = sbc_codigo;
        this.sbc_descricao = sbc_descricao;
        this.sbc_fk_ctc_codigo = sbc_fk_ctc_codigo;
    }

    public Integer getSbc_codigo() {
        return sbc_codigo;
    }

    public void setSbc_codigo(Integer sbc_codigo) {
        this.sbc_codigo = sbc_codigo;
    }

    public String getSbc_descricao() {
        return sbc_descricao;
    }

    public void setSbc_descricao(String sbc_descricao) {
        this.sbc_descricao = sbc_descricao;
    }

    public Integer getSbc_fk_ctc_codigo() {
        return sbc_fk_ctc_codigo;
    }

    public void setSbc_fk_ctc_codigo(Integer sbc_fk_ctc_codigo) {
        this.sbc_fk_ctc_codigo = sbc_fk_ctc_codigo;
    }
    
    
}

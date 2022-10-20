/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Bruno
 */
public class CategoriasContas {
    private Integer ctc_codigo;
    private String ctc_descricao;
    private Boolean ctc_positiva;

    public CategoriasContas(Integer ctc_codigo, String ctc_descricao, Boolean ctc_positiva) {
        this.ctc_codigo = ctc_codigo;
        this.ctc_descricao = ctc_descricao;
        this.ctc_positiva = ctc_positiva;
    }

    public Integer getCtc_codigo() {
        return ctc_codigo;
    }

    public void setCtc_codigo(Integer ctc_codigo) {
        this.ctc_codigo = ctc_codigo;
    }

    public String getCtc_descricao() {
        return ctc_descricao;
    }

    public void setCtc_descricao(String ctc_descricao) {
        this.ctc_descricao = ctc_descricao;
    }

    public Boolean getCtc_positiva() {
        return ctc_positiva;
    }

    public void setCtc_positiva(Boolean ctc_positiva) {
        this.ctc_positiva = ctc_positiva;
    }
}

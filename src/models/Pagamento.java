/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package models;

/**
 *
 * @author Bruno
 */

public enum Pagamento {
    CARTAO_CREDITO,
    DINHEIRO,
    BOLETO,
    DEPOSITO,
    CONVENIO;
    
    public int selecionarPagamento(){
        return ordinal() + 1;
    }
}

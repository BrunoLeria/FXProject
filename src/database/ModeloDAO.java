/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Monica
 */
public abstract class ModeloDAO<To, Tj> {

    private EntityManagerFactory emf;
    protected Tj objetoJPA;

    public ModeloDAO() {
        emf = Persistence.createEntityManagerFactory("fluxoPU");
    }

    public abstract void inserir(To objeto) throws Exception;
//        objetoJPA.create(objeto);

    public abstract void editar(To objeto) throws Exception;
//        try {
//            objetoJPA.edit(objeto);
//        } catch (NonexistentEntityException ex) {
//            throw new Exception("Não existe esta venda no banco: " + objeto);
//        }

    public abstract void excluir(Integer id) throws Exception;
//        try {
//            objetoJPA.destroy(id);
//        } catch (NonexistentEntityException ex) {
//            throw new Exception("Não existe esta venda no banco: " + id);
//        }

    public abstract void excluir(To objeto) throws Exception;
//        try {
//            objetoJPA.destroy(objeto.getIdVenda());
//        } catch (NonexistentEntityException ex) {
//            throw new Exception("Não existe esta venda no banco: " + objeto);
//        }

    public abstract To consultar(Integer id) throws Exception;
//        return objetoJPA.findFluxocaixa(id);

    public abstract List<To> consultarTodas() throws Exception;
//        return objetoJPA.findFluxocaixaEntities();

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * @param emf the emf to set
     */
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}

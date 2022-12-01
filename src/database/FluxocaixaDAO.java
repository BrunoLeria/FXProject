/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import controllers.FluxocaixaJpaController;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Fluxocaixa;

/**
 *
 * @author Bruno
 */
public class FluxocaixaDAO extends ModeloDAO<Fluxocaixa, FluxocaixaJpaController> {

    EntityManagerFactory emf;
    FluxocaixaJpaController objetoJPA;

    public FluxocaixaDAO() {
        emf = Persistence.createEntityManagerFactory("fluxoPU");
        objetoJPA = new FluxocaixaJpaController(emf);
    }

    @Override
    public void inserir(Fluxocaixa objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(Fluxocaixa objeto) throws Exception {
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe esta venda no banco: " + objeto);
        }
    }

    @Override
    public void excluir(Integer id) throws Exception {
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe esta venda no banco: " + id);
        }
    }

    @Override
    public void excluir(Fluxocaixa objeto) throws Exception {
        try {
            objetoJPA.destroy(objeto.getFlcCodigo());
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe esta venda no banco: " + objeto);
        }
    }

    @Override
    public Fluxocaixa consultar(Integer id) throws Exception {
        return objetoJPA.findFluxocaixa(id);
    }

    @Override
    public List<Fluxocaixa> consultarTodas() throws Exception {
        return objetoJPA.findFluxocaixaEntities();
    }

}

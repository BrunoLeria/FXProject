/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import controllers.SubcategoriasJpaController;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Subcategorias;

/**
 *
 * @author Bruno
 */
public class SubcategoriasDAO extends ModeloDAO<Subcategorias, SubcategoriasJpaController> {

    EntityManagerFactory emf;
    SubcategoriasJpaController objetoJPA;

    public SubcategoriasDAO() {
        emf = Persistence.createEntityManagerFactory("fluxoPU");
        objetoJPA = new SubcategoriasJpaController(emf);
    }

    @Override
    public void inserir(Subcategorias objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(Subcategorias objeto) throws Exception {
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
    public void excluir(Subcategorias objeto) throws Exception {
        try {
            objetoJPA.destroy(objeto.getSbcCodigo());
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe esta venda no banco: " + objeto);
        }
    }

    @Override
    public Subcategorias consultar(Integer id) throws Exception {
        return objetoJPA.findSubcategorias(id);
    }

    @Override
    public List<Subcategorias> consultarTodas() throws Exception {
        return objetoJPA.findSubcategoriasEntities();
    }

}

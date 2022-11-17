package database;

import controllers.CategoriascontasJpaController;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import models.Categoriascontas;

/**
 * CategoriasContas
 */
public class CategoriascontasDAO extends ModeloDAO<Categoriascontas, CategoriascontasJpaController> {

    @Override
    public void inserir(Categoriascontas objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(Categoriascontas objeto) throws Exception {
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
    public void excluir(Categoriascontas objeto) throws Exception {
                try {
            objetoJPA.destroy(objeto.getCtcCodigo());
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe esta venda no banco: " + objeto);
        }
    }

    @Override
    public Categoriascontas consultar(Integer id) throws Exception {
        return objetoJPA.findCategoriascontas(id);
    }

    @Override
    public List<Categoriascontas> consultarTodas() throws Exception {
             return objetoJPA.findCategoriascontasEntities();

    }
    
}
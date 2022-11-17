/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.Categoriascontas;
import models.Fluxocaixa;
import models.Subcategorias;
import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Bruno
 */
public class FluxocaixaJpaController implements Serializable {

    public FluxocaixaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fluxocaixa fluxocaixa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriascontas flcFkCtcCodigo = fluxocaixa.getFlcFkCtcCodigo();
            if (flcFkCtcCodigo != null) {
                flcFkCtcCodigo = em.getReference(flcFkCtcCodigo.getClass(), flcFkCtcCodigo.getCtcCodigo());
                fluxocaixa.setFlcFkCtcCodigo(flcFkCtcCodigo);
            }
            Subcategorias flcFkSbcCodigo = fluxocaixa.getFlcFkSbcCodigo();
            if (flcFkSbcCodigo != null) {
                flcFkSbcCodigo = em.getReference(flcFkSbcCodigo.getClass(), flcFkSbcCodigo.getSbcCodigo());
                fluxocaixa.setFlcFkSbcCodigo(flcFkSbcCodigo);
            }
            em.persist(fluxocaixa);
            if (flcFkCtcCodigo != null) {
                flcFkCtcCodigo.getFluxocaixaCollection().add(fluxocaixa);
                flcFkCtcCodigo = em.merge(flcFkCtcCodigo);
            }
            if (flcFkSbcCodigo != null) {
                flcFkSbcCodigo.getFluxocaixaCollection().add(fluxocaixa);
                flcFkSbcCodigo = em.merge(flcFkSbcCodigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFluxocaixa(fluxocaixa.getFlcCodigo()) != null) {
                throw new PreexistingEntityException("Fluxocaixa " + fluxocaixa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fluxocaixa fluxocaixa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fluxocaixa persistentFluxocaixa = em.find(Fluxocaixa.class, fluxocaixa.getFlcCodigo());
            Categoriascontas flcFkCtcCodigoOld = persistentFluxocaixa.getFlcFkCtcCodigo();
            Categoriascontas flcFkCtcCodigoNew = fluxocaixa.getFlcFkCtcCodigo();
            Subcategorias flcFkSbcCodigoOld = persistentFluxocaixa.getFlcFkSbcCodigo();
            Subcategorias flcFkSbcCodigoNew = fluxocaixa.getFlcFkSbcCodigo();
            if (flcFkCtcCodigoNew != null) {
                flcFkCtcCodigoNew = em.getReference(flcFkCtcCodigoNew.getClass(), flcFkCtcCodigoNew.getCtcCodigo());
                fluxocaixa.setFlcFkCtcCodigo(flcFkCtcCodigoNew);
            }
            if (flcFkSbcCodigoNew != null) {
                flcFkSbcCodigoNew = em.getReference(flcFkSbcCodigoNew.getClass(), flcFkSbcCodigoNew.getSbcCodigo());
                fluxocaixa.setFlcFkSbcCodigo(flcFkSbcCodigoNew);
            }
            fluxocaixa = em.merge(fluxocaixa);
            if (flcFkCtcCodigoOld != null && !flcFkCtcCodigoOld.equals(flcFkCtcCodigoNew)) {
                flcFkCtcCodigoOld.getFluxocaixaCollection().remove(fluxocaixa);
                flcFkCtcCodigoOld = em.merge(flcFkCtcCodigoOld);
            }
            if (flcFkCtcCodigoNew != null && !flcFkCtcCodigoNew.equals(flcFkCtcCodigoOld)) {
                flcFkCtcCodigoNew.getFluxocaixaCollection().add(fluxocaixa);
                flcFkCtcCodigoNew = em.merge(flcFkCtcCodigoNew);
            }
            if (flcFkSbcCodigoOld != null && !flcFkSbcCodigoOld.equals(flcFkSbcCodigoNew)) {
                flcFkSbcCodigoOld.getFluxocaixaCollection().remove(fluxocaixa);
                flcFkSbcCodigoOld = em.merge(flcFkSbcCodigoOld);
            }
            if (flcFkSbcCodigoNew != null && !flcFkSbcCodigoNew.equals(flcFkSbcCodigoOld)) {
                flcFkSbcCodigoNew.getFluxocaixaCollection().add(fluxocaixa);
                flcFkSbcCodigoNew = em.merge(flcFkSbcCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fluxocaixa.getFlcCodigo();
                if (findFluxocaixa(id) == null) {
                    throw new NonexistentEntityException("The fluxocaixa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fluxocaixa fluxocaixa;
            try {
                fluxocaixa = em.getReference(Fluxocaixa.class, id);
                fluxocaixa.getFlcCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fluxocaixa with id " + id + " no longer exists.", enfe);
            }
            Categoriascontas flcFkCtcCodigo = fluxocaixa.getFlcFkCtcCodigo();
            if (flcFkCtcCodigo != null) {
                flcFkCtcCodigo.getFluxocaixaCollection().remove(fluxocaixa);
                flcFkCtcCodigo = em.merge(flcFkCtcCodigo);
            }
            Subcategorias flcFkSbcCodigo = fluxocaixa.getFlcFkSbcCodigo();
            if (flcFkSbcCodigo != null) {
                flcFkSbcCodigo.getFluxocaixaCollection().remove(fluxocaixa);
                flcFkSbcCodigo = em.merge(flcFkSbcCodigo);
            }
            em.remove(fluxocaixa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fluxocaixa> findFluxocaixaEntities() {
        return findFluxocaixaEntities(true, -1, -1);
    }

    public List<Fluxocaixa> findFluxocaixaEntities(int maxResults, int firstResult) {
        return findFluxocaixaEntities(false, maxResults, firstResult);
    }

    private List<Fluxocaixa> findFluxocaixaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fluxocaixa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Fluxocaixa findFluxocaixa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fluxocaixa.class, id);
        } finally {
            em.close();
        }
    }

    public int getFluxocaixaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fluxocaixa> rt = cq.from(Fluxocaixa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Categoriascontas;
import models.Fluxocaixa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Subcategorias;

/**
 *
 * @author Bruno
 */
public class SubcategoriasJpaController implements Serializable {

    public SubcategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subcategorias subcategorias) throws PreexistingEntityException, Exception {
        if (subcategorias.getFluxocaixaCollection() == null) {
            subcategorias.setFluxocaixaCollection(new ArrayList<Fluxocaixa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriascontas sbcFkCtcCodigo = subcategorias.getSbcFkCtcCodigo();
            if (sbcFkCtcCodigo != null) {
                sbcFkCtcCodigo = em.getReference(sbcFkCtcCodigo.getClass(), sbcFkCtcCodigo.getCtcCodigo());
                subcategorias.setSbcFkCtcCodigo(sbcFkCtcCodigo);
            }
            Collection<Fluxocaixa> attachedFluxocaixaCollection = new ArrayList<Fluxocaixa>();
            for (Fluxocaixa fluxocaixaCollectionFluxocaixaToAttach : subcategorias.getFluxocaixaCollection()) {
                fluxocaixaCollectionFluxocaixaToAttach = em.getReference(fluxocaixaCollectionFluxocaixaToAttach.getClass(), fluxocaixaCollectionFluxocaixaToAttach.getFlcCodigo());
                attachedFluxocaixaCollection.add(fluxocaixaCollectionFluxocaixaToAttach);
            }
            subcategorias.setFluxocaixaCollection(attachedFluxocaixaCollection);
            em.persist(subcategorias);
            if (sbcFkCtcCodigo != null) {
                sbcFkCtcCodigo.getSubcategoriasCollection().add(subcategorias);
                sbcFkCtcCodigo = em.merge(sbcFkCtcCodigo);
            }
            for (Fluxocaixa fluxocaixaCollectionFluxocaixa : subcategorias.getFluxocaixaCollection()) {
                Subcategorias oldFlcFkSbcCodigoOfFluxocaixaCollectionFluxocaixa = fluxocaixaCollectionFluxocaixa.getFlcFkSbcCodigo();
                fluxocaixaCollectionFluxocaixa.setFlcFkSbcCodigo(subcategorias);
                fluxocaixaCollectionFluxocaixa = em.merge(fluxocaixaCollectionFluxocaixa);
                if (oldFlcFkSbcCodigoOfFluxocaixaCollectionFluxocaixa != null) {
                    oldFlcFkSbcCodigoOfFluxocaixaCollectionFluxocaixa.getFluxocaixaCollection().remove(fluxocaixaCollectionFluxocaixa);
                    oldFlcFkSbcCodigoOfFluxocaixaCollectionFluxocaixa = em.merge(oldFlcFkSbcCodigoOfFluxocaixaCollectionFluxocaixa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSubcategorias(subcategorias.getSbcCodigo()) != null) {
                throw new PreexistingEntityException("Subcategorias " + subcategorias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subcategorias subcategorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subcategorias persistentSubcategorias = em.find(Subcategorias.class, subcategorias.getSbcCodigo());
            Categoriascontas sbcFkCtcCodigoOld = persistentSubcategorias.getSbcFkCtcCodigo();
            Categoriascontas sbcFkCtcCodigoNew = subcategorias.getSbcFkCtcCodigo();
            Collection<Fluxocaixa> fluxocaixaCollectionOld = persistentSubcategorias.getFluxocaixaCollection();
            Collection<Fluxocaixa> fluxocaixaCollectionNew = subcategorias.getFluxocaixaCollection();
            if (sbcFkCtcCodigoNew != null) {
                sbcFkCtcCodigoNew = em.getReference(sbcFkCtcCodigoNew.getClass(), sbcFkCtcCodigoNew.getCtcCodigo());
                subcategorias.setSbcFkCtcCodigo(sbcFkCtcCodigoNew);
            }
            Collection<Fluxocaixa> attachedFluxocaixaCollectionNew = new ArrayList<Fluxocaixa>();
            for (Fluxocaixa fluxocaixaCollectionNewFluxocaixaToAttach : fluxocaixaCollectionNew) {
                fluxocaixaCollectionNewFluxocaixaToAttach = em.getReference(fluxocaixaCollectionNewFluxocaixaToAttach.getClass(), fluxocaixaCollectionNewFluxocaixaToAttach.getFlcCodigo());
                attachedFluxocaixaCollectionNew.add(fluxocaixaCollectionNewFluxocaixaToAttach);
            }
            fluxocaixaCollectionNew = attachedFluxocaixaCollectionNew;
            subcategorias.setFluxocaixaCollection(fluxocaixaCollectionNew);
            subcategorias = em.merge(subcategorias);
            if (sbcFkCtcCodigoOld != null && !sbcFkCtcCodigoOld.equals(sbcFkCtcCodigoNew)) {
                sbcFkCtcCodigoOld.getSubcategoriasCollection().remove(subcategorias);
                sbcFkCtcCodigoOld = em.merge(sbcFkCtcCodigoOld);
            }
            if (sbcFkCtcCodigoNew != null && !sbcFkCtcCodigoNew.equals(sbcFkCtcCodigoOld)) {
                sbcFkCtcCodigoNew.getSubcategoriasCollection().add(subcategorias);
                sbcFkCtcCodigoNew = em.merge(sbcFkCtcCodigoNew);
            }
            for (Fluxocaixa fluxocaixaCollectionOldFluxocaixa : fluxocaixaCollectionOld) {
                if (!fluxocaixaCollectionNew.contains(fluxocaixaCollectionOldFluxocaixa)) {
                    fluxocaixaCollectionOldFluxocaixa.setFlcFkSbcCodigo(null);
                    fluxocaixaCollectionOldFluxocaixa = em.merge(fluxocaixaCollectionOldFluxocaixa);
                }
            }
            for (Fluxocaixa fluxocaixaCollectionNewFluxocaixa : fluxocaixaCollectionNew) {
                if (!fluxocaixaCollectionOld.contains(fluxocaixaCollectionNewFluxocaixa)) {
                    Subcategorias oldFlcFkSbcCodigoOfFluxocaixaCollectionNewFluxocaixa = fluxocaixaCollectionNewFluxocaixa.getFlcFkSbcCodigo();
                    fluxocaixaCollectionNewFluxocaixa.setFlcFkSbcCodigo(subcategorias);
                    fluxocaixaCollectionNewFluxocaixa = em.merge(fluxocaixaCollectionNewFluxocaixa);
                    if (oldFlcFkSbcCodigoOfFluxocaixaCollectionNewFluxocaixa != null && !oldFlcFkSbcCodigoOfFluxocaixaCollectionNewFluxocaixa.equals(subcategorias)) {
                        oldFlcFkSbcCodigoOfFluxocaixaCollectionNewFluxocaixa.getFluxocaixaCollection().remove(fluxocaixaCollectionNewFluxocaixa);
                        oldFlcFkSbcCodigoOfFluxocaixaCollectionNewFluxocaixa = em.merge(oldFlcFkSbcCodigoOfFluxocaixaCollectionNewFluxocaixa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subcategorias.getSbcCodigo();
                if (findSubcategorias(id) == null) {
                    throw new NonexistentEntityException("The subcategorias with id " + id + " no longer exists.");
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
            Subcategorias subcategorias;
            try {
                subcategorias = em.getReference(Subcategorias.class, id);
                subcategorias.getSbcCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subcategorias with id " + id + " no longer exists.", enfe);
            }
            Categoriascontas sbcFkCtcCodigo = subcategorias.getSbcFkCtcCodigo();
            if (sbcFkCtcCodigo != null) {
                sbcFkCtcCodigo.getSubcategoriasCollection().remove(subcategorias);
                sbcFkCtcCodigo = em.merge(sbcFkCtcCodigo);
            }
            Collection<Fluxocaixa> fluxocaixaCollection = subcategorias.getFluxocaixaCollection();
            for (Fluxocaixa fluxocaixaCollectionFluxocaixa : fluxocaixaCollection) {
                fluxocaixaCollectionFluxocaixa.setFlcFkSbcCodigo(null);
                fluxocaixaCollectionFluxocaixa = em.merge(fluxocaixaCollectionFluxocaixa);
            }
            em.remove(subcategorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subcategorias> findSubcategoriasEntities() {
        return findSubcategoriasEntities(true, -1, -1);
    }

    public List<Subcategorias> findSubcategoriasEntities(int maxResults, int firstResult) {
        return findSubcategoriasEntities(false, maxResults, firstResult);
    }

    private List<Subcategorias> findSubcategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subcategorias.class));
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

    public Subcategorias findSubcategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subcategorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubcategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subcategorias> rt = cq.from(Subcategorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

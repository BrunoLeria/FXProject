/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.Categoriascontas;
import models.Fluxocaixa;
import models.Subcategorias;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Bruno
 */
public class CategoriascontasJpaController implements Serializable {

    public CategoriascontasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriascontas categoriascontas) throws PreexistingEntityException, Exception {
        if (categoriascontas.getSubcategoriasCollection() == null) {
            categoriascontas.setSubcategoriasCollection(new ArrayList<Subcategorias>());
        }
        if (categoriascontas.getFluxocaixaCollection() == null) {
            categoriascontas.setFluxocaixaCollection(new ArrayList<Fluxocaixa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Subcategorias> attachedSubcategoriasCollection = new ArrayList<Subcategorias>();
            for (Subcategorias subcategoriasCollectionSubcategoriasToAttach : categoriascontas.getSubcategoriasCollection()) {
                subcategoriasCollectionSubcategoriasToAttach = em.getReference(subcategoriasCollectionSubcategoriasToAttach.getClass(), subcategoriasCollectionSubcategoriasToAttach.getSbcCodigo());
                attachedSubcategoriasCollection.add(subcategoriasCollectionSubcategoriasToAttach);
            }
            categoriascontas.setSubcategoriasCollection(attachedSubcategoriasCollection);
            Collection<Fluxocaixa> attachedFluxocaixaCollection = new ArrayList<Fluxocaixa>();
            for (Fluxocaixa fluxocaixaCollectionFluxocaixaToAttach : categoriascontas.getFluxocaixaCollection()) {
                fluxocaixaCollectionFluxocaixaToAttach = em.getReference(fluxocaixaCollectionFluxocaixaToAttach.getClass(), fluxocaixaCollectionFluxocaixaToAttach.getFlcCodigo());
                attachedFluxocaixaCollection.add(fluxocaixaCollectionFluxocaixaToAttach);
            }
            categoriascontas.setFluxocaixaCollection(attachedFluxocaixaCollection);
            em.persist(categoriascontas);
            for (Subcategorias subcategoriasCollectionSubcategorias : categoriascontas.getSubcategoriasCollection()) {
                Categoriascontas oldSbcFkCtcCodigoOfSubcategoriasCollectionSubcategorias = subcategoriasCollectionSubcategorias.getSbcFkCtcCodigo();
                subcategoriasCollectionSubcategorias.setSbcFkCtcCodigo(categoriascontas);
                subcategoriasCollectionSubcategorias = em.merge(subcategoriasCollectionSubcategorias);
                if (oldSbcFkCtcCodigoOfSubcategoriasCollectionSubcategorias != null) {
                    oldSbcFkCtcCodigoOfSubcategoriasCollectionSubcategorias.getSubcategoriasCollection().remove(subcategoriasCollectionSubcategorias);
                    oldSbcFkCtcCodigoOfSubcategoriasCollectionSubcategorias = em.merge(oldSbcFkCtcCodigoOfSubcategoriasCollectionSubcategorias);
                }
            }
            for (Fluxocaixa fluxocaixaCollectionFluxocaixa : categoriascontas.getFluxocaixaCollection()) {
                Categoriascontas oldFlcFkCtcCodigoOfFluxocaixaCollectionFluxocaixa = fluxocaixaCollectionFluxocaixa.getFlcFkCtcCodigo();
                fluxocaixaCollectionFluxocaixa.setFlcFkCtcCodigo(categoriascontas);
                fluxocaixaCollectionFluxocaixa = em.merge(fluxocaixaCollectionFluxocaixa);
                if (oldFlcFkCtcCodigoOfFluxocaixaCollectionFluxocaixa != null) {
                    oldFlcFkCtcCodigoOfFluxocaixaCollectionFluxocaixa.getFluxocaixaCollection().remove(fluxocaixaCollectionFluxocaixa);
                    oldFlcFkCtcCodigoOfFluxocaixaCollectionFluxocaixa = em.merge(oldFlcFkCtcCodigoOfFluxocaixaCollectionFluxocaixa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriascontas(categoriascontas.getCtcCodigo()) != null) {
                throw new PreexistingEntityException("Categoriascontas " + categoriascontas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoriascontas categoriascontas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriascontas persistentCategoriascontas = em.find(Categoriascontas.class, categoriascontas.getCtcCodigo());
            Collection<Subcategorias> subcategoriasCollectionOld = persistentCategoriascontas.getSubcategoriasCollection();
            Collection<Subcategorias> subcategoriasCollectionNew = categoriascontas.getSubcategoriasCollection();
            Collection<Fluxocaixa> fluxocaixaCollectionOld = persistentCategoriascontas.getFluxocaixaCollection();
            Collection<Fluxocaixa> fluxocaixaCollectionNew = categoriascontas.getFluxocaixaCollection();
            List<String> illegalOrphanMessages = null;
            for (Subcategorias subcategoriasCollectionOldSubcategorias : subcategoriasCollectionOld) {
                if (!subcategoriasCollectionNew.contains(subcategoriasCollectionOldSubcategorias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subcategorias " + subcategoriasCollectionOldSubcategorias + " since its sbcFkCtcCodigo field is not nullable.");
                }
            }
            for (Fluxocaixa fluxocaixaCollectionOldFluxocaixa : fluxocaixaCollectionOld) {
                if (!fluxocaixaCollectionNew.contains(fluxocaixaCollectionOldFluxocaixa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fluxocaixa " + fluxocaixaCollectionOldFluxocaixa + " since its flcFkCtcCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Subcategorias> attachedSubcategoriasCollectionNew = new ArrayList<Subcategorias>();
            for (Subcategorias subcategoriasCollectionNewSubcategoriasToAttach : subcategoriasCollectionNew) {
                subcategoriasCollectionNewSubcategoriasToAttach = em.getReference(subcategoriasCollectionNewSubcategoriasToAttach.getClass(), subcategoriasCollectionNewSubcategoriasToAttach.getSbcCodigo());
                attachedSubcategoriasCollectionNew.add(subcategoriasCollectionNewSubcategoriasToAttach);
            }
            subcategoriasCollectionNew = attachedSubcategoriasCollectionNew;
            categoriascontas.setSubcategoriasCollection(subcategoriasCollectionNew);
            Collection<Fluxocaixa> attachedFluxocaixaCollectionNew = new ArrayList<Fluxocaixa>();
            for (Fluxocaixa fluxocaixaCollectionNewFluxocaixaToAttach : fluxocaixaCollectionNew) {
                fluxocaixaCollectionNewFluxocaixaToAttach = em.getReference(fluxocaixaCollectionNewFluxocaixaToAttach.getClass(), fluxocaixaCollectionNewFluxocaixaToAttach.getFlcCodigo());
                attachedFluxocaixaCollectionNew.add(fluxocaixaCollectionNewFluxocaixaToAttach);
            }
            fluxocaixaCollectionNew = attachedFluxocaixaCollectionNew;
            categoriascontas.setFluxocaixaCollection(fluxocaixaCollectionNew);
            categoriascontas = em.merge(categoriascontas);
            for (Subcategorias subcategoriasCollectionNewSubcategorias : subcategoriasCollectionNew) {
                if (!subcategoriasCollectionOld.contains(subcategoriasCollectionNewSubcategorias)) {
                    Categoriascontas oldSbcFkCtcCodigoOfSubcategoriasCollectionNewSubcategorias = subcategoriasCollectionNewSubcategorias.getSbcFkCtcCodigo();
                    subcategoriasCollectionNewSubcategorias.setSbcFkCtcCodigo(categoriascontas);
                    subcategoriasCollectionNewSubcategorias = em.merge(subcategoriasCollectionNewSubcategorias);
                    if (oldSbcFkCtcCodigoOfSubcategoriasCollectionNewSubcategorias != null && !oldSbcFkCtcCodigoOfSubcategoriasCollectionNewSubcategorias.equals(categoriascontas)) {
                        oldSbcFkCtcCodigoOfSubcategoriasCollectionNewSubcategorias.getSubcategoriasCollection().remove(subcategoriasCollectionNewSubcategorias);
                        oldSbcFkCtcCodigoOfSubcategoriasCollectionNewSubcategorias = em.merge(oldSbcFkCtcCodigoOfSubcategoriasCollectionNewSubcategorias);
                    }
                }
            }
            for (Fluxocaixa fluxocaixaCollectionNewFluxocaixa : fluxocaixaCollectionNew) {
                if (!fluxocaixaCollectionOld.contains(fluxocaixaCollectionNewFluxocaixa)) {
                    Categoriascontas oldFlcFkCtcCodigoOfFluxocaixaCollectionNewFluxocaixa = fluxocaixaCollectionNewFluxocaixa.getFlcFkCtcCodigo();
                    fluxocaixaCollectionNewFluxocaixa.setFlcFkCtcCodigo(categoriascontas);
                    fluxocaixaCollectionNewFluxocaixa = em.merge(fluxocaixaCollectionNewFluxocaixa);
                    if (oldFlcFkCtcCodigoOfFluxocaixaCollectionNewFluxocaixa != null && !oldFlcFkCtcCodigoOfFluxocaixaCollectionNewFluxocaixa.equals(categoriascontas)) {
                        oldFlcFkCtcCodigoOfFluxocaixaCollectionNewFluxocaixa.getFluxocaixaCollection().remove(fluxocaixaCollectionNewFluxocaixa);
                        oldFlcFkCtcCodigoOfFluxocaixaCollectionNewFluxocaixa = em.merge(oldFlcFkCtcCodigoOfFluxocaixaCollectionNewFluxocaixa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriascontas.getCtcCodigo();
                if (findCategoriascontas(id) == null) {
                    throw new NonexistentEntityException("The categoriascontas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriascontas categoriascontas;
            try {
                categoriascontas = em.getReference(Categoriascontas.class, id);
                categoriascontas.getCtcCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriascontas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Subcategorias> subcategoriasCollectionOrphanCheck = categoriascontas.getSubcategoriasCollection();
            for (Subcategorias subcategoriasCollectionOrphanCheckSubcategorias : subcategoriasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoriascontas (" + categoriascontas + ") cannot be destroyed since the Subcategorias " + subcategoriasCollectionOrphanCheckSubcategorias + " in its subcategoriasCollection field has a non-nullable sbcFkCtcCodigo field.");
            }
            Collection<Fluxocaixa> fluxocaixaCollectionOrphanCheck = categoriascontas.getFluxocaixaCollection();
            for (Fluxocaixa fluxocaixaCollectionOrphanCheckFluxocaixa : fluxocaixaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoriascontas (" + categoriascontas + ") cannot be destroyed since the Fluxocaixa " + fluxocaixaCollectionOrphanCheckFluxocaixa + " in its fluxocaixaCollection field has a non-nullable flcFkCtcCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoriascontas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoriascontas> findCategoriascontasEntities() {
        return findCategoriascontasEntities(true, -1, -1);
    }

    public List<Categoriascontas> findCategoriascontasEntities(int maxResults, int firstResult) {
        return findCategoriascontasEntities(false, maxResults, firstResult);
    }

    private List<Categoriascontas> findCategoriascontasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriascontas.class));
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

    public Categoriascontas findCategoriascontas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriascontas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriascontasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriascontas> rt = cq.from(Categoriascontas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Owner;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
/**
 *
 * @author Home
 */
public class OwnerService implements Serializable{
    
    public OwnerService(EntityManagerFactory emf){
        this.emf =  emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Owner owner){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().commit();
            em.persist(owner);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public void edit(Owner owner) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            owner = em.merge(owner);
            em.getTransaction().commit();
        } catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Long id = owner.getId();
                if(findOwner(id) == null){
                    throw new NonexistentEntityException ("The owner with id" + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if(em != null){
                em.close();
            }
        }
    }
    
    public void destroy (Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Owner owner;
            try {
                owner = em.getReference(Owner.class, id);
                owner.getId();
            } catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The owner with id " + id + " no longer exists. ", enfe);
            }
            em.remove(owner);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public List<Owner> findOwnerEntities(){
        return findOwnerEntities(true, -1, -1);
    }
    
    public List<Owner> findOwnerEntities (int maxResults, int firstResult){
        return findOwnerEntities(false, maxResults, firstResult);
    }
    
    public List<Owner> findOwnerEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Owner.class));
            Query q = em.createQuery(cq);
            if (!all){
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Owner findOwner(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Owner.class, id);
        } finally {
            em.close();
        }
    }

        
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Veterinarian;
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
public class VeterinarianService implements Serializable {
    
    public VeterinarianService(EntityManagerFactory emf){
        this.emf =  emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Veterinarian veterinarian){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().commit();
            em.persist(veterinarian);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public void edit(Veterinarian veterinarian) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            veterinarian = em.merge(veterinarian);
            em.getTransaction().commit();
        } catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Long id = veterinarian.getId();
                if(findVeterinarian(id) == null){
                    throw new NonexistentEntityException ("The veterinarian with id" + id + " no longer exists.");
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
            Veterinarian veterinarian;
            try {
                veterinarian = em.getReference(Veterinarian.class, id);
                veterinarian.getId();
            } catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The veterinarian with id " + id + " no longer exists. ", enfe);
            }
            em.remove(veterinarian);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public List<Veterinarian> findVeterinarianEntities(){
        return findVeterinarianEntities(true, -1, -1);
    }
    
    public List<Veterinarian> findVeterinarianEntities (int maxResults, int firstResult){
        return findVeterinarianEntities(false, maxResults, firstResult);
    }
    
    public List<Veterinarian> findVeterinarianEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veterinarian.class));
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

    public Veterinarian findVeterinarian(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veterinarian.class, id);
        } finally {
            em.close();
        }
    }
    
}

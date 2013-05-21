/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Vacine;
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
public class VacineService implements Serializable {
    
    public VacineService(EntityManagerFactory emf){
        this.emf =  emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Vacine vacine){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().commit();
            em.persist(vacine);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public void edit(Vacine vacine) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vacine = em.merge(vacine);
            em.getTransaction().commit();
        } catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Long id = vacine.getId();
                if(findVacine(id) == null){
                    throw new NonexistentEntityException ("The vacine with id" + id + " no longer exists.");
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
            Vacine vacine;
            try {
                vacine = em.getReference(Vacine.class, id);
                vacine.getId();
            } catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The vacine with id " + id + " no longer exists. ", enfe);
            }
            em.remove(vacine);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public List<Vacine> findVacineEntities(){
        return findVacineEntities(true, -1, -1);
    }
    
    public List<Vacine> findVacineEntities (int maxResults, int firstResult){
        return findVacineEntities(false, maxResults, firstResult);
    }
    
    public List<Vacine> findVacineEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacine.class));
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

    public Vacine findVacine(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacine.class, id);
        } finally {
            em.close();
        }
    }
    
}

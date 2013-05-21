/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Appointment;
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
public class AppointmentService implements Serializable{
    
    public AppointmentService(EntityManagerFactory emf){
        this.emf =  emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Appointment appointment){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().commit();
            em.persist(appointment);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public void edit(Appointment appointment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            appointment = em.merge(appointment);
            em.getTransaction().commit();
        } catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Long id = appointment.getId();
                if(findAppointment(id) == null){
                    throw new NonexistentEntityException ("The appointment with id" + id + " no longer exists.");
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
            Appointment appointment;
            try {
                appointment = em.getReference(Appointment.class, id);
                appointment.getId();
            } catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The appointment with id " + id + " no longer exists. ", enfe);
            }
            em.remove(appointment);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public List<Appointment> findAppointmentEntities(){
        return findAppointmentEntities(true, -1, -1);
    }
    
    public List<Appointment> findAppointmentEntities (int maxResults, int firstResult){
        return findAppointmentEntities(false, maxResults, firstResult);
    }
    
    public List<Appointment> findAppointmentEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Appointment.class));
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

    public Appointment findAppointment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Appointment.class, id);
        } finally {
            em.close();
        }
    }
    
}

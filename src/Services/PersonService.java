package Services;

import Entity.Person;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;


public class PersonService implements Serializable{
    
    public PersonService(EntityManagerFactory emf){
        this.emf =  emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Person person){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().commit();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public void edit(Person person) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.merge(person);
            em.getTransaction().commit();
        } catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Long id = person.getId();
                if(findPerson(id) == null){
                    throw new NonexistentEntityException ("The person with id" + id + " no longer exists.");
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
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getId();
            } catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The person with id " + id + " no longer exists. ", enfe);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public List<Person> findPersonEntities(){
        return findPersonEntities(true, -1, -1);
    }
    
    public List<Person> findPersonEntities (int maxResults, int firstResult){
        return findPersonEntities(false, maxResults, firstResult);
    }
    
    public List<Person> findPersonEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
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

    public Person findPerson(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }
}
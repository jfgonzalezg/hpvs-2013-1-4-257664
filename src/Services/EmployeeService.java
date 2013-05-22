/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Employee;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author John F
 */
public class EmployeeService implements Serializable{
    private EntityManagerFactory emf=null;
    public EmployeeService(EntityManagerFactory emf){
        this.emf=emf;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Employee e){
        EntityManager em=null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    public void edit(Employee e) throws NonexistentEntityException, Exception{
        EntityManager em=null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg=ex.getLocalizedMessage();
            if(msg==null||msg.length()==0){
              Long id=e.getId();
              if(findEmployee(id)==null){
              throw new NonexistentEntityException("The employee with id: "+id+" no longer exists.");  
              }
            }
            throw ex;
              
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    public void destroy(Long id)throws NonexistentEntityException{
        EntityManager em=null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            Employee e;
            try{
                e=em.getReference(Employee.class,id);
                e.getId();
            }catch(EntityNotFoundException enfe){
                throw new NonexistentEntityException("The employee with id"+id+"no longer exists.",enfe);
            }
            em.remove(e);
            em.getTransaction().commit();
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    public Employee findEmployee(Long id){
        EntityManager em=null;
        try{
            return em.find(Employee.class,id);
        }finally{
            em.close();
        }
     }
    public int getEmployeeCount(){
        EntityManager em=null;
        try{
            CriteriaQuery cq=em.getCriteriaBuilder().createQuery();
            Root<Employee> rt=cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q=em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }
    public List<Employee> findEmployeeEntities(){
        return findEmployeeEntities(true,-1,-1);
    }
    public List<Employee> findEmployeeEntities(int maxResults,int firtsResult){
        return findEmployeeEntities(false,maxResults,firtsResult);
    }
    public List<Employee> findEmployeeEntities(boolean all,int maxResults,int firtsResult){
        EntityManager em=getEntityManager();
        try{
            CriteriaQuery cq=em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q=em.createQuery(cq);
            if(!all){
                q.setMaxResults(maxResults);
                q.setFirstResult(firtsResult);
            }
            return q.getResultList();
        }finally {
            em.close();
        }       
    }
}


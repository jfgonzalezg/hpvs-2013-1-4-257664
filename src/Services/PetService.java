/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Owner;
import Entity.Pet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
/**
 *
 * @author Home
 */
public class PetService implements Serializable {
    
    public PetService(EntityManagerFactory emf){
        this.emf =  emf;
        ownerService = new OwnerService(emf);
    }
    
    private EntityManagerFactory emf = null;
    OwnerService ownerService = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Pet pet){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pet);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public void edit(Pet pet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pet = em.merge(pet);
            em.getTransaction().commit();
        } catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Long id = pet.getId();
                if(findPet(id) == null){
                    throw new NonexistentEntityException ("The pet with id" + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if(em != null){
                em.close();
            }
        }
    }
    
    public void destroy (Long id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pet pet;
            try {
                pet = em.getReference(Pet.class, id);
                pet.getId();
            } catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The pet with id " + id + " no longer exists. ", enfe);
            }
            em.remove(pet);
            em.getTransaction().commit();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
    
    public List<Pet> findPetEntities(){
        return findPetEntities(true, -1, -1);
    }
    
    public List<Pet> findPetEntities (int maxResults, int firstResult){
        return findPetEntities(false, maxResults, firstResult);
    }
    
    public List<Pet> findPetEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pet.class));
            Query q = em.createQuery(cq);
            if (!all){
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            for(int i = 0; i< q.getResultList().size(); i++){
                Pet pet = (Pet) q.getResultList().get(i);
                long petId = pet.getId();
                Query query = em.createNativeQuery("select ownerList_PersonID from pet_owner"
                        + "where Pet_ID = " + petId);
                pet.setOwnerList(findOwners(query));
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    @SuppressWarnings("empty-stament")
    public Pet findPet(Long id){
        EntityManager em = getEntityManager();
        try {
            Pet pet = em.find(Pet.class, id);
            long petId = pet.getId();
            Query query = em.createNativeQuery("select ownerList_PersonID from pet_owner where"
                    + "Pet_ID = " + petId);
            pet.setOwnerList(findOwners(query));
            return pet;
        } finally {
            em.close();
        }
    }
    
    private List<Owner> findOwners(Query query){
        List<Owner> owners = new ArrayList<>();
        for (int j = 0; j < query.getResultList().size(); j++){
            Long ownerId = (Long) query.getResultList().get(j);
            owners.add( ownerService.findOwner(ownerId));
        }
        return owners;
    }
    
    public int getPetCount(){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pet> rt = cq.from(Pet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
}

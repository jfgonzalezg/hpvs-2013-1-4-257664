/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;



/**
 *
 * @author John F
 */
@Entity
@DiscriminatorValue("Owner")
public class Owner extends Person{
        
    public Owner(){
        super();
    }
    public Owner(long id, String nam, String lastname, String SSN, ArrayList<Pet> pets){
    
        super(id,nam,lastname,SSN);
        this.pet=pets;
    }
    
    
    @OneToMany(mappedBy = "Owner")
    private List<Pet> pet = new ArrayList<>();
    
    public List<Pet> getPets(){
        return pet;
    }
    public void setPets(List<Pet> pet){
        this.pet=pet;
    }
        

}
